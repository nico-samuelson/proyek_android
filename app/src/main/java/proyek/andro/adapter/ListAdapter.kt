package proyek.andro.adapter

import android.annotation.SuppressLint
import android.net.Uri
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
import proyek.andro.model.Tournament
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class ListAdapter (
    private val images : List<String>,
    private val titles : List<String>,
    private val descriptions : List<String>,
    private val imagePath : String,
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    val storageRef = FirebaseStorage.getInstance().reference

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
        fun delData(pos : Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.listImage)
        val title : TextView = itemView.findViewById(R.id.listHeadline)
        val desc : TextView = itemView.findViewById(R.id.listDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.title.text = titles.get(position)
        holder.desc.text = descriptions.get(position)

        storageRef.child(imagePath + "/" + images.get(position)).downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.bg_gradient_1)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.image)
        }.addOnFailureListener {
            Picasso.get()
                .load(images.get(position))
                .placeholder(R.drawable.bg_gradient_1)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.image)
        }
//        Picasso.get()
//            .load(images.get(position))
//            .placeholder(R.drawable.bg_gradient_1)
//            .networkPolicy(NetworkPolicy.OFFLINE)
//            .into(holder.image)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(titles.get(position)) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemViewType(position: Int): Int {
        return 2
    }
}