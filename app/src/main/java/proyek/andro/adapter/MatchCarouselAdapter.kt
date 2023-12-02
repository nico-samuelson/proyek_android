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
import proyek.andro.model.Match
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.coroutines.resume

class MatchCarouselAdapter (
    private val matches : ArrayList<Match>,
    private val teams : ArrayList<Team>,
) : RecyclerView.Adapter<MatchCarouselAdapter.ListViewHolder>() {
    val storageRef = FirebaseStorage.getInstance().reference

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.carousel_match_name)
        var team1 : ImageView = itemView.findViewById(R.id.carousel_match_team_1)
        var team2 : ImageView = itemView.findViewById(R.id.carousel_match_team_2)
        var team1Name : TextView = itemView.findViewById(R.id.carousel_match_team_1_name)
        var team2Name : TextView = itemView.findViewById(R.id.carousel_match_team_2_name)
        var team1Score : TextView = itemView.findViewById(R.id.carousel_match_team_1_score)
        var team2Score : TextView = itemView.findViewById(R.id.carousel_match_team_2_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_match_carousel, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val match = matches.get(position)
        val scores = match.score.split("-")
        val team1 = teams.filter { it.id == match.team1 }.first()
        val team2 = teams.filter { it.id == match.team2 }.first()


        Log.d("team", teams.filter { it.id == match.team1 }.toString())
        holder.name.text = match.name
        holder.team1Name.text = team1.name
        holder.team2Name.text = team2.name
        holder.team1Score.text = match.score.split("-")[0]
        holder.team2Score.text = match.score.split("-")[1]

        if (scores[0] > scores[1]) {
            holder.team1Score.setTextColor(holder.itemView.context.resources.getColor(R.color.green))
            holder.team2Score.setTextColor(holder.itemView.context.resources.getColor(R.color.red))
        } else if (scores[0] < scores[1]) {
            holder.team1Score.setTextColor(holder.itemView.context.resources.getColor(R.color.red))
            holder.team2Score.setTextColor(holder.itemView.context.resources.getColor(R.color.green))
        } else {
            holder.team1Score.setTextColor(holder.itemView.context.resources.getColor(R.color.disabled))
            holder.team2Score.setTextColor(holder.itemView.context.resources.getColor(R.color.disabled))
        }

        storageRef.child("logo/orgs/${team1.logo}")
            .downloadUrl
            .addOnSuccessListener { uri ->
                    Picasso.get()
                        .load(uri)
                        .placeholder(R.drawable.card_placeholder)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.team1, object : Callback {
                            override fun onSuccess() {
                                Log.d("load image", "success")
                            }

                            override fun onError(e: Exception?) {
                                Picasso.get()
                                    .load(uri)
                                    .placeholder(R.drawable.card_placeholder)
                                    .into(holder.team1)
                            }
                        })
            }
            .addOnFailureListener {
                Log.e("preload image", it.message.toString())
            }

        storageRef.child("logo/orgs/${team2.logo}")
            .downloadUrl
            .addOnSuccessListener { uri ->
                Picasso.get()
                    .load(uri)
                    .placeholder(R.drawable.card_placeholder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.team2, object : Callback {
                        override fun onSuccess() {
                            Log.d("load image", "success")
                        }

                        override fun onError(e: Exception?) {
                            Picasso.get()
                                .load(uri)
                                .placeholder(R.drawable.card_placeholder)
                                .into(holder.team1)
                        }
                    })
            }
            .addOnFailureListener {
                Log.e("preload image", it.message.toString())
            }
    }
}