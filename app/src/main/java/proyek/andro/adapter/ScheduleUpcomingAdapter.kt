package proyek.andro.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Match
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ScheduleUpcomingAdapter(
    private val matches : ArrayList<Match>,
    private val teams : ArrayList<Team>
) : RecyclerView.Adapter<ScheduleUpcomingAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val ivTeam1 : ImageView = itemView.findViewById(R.id.ivTeam1)
        val tvTeam1 : TextView = itemView.findViewById(R.id.tvTeam1)

        val ivTeam2 : ImageView = itemView.findViewById(R.id.ivTeam2)
        val tvTeam2 : TextView = itemView.findViewById(R.id.tvTeam2)

        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvTime : TextView = itemView.findViewById(R.id.tvWaktu)

        val tvStatus : TextView = itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleUpcomingAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_schedule_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleUpcomingAdapter.ListViewHolder, position: Int) {
        val currentItem = matches[position]
        val team1 = teams.filter { it.id == currentItem.team1 }.first()
        val team2 = teams.filter { it.id == currentItem.team2 }.first()

        Log.d("match", "Matches: ${matches.size}")
        Log.d("match", "Teams: ${teams.size}")

        val match_time = currentItem.time.split(" UTC")
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy-HH:mm")

        val utcDateTime = ZonedDateTime.parse(match_time[0], inputFormatter.withZone(ZoneId.of("UTC")))
        val localDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Jakarta"))
        val formattedLocalDateTime = localDateTime.format(outputFormatter)

        holder.tvDate.text = formattedLocalDateTime.split("-")[0]
        holder.tvTime.text = "${formattedLocalDateTime.split("-")[1]} WIB"
//        holder.tvTime.text = "${formattedLocalDateTime.split("-")[1]} \n${ZoneId.systemDefault().id}"
        holder.tvTeam1.text = team1.name
        holder.tvTeam2.text = team2.name

        if (currentItem.status == 0L) {
            holder.tvStatus.text = "Upcoming"
        }
        else if (currentItem.status == 1L) {
            holder.tvStatus.text = "Ongoing"
        }
        else if (currentItem.status == 2L) {
            holder.tvStatus.text = "Finished"
        }
        else if (currentItem.status == 3L) {
            holder.tvStatus.text = "Postponed"
        }
        else if (currentItem.status == 4L) {
            holder.tvStatus.text = "Canceled"
        }

        CoroutineScope(Dispatchers.Main).launch {
            val team1_logo = StorageHelper().getImageURI(team1.logo, "logo/orgs")
            val team2_logo = StorageHelper().getImageURI(team2.logo, "logo/orgs")

            Picasso.get()
                .load(team1_logo)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.ivTeam1, object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        Picasso.get().load(team1_logo).into(holder.ivTeam1)
                    }
                })

            Picasso.get()
                .load(team2_logo)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.ivTeam2, object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        Picasso.get().load(team2_logo).into(holder.ivTeam2)
                    }
                })
        }
    }

    override fun getItemCount(): Int {
        return matches.size
    }
}
