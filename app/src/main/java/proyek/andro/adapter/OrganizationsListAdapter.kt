package proyek.andro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Organization

class OrganizationsListAdapter(
    private val organizatoins : ArrayList<Organization>
) : RecyclerView.Adapter<OrganizationsListAdapter.ListViewHolder>(){
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvNickname : TextView = itemView.findViewById(R.id.tvName)
        val ivOrganization : ImageView = itemView.findViewById(R.id.ivOrganization)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = organizatoins[position]
                    val intent = Intent(itemView.context, proyek.andro.userActivity.OrganizationPage::class.java)
                    intent.putExtra("orgs", currentItem.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrganizationsListAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_organizations_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrganizationsListAdapter.ListViewHolder, position: Int) {
        val currentItem = organizatoins[position]
        holder.tvNickname.text = currentItem.name

        CoroutineScope(Dispatchers.Main).launch {
            val imageURI = StorageHelper().getImageURI(currentItem.logo, "logo/orgs")

            Picasso.get()
                .load(imageURI)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.ivOrganization, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        // Do nothing
                    }

                    override fun onError(e: Exception?) {
                        Picasso.get()
                            .load(imageURI)
                            .into(holder.ivOrganization)
                    }
                })
        }
    }

    override fun getItemCount(): Int {
        return organizatoins.size
    }
}