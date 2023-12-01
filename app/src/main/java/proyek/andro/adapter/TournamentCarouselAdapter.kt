package proyek.andro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.model.Tournament
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class TournamentCarouselAdapter (
    private val context : Context,
    private val tournaments : ArrayList<Tournament>,
) : RecyclerView.Adapter<TournamentCarouselAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

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
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.carousel, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val banner = tournaments.get(position).banner
        val logo = tournaments.get(position).logo
        val start_date = LocalDate.parse(tournaments.get(position).start_date)
        val end_date = LocalDate.parse(tournaments.get(position).end_date)
        val start = start_date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + start_date.dayOfMonth
        val end = end_date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + end_date.dayOfMonth

        holder.name.text = tournaments.get(position).name
        holder.date.text = "${start} - ${end}, ${end_date.year}"

        val bannerRes = context.resources.getIdentifier(banner, "drawable", context.packageName)
        val logoRes = context.resources.getIdentifier(logo, "drawable", context.packageName)

        holder.image.setImageResource(bannerRes)
        holder.logo.setImageResource(logoRes)

        holder.image.setOnClickListener {
            onItemClickCallback.onItemClicked(tournaments.get(position).banner)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}