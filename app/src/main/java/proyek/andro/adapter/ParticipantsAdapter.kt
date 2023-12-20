package proyek.andro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.model.Participant

class ParticipantsAdapter(
    private val participants : ArrayList<Participant>
) : RecyclerView.Adapter<ParticipantsAdapter.ParticipantViewHolder>() {
    inner class ParticipantViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvParticipant : TextView = itemView.findViewById(R.id.tvParticipant)

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Clicked: ${tvParticipant.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val currentItem = participants[position]
        holder.tvParticipant.text = currentItem.team

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