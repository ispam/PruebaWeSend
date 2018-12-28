package destinum.tech.pruebawesend.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import destinum.tech.pruebawesend.Data.Local.Entities.AdList
import destinum.tech.pruebawesend.Data.Local.Entities.ListData
import destinum.tech.pruebawesend.R
import java.util.*

class ResultAdapter(val data: List<ListData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.format_adlist, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val adData = data[position]
        (holder as ViewHolder).bind(adData)
    }

    internal class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val username = view.findViewById<TextView>(R.id.format_adlist_username)
        private val feedback = view.findViewById<TextView>(R.id.format_adlist_feedback)
        private val trades = view.findViewById<TextView>(R.id.format_adlist_trades)
        private val money1 = view.findViewById<TextView>(R.id.format_adlist_money1)
        private val money2 = view.findViewById<TextView>(R.id.format_adlist_money2)
        private val card = view.findViewById<CardView>(R.id.format_adlist_card)
        private val constraint = view.findViewById<ConstraintLayout>(R.id.format_adlist_constraint)

        fun bind(adData: ListData) {

            val color = getColor(Random().nextInt(15))
            constraint.setBackgroundColor(Color.parseColor("#$color"))

            username.text = adData.profile.username
            feedback.text = "${adData.profile.feedback_score}%"
            trades.text = adData.profile.trade_count
            money1.text = "${adData.temp_price_usd} USD"
            money2.text = "${adData.temp_price} VES"

        }

        private fun getColor(pos: Int): String {
            val colors = arrayOf("b871b3", "530852", "091256", "094e56", "095613", "305609", "565209",
                "561c09", "00bcd4", "03a9f4", "ff9800", "607d8b", "ec407a", "d4e157", "e84e40")
            return colors[pos]
        }
    }
}