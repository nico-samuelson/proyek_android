package proyek.andro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.model.Tournament

class TourneyPageAdapter(
    private val tournaments : ArrayList<Tournament>
) : RecyclerView.Adapter<TourneyPageAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): TourneyPageAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_tourneypage_carousel, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourneyPageAdapter.ListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }
}