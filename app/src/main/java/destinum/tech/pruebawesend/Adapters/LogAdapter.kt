package destinum.tech.pruebawesend.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import destinum.tech.pruebawesend.Activities.ResultActivity
import destinum.tech.pruebawesend.Data.Local.Entities.Log
import destinum.tech.pruebawesend.R

class LogAdapter(val list: List<Log>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.format_log, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val log = list[position]
        (holder as ViewHolder).bind(log)
    }

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val date = view.findViewById<TextView>(R.id.format_log_date)
        private val rateUsd = view.findViewById<TextView>(R.id.format_log_rate_usd)
        private val rateVes = view.findViewById<TextView>(R.id.format_log_rate_ves)
        private val above = view.findViewById<TextView>(R.id.format_log_above)
        private val below = view.findViewById<TextView>(R.id.format_log_below)
        private val card = view.findViewById<CardView>(R.id.format_log_card)

        fun bind(log: Log) {

            date.text = log.date
            rateUsd.text = "${String.format("%.${2}f", log.rate_usd.toDouble())} USD"
            rateVes.text = "${String.format("%.${2}f", log.rate_ves.toDouble())} VES"
            above.text = log.above
            below.text = log.below

            card.setOnClickListener {
                val intent = Intent(it.context, ResultActivity::class.java)
                intent.putExtra("log_id", log.logs_id-1)
                intent.putExtra("adapter", true)
                it.context.startActivity(intent)

            }

        }
    }
}