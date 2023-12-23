package proyek.andro.adapter

import android.content.Intent
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
import proyek.andro.model.Game
import proyek.andro.model.Team
import java.lang.Exception

class TeamListAdapter(
    private val teams: ArrayList<Team>,
    private val games : ArrayList<Game>
) : RecyclerView.Adapter<TeamListAdapter.ParticipantViewHolder>() {
    inner class ParticipantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvParticipant: TextView = itemView.findViewById(R.id.tvParticipant)
        val team_logo: ImageView = itemView.findViewById(R.id.team_logo)

        init {
            itemView.setOnClickListener {
                val currentItem = teams[adapterPosition]

                val intent = Intent(itemView.context, proyek.andro.userActivity.TeamProfile::class.java)
                intent.putExtra("team", currentItem.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val currentItem = teams[position]
        holder.tvParticipant.text = games.filter { it.id == currentItem.game }[0].name

        CoroutineScope(Dispatchers.Main).launch {
            val imageUri = StorageHelper().getImageURI(
                games.filter { it.id == currentItem.game }[0].logo,
                "logo/games/"
            )

            Picasso.get()
                .load(imageUri)
                .fit()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.team_logo, object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        Picasso.get().load(imageUri).fit().into(holder.team_logo)
                    }
                })
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamListAdapter.ParticipantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_participants_carousel, parent, false)

        return ParticipantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return teams.size
    }
}