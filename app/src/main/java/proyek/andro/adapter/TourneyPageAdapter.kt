package proyek.andro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.userActivity.TournamentExtension.TourneyString

class TourneyPageAdapter(
    private val tournaments : ArrayList<TourneyString>
) : RecyclerView.Adapter<TourneyPageAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJudul : TextView = itemView.findViewById(R.id.tvJudul)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): TourneyPageAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_tourneypage_carousel, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourneyPageAdapter.ListViewHolder, position: Int) {
        val currentItem = tournaments[position]
        holder.tvJudul.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }
}