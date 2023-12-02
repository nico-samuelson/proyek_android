package proyek.andro.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import proyek.andro.R
import proyek.andro.model.Game

class GameCarouselAdapter (
    private val games : ArrayList<Game>,
    private val banners : ArrayList<Uri>
) : RecyclerView.Adapter<GameCarouselAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    val storageRef = FirebaseStorage.getInstance().reference

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var banner : ImageView = itemView.findViewById(R.id.game_carousel_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_game_carousel, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val banner = games.get(position).banner

//        Picasso.get().load(banners.get(position)).into(holder.banner)
        storageRef.child("banner/games/${banner}")
            .downloadUrl
            .addOnSuccessListener {
                Picasso.get()
                    .load(it)
                    .placeholder(R.drawable.logo_m5)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.banner, object : Callback {
                        override fun onSuccess() {
                            Log.d("Picasso", "Image loaded from cache")
                        }

                        override fun onError(e: Exception?) {
                            Picasso.get().load(it).into(holder.banner)
                        }
                    })
            }

        holder.banner.setOnClickListener {
            onItemClickCallback.onItemClicked(games.get(position).id)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}