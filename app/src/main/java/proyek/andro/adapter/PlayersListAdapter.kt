package proyek.andro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.model.Player

class PlayersListAdapter(
    private val players : ArrayList<Player>
) : RecyclerView.Adapter<PlayersListAdapter.ListViewHolder>(){
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvNickname : TextView = itemView.findViewById(R.id.tvNickname)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = players[position]
                    val intent = Intent(itemView.context, proyek.andro.userActivity.PlayerProfile::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayersListAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_players_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayersListAdapter.ListViewHolder, position: Int) {
        val currentItem = players[position]
        holder.tvNickname.text = currentItem.nickname
    }

    override fun getItemCount(): Int {
        return players.size
    }
}