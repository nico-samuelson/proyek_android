package proyek.andro.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import proyek.andro.R

class SimpleListAdapter(
    private var images: List<String>,
    private var titles: List<String>,
    private var imagePath: String,
) : RecyclerView.Adapter<SimpleListAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    val storageRef = FirebaseStorage.getInstance().reference

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
        fun delData(pos: Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.listImage)
        val title: TextView = itemView.findViewById(R.id.listName)
        val delBtn: ImageView = itemView.findViewById(R.id.delBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_simple_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.title.text = titles.get(position)

        storageRef.child(imagePath + images.get(position)).downloadUrl
            .addOnSuccessListener {
                Picasso.get()
                    .load(it)
                    .placeholder(R.drawable.card_placeholder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.image, object : Callback {
                        override fun onSuccess() {}
                        override fun onError(e: Exception?) {
                            Picasso.get()
                                .load(it)
                                .placeholder(R.drawable.card_placeholder)
                                .into(holder.image)
                        }
                    })
            }
            .addOnFailureListener {
                Picasso.get()
                    .load(R.drawable.card_placeholder)
                    .into(holder.image)
            }

        holder.title.setOnClickListener { onItemClickCallback.onItemClicked(titles.get(position)) }
        holder.image.setOnClickListener { onItemClickCallback.onItemClicked(titles.get(position)) }
        holder.delBtn.setOnClickListener { onItemClickCallback.delData(position) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(images: List<String>, titles: List<String>) {
        this.images = images
        this.titles = titles

        Log.d("filterPlayers", "adapterNames: ${titles}")
        Log.d("itemCount", "itemCount: ${itemCount}")
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}