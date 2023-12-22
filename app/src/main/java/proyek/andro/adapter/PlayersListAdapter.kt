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
import proyek.andro.model.Player

class PlayersListAdapter(
    private val players : ArrayList<Player>
) : RecyclerView.Adapter<PlayersListAdapter.ListViewHolder>(){
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvNickname : TextView = itemView.findViewById(R.id.tvNickname)
        val tvNationality : TextView = itemView.findViewById(R.id.tvNationality)
        val ivPlayer : ImageView = itemView.findViewById(R.id.ivPlayer)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = players[position]
                    val intent = Intent(itemView.context, proyek.andro.userActivity.PlayerProfile::class.java)
                    intent.putExtra("player", currentItem.id)
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
        holder.tvNationality.text = currentItem.nationality

        CoroutineScope(Dispatchers.Main).launch {
            val imageURI = StorageHelper().getImageURI(currentItem.photo ,"")

            Picasso.get()
                .load(imageURI)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.ivPlayer, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception) {
                        Picasso.get()
                            .load(imageURI)
                            .into(holder.ivPlayer)
                    }
                })
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }
}