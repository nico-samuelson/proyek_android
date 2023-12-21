package proyek.andro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.model.Tournament

class ScheduleUpcomingAdapter(
    private val tournaments : ArrayList<Tournament>
) : RecyclerView.Adapter<ScheduleUpcomingAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val ivTeam1 : ImageView = itemView.findViewById(R.id.ivTeam1)
        val tvTeam1 : TextView = itemView.findViewById(R.id.tvTeam1)

        val ivTeam2 : ImageView = itemView.findViewById(R.id.ivTeam2)
        val tvTeam2 : TextView = itemView.findViewById(R.id.tvTeam2)

        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvTime : TextView = itemView.findViewById(R.id.tvWaktu)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleUpcomingAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_schedule_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleUpcomingAdapter.ListViewHolder, position: Int) {
        val currentItem = tournaments[position]
        holder.tvDate.text = currentItem.start_date
        holder.tvTime.text = "23:00 WIB"
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }
}
