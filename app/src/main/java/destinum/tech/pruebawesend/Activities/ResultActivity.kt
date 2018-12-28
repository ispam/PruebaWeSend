package destinum.tech.pruebawesend.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import destinum.tech.pruebawesend.Adapters.ResultAdapter
import destinum.tech.pruebawesend.Data.Local.ViewModels.ListDataViewModel
import destinum.tech.pruebawesend.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_result.*
import javax.inject.Inject

class ResultActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var listDataVM: ListDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        App.component.inject(this)

        actionBar.setHomeButtonEnabled(true)

        result_recycler.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        val logId = intent.extras.get("log_id") as Long

        disposable.add(listDataVM.getListDataBasedOnLogID(logId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.isNotEmpty() }
            .map {
                val adapter = ResultAdapter(it)
                adapter.setHasStableIds(true)
                result_recycler.adapter = adapter
            }
            .subscribe())

    }

    override fun onStop() {
        if (!disposable.isDisposed) disposable.clear()
        super.onStop()
    }
}
