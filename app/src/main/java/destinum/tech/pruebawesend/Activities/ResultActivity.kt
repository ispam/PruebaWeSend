package destinum.tech.pruebawesend.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import destinum.tech.pruebawesend.Adapters.ResultAdapter
import destinum.tech.pruebawesend.Data.Local.Entities.ListData
import destinum.tech.pruebawesend.Data.Local.Entities.Log
import destinum.tech.pruebawesend.Data.Local.ViewModels.ListDataViewModel
import destinum.tech.pruebawesend.Data.Local.ViewModels.LogViewModel
import destinum.tech.pruebawesend.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_result.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ResultActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ResultActivity"
    }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var listDataVM: ListDataViewModel

    @Inject
    lateinit var logVM: LogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        App.component.inject(this)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        title = getString(R.string.result_title)

        result_recycler.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        val logId = intent.extras.get("log_id") as Long

        disposable.add(listDataVM.getListDataBasedOnLogID(logId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.isNotEmpty() }
            .map {
                val adapter = ResultAdapter(it)
                getAtypicalData(it)
                adapter.setHasStableIds(true)
                result_recycler.adapter = adapter
            }
            .subscribe())

    }

    private fun getAtypicalData(list: Collection<ListData>) {

        val usd = list.map { it -> it.temp_price_usd.toDouble()}.average()
        val ves = list.map { it -> it.temp_price.toDouble()}.average()
        result_money1.text = "${String.format("%.${2}f", usd)} USD"
        result_money2.text = "${String.format("%.${2}f", ves)} VES"

        val above = list
            .map { it -> it.temp_price.toDouble() }
            .filter { it -> it > usd }.count()

        val below = list
            .map { it -> it.temp_price.toDouble() }
            .filter { it -> it < usd }.count()

        result_above.text = above.toString()
        result_below.text = below.toString()

        val date = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a")

        disposable.add(logVM.insertLog(Log(sdf.format(date.time), usd.toString(), above.toString(), below.toString()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { android.util.Log.i(TAG, "Log Created") }
            .subscribe())


    }

    override fun onStop() {
        if (!disposable.isDisposed) disposable.clear()
        super.onStop()
    }
}
