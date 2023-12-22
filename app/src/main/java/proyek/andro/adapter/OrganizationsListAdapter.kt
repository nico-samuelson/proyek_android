package proyek.andro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.model.Organization

class OrganizationsListAdapter(
    private val organizatoins : ArrayList<Organization>
) : RecyclerView.Adapter<OrganizationsListAdapter.ListViewHolder>(){
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvNickname : TextView = itemView.findViewById(R.id.tvNickname)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = organizatoins[position]
                    val intent = Intent(itemView.context, proyek.andro.userActivity.OrganizationProfile::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrganizationsListAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_players_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrganizationsListAdapter.ListViewHolder, position: Int) {
        val currentItem = organizatoins[position]
        holder.tvNickname.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return organizatoins.size
    }
}