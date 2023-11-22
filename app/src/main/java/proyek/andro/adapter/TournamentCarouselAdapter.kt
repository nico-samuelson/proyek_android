package proyek.andro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.model.Tournament

class TournamentCarouselAdapter (
    private val context : Context,
    private val tournaments : ArrayList<Tournament>
) : RecyclerView.Adapter<TournamentCarouselAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
        fun delData(pos : Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _image: ImageView = itemView.findViewById(R.id.carousel_img)
        val name : TextView = itemView.findViewById(R.id.carousel_tournament_name)
        val date : TextView = itemView.findViewById(R.id.carousel_tournament_date)
        val game : TextView = itemView.findViewById(R.id.carousel_game_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.carousel, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val image = tournaments.get(position).banner

        holder.name.text = tournaments.get(position).name
        holder.date.text = "${tournaments.get(position).start_date} - ${tournaments.get(position).end_date}"
        holder.game.text = tournaments.get(position).game.name
        val imageRes = context.resources.getIdentifier(image, "drawable", context.packageName)
        holder._image.setImageResource(imageRes)

        holder._image.setOnClickListener {
            onItemClickCallback.onItemClicked(tournaments.get(position).banner)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}