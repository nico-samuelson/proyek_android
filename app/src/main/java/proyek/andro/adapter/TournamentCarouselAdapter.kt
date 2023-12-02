package proyek.andro.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Tournament
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class TournamentCarouselAdapter (
    private val tournaments : ArrayList<Tournament>,
    private val banners : ArrayList<RequestCreator>,
    private val logos : ArrayList<RequestCreator>
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

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val start_date = LocalDate.parse(tournaments.get(position).start_date)
        val end_date = LocalDate.parse(tournaments.get(position).end_date)
        val start = start_date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + start_date.dayOfMonth
        val end = end_date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + end_date.dayOfMonth

        holder.name.text = tournaments.get(position).name
        holder.date.text = "${start} - ${end}, ${end_date.year}"

        val banner = banners.get(position)
        val logo = logos.get(position)

        banners.get(position)
            .into(holder.image, object : Callback {
                override fun onSuccess() {
                    Log.d("Picasso", "Image loaded from cache")
                }

                override fun onError(e: Exception?) {
                    storageRef.child("banner/tournaments/${banner}")
                        .downloadUrl
                        .addOnSuccessListener {
                            Picasso.get().load(it).into(holder.image)
                        }
                }
            })

        logos.get(position)
            .into(holder.logo, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    storageRef.child("logo/tournaments/${logo}")
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
}