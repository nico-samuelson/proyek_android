package proyek.andro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Participant
import proyek.andro.model.Team
import java.lang.Exception

class ParticipantsAdapter(
    private val participants : ArrayList<Participant>,
    private val teams : ArrayList<Team>
) : RecyclerView.Adapter<ParticipantsAdapter.ParticipantViewHolder>() {
    inner class ParticipantViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvParticipant : TextView = itemView.findViewById(R.id.tvParticipant)
        val team_logo : ImageView = itemView.findViewById(R.id.team_logo)

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Clicked: ${tvParticipant.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val currentItem = participants[position]
        holder.tvParticipant.text = teams.filter { it.id == currentItem.team }[0].name

        CoroutineScope(Dispatchers.Main).launch {
            val imageUri = StorageHelper().getImageURI(
                teams.filter { it.id == currentItem.team }[0].logo,
                "logo/orgs/"
            )

            Picasso.get()
                .load(imageUri)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.team_logo, object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        Picasso.get().load(imageUri).into(holder.team_logo)
                    }
                })
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParticipantsAdapter.ParticipantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_participants_carousel, parent, false)

        return ParticipantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return participants.size
    }
}