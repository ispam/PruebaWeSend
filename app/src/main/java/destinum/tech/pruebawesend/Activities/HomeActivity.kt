package destinum.tech.pruebawesend.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import destinum.tech.pruebawesend.Adapters.LogAdapter
import destinum.tech.pruebawesend.Data.Local.Entities.DataResult
import destinum.tech.pruebawesend.Data.Local.Entities.ListData
import destinum.tech.pruebawesend.Data.Local.ViewModels.ListDataViewModel
import destinum.tech.pruebawesend.Data.Local.ViewModels.LogViewModel
import destinum.tech.pruebawesend.Data.Remote.LocalBitcoinsAPI
import destinum.tech.pruebawesend.R
import destinum.tech.pruebawesend.Utils.checkFirstRun
import destinum.tech.pruebawesend.Utils.isOnline
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    companion object {
        const val TAG = "HomeActivity"
    }

    private val disposable = CompositeDisposable()
    private lateinit var  dialog: ProgressDialog

    @Inject
    lateinit var provideAPI: LocalBitcoinsAPI

    @Inject
    lateinit var listDataVM: ListDataViewModel

    @Inject
    lateinit var logVM: LogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        App.component.inject(this)

        title = getString(R.string.home_title)
        checkFirstRun(this, {}, setUpTapTarget(), {})

        home_recycler.layoutManager = LinearLayoutManager(this,  RecyclerView.VERTICAL, false)
        getLogsFromDB()

        home_fab.setOnClickListener {
            showProgressDialog()
            if (isOnline(this)){
                getLastLogNumber()
            } else {
                Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        getLogsFromDB()
    }

    private fun getLogsFromDB() {

        disposable.add(logVM.getAllLogs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                if (it.isEmpty()) {
                    home_message.visibility = View.VISIBLE
                }
                val adapter = LogAdapter(it.sortedByDescending { it.logs_id })
                adapter.setHasStableIds(true)
                home_recycler.adapter = adapter
            }
            .doOnError { e -> Log.e(TAG, e.message) }
            .subscribe())
    }

    private fun getLastLogNumber() {

        disposable.add(logVM.getCurrentLogsCount()
            .subscribeOn(Schedulers.io())
            .map {  getResults(it.toLong()) }
            .subscribe())

    }

    private fun getResults(logId: Long) {

        disposable.add(Observable.zip(provideAPI.getList(1), provideAPI.getList(2),
            BiFunction { data1: DataResult, data2: DataResult ->
                val list = arrayListOf<DataResult>()
                list.add(data1)
                list.add(data2)
                return@BiFunction list
             })
            .subscribeOn(Schedulers.io())
            .doOnError { e -> Log.e(TAG, e.message) }
            .flatMapCompletable {
                val adapterList = mutableListOf<ListData>()

                for (dataResult in it) {
                    for (adList in dataResult.data.ad_list){
                        adapterList.add(ListData(adList.listData.profile, adList.listData.ad_id, adList.listData.temp_price, adList.listData.temp_price_usd, logId))
                    }
                }
                listDataVM.insertListData(adapterList)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("log_id", logId)
                startActivity(intent)
            }
            .subscribe())

    }

    private fun setUpTapTarget(): () -> Unit {
        return {
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(home_fab)
//            .setAutoDismiss(false)
//            .setAutoFinish(false)
                .setPrimaryText("You can search for new info here!")
                .setBackgroundColour(resources.getColor(R.color.colorPrimaryDark))
                .setAnimationInterpolator(FastOutSlowInInterpolator())
//            .setPromptFocal(RectanglePromptFocal())
                .setPromptStateChangeListener { prompt, state ->
                    when (state) {
                        MaterialTapTargetPrompt.STATE_FOCAL_PRESSED -> {
                            // User has pressed the prompt target
                        }
                        MaterialTapTargetPrompt.STATE_DISMISSED -> {
                            // Prompt has been removed from view after the prompt has either been pressed somewhere other than the prompt target or the system back button has been pressed
                        }
                        MaterialTapTargetPrompt.STATE_FINISHED -> {
                            // Prompt has been removed from view after the prompt has been pressed in the focal area
                        }
                    }
                }
                .show()
        }
    }

    private fun showProgressDialog() {
        dialog = ProgressDialog(this)
        dialog.setCancelable(false)
        dialog.setMessage(getString(R.string.downloading))
        dialog.show()
    }

    private fun hideProgressDialog() {
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }

    override fun onStop() {
        if (!disposable.isDisposed) disposable.clear()
        hideProgressDialog()
        super.onStop()
    }
}
