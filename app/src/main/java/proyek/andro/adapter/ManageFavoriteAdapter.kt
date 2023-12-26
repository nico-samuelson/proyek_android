package proyek.andro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import proyek.andro.R
import proyek.andro.model.Game
import proyek.andro.model.News
import proyek.andro.model.UserFavorite

class ManageFavoriteAdapter(
    private val listGames: ArrayList<Game>,
    private val listFavorite: ArrayList<UserFavorite>
) : RecyclerView.Adapter<ManageFavoriteAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: ManageFavoriteAdapter.OnItemClickCallback
    val storageRef = FirebaseStorage.getInstance().reference

    interface OnItemClickCallback {
        fun onItemClicked(data: Game)
        fun delData(pos : Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.tvFavorite)
        var manage: ImageView = itemView.findViewById(R.id.manageFavorite)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageFavoriteAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_manage_favorite, parent, false)
        return ListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return listGames.size
    }

    override fun onBindViewHolder(holder: ManageFavoriteAdapter.ListViewHolder, position: Int) {
        var game = listGames[position]
        holder.title.setText(game.name)
        if (listFavorite.map { it.game == game.id }.first()) {
            holder.manage.setImageResource(R.drawable.ic_delete_24)
        } else {
            holder.manage.setImageResource(R.drawable.add)
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(game) }
    }
    fun setOnItemClickCallback(onItemClickCallback: ManageFavoriteAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


}