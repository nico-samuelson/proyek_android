package proyek.andro.adapter

import android.annotation.SuppressLint
import android.net.Uri
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

class TournamentCarouselAdapter (
    private val tournaments : ArrayList<Tournament>,
    private val banners : ArrayList<Uri>,
    private val logos : ArrayList<Uri>
) : RecyclerView.Adapter<TournamentCarouselAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    val storageRef = FirebaseStorage.getInstance().reference

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
        fun delData(pos : Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.carousel_img)
        val logo : ImageView = itemView.findViewById(R.id.carousel_tournament_logo)
        val name : TextView = itemView.findViewById(R.id.carousel_tournament_name)
        val date : TextView = itemView.findViewById(R.id.carousel_tournament_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_tournament_carousel, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val start_date = if (tournaments.get(position).start_date != "") LocalDate.parse(tournaments.get(position).start_date) else LocalDate.now()
        val end_date = if (tournaments.get(position).start_date != "") LocalDate.parse(tournaments.get(position).end_date) else LocalDate.now()
        val start = start_date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + start_date.dayOfMonth
        val end = end_date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + end_date.dayOfMonth

        holder.name.text = tournaments.get(position).name
        holder.date.text = if (tournaments.get(position).start_date != "") "${start} - ${end}, ${end_date.year}" else "TBA"

//        val banner = banners.get(position)
//        val logo = logos.get(position)

        Picasso.get()
            .load(banners.get(position))
            .placeholder(R.drawable.card_placeholder)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.image, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    storageRef.child("banner/tournaments/${tournaments.get(position).banner}")
                        .downloadUrl
                        .addOnSuccessListener {
                            Picasso.get().load(it).into(holder.image)
                        }
                }
            })

        Picasso.get()
            .load(logos.get(position))
            .placeholder(R.drawable.card_placeholder)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.logo, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    storageRef.child("logo/tournaments/${tournaments.get(position).logo}")
                        .downloadUrl
                        .addOnSuccessListener {
                            Picasso.get().load(it).into(holder.logo)
                        }
                }
            })

        holder.image.setOnClickListener {
            onItemClickCallback.onItemClicked(tournaments.get(position).banner)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemViewType(position: Int): Int {
        return 2
    }
}