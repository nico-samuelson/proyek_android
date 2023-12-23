package proyek.andro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import proyek.andro.model.UserFavorite

class FavoriteAdapter(
    private val favorites : ArrayList<UserFavorite>,
    private val games : ArrayList<Game>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    private lateinit var onItemClickCallback: FavoriteAdapter.OnItemClickCallback
    interface OnItemClickCallback {
        fun onItemClicked(data: UserFavorite)
    }
    inner class FavoriteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imgBanner : ImageView = itemView.findViewById(R.id.imgBanner)

        init {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        val currentItem = favorites[position]

        CoroutineScope(Dispatchers.Main).launch {
            val imageUri = StorageHelper().getImageURI(
                games.filter {
                    currentItem.game == it.id
                }[0].banner,"banner/games/"
            )

            Picasso.get()
                .load(imageUri)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.imgBanner, object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        Picasso.get().load(imageUri).into(holder.imgBanner)
                    }
                })
        }

        holder.imgBanner.setOnClickListener {
            onItemClickCallback.onItemClicked(favorites.get(position))
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: FavoriteAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int {
        return favorites.size
    }
}