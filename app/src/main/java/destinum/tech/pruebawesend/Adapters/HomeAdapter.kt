package destinum.tech.pruebawesend.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import destinum.tech.pruebawesend.Data.Local.Entities.AdList
import destinum.tech.pruebawesend.R

class HomeAdapter(val list: List<AdList>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.format_adlist, parent, false))
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
        val adList = list[position]
        (holder as ViewHolder).bind(adList)
    }

    internal class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val username = view.findViewById<TextView>(R.id.format_adlist_username)
        private val feedback = view.findViewById<TextView>(R.id.format_adlist_feedback)
        private val trades = view.findViewById<TextView>(R.id.format_adlist_trades)
        private val money1 = view.findViewById<TextView>(R.id.format_adlist_money1)
        private val money2 = view.findViewById<TextView>(R.id.format_adlist_money2)

        fun bind(adList: AdList) {

            username.text = adList.data?.profile!!.username
            feedback.text = adList.data.profile.username
            trades.text = adList.data.profile.username
            money1.text = adList.data.temp_price_usd
            money2.text = adList.data.temp_price

        }
    }
}