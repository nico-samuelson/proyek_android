package proyek.andro.userActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Filter
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.PlayersListAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Organization
import proyek.andro.model.Player
import proyek.andro.model.Team

class TeamProfile : AppCompatActivity() {
    private var players : ArrayList<Player> = ArrayList()
    private var team : Team? = null
    private var orgs : Organization? = null
    lateinit var rvRoasters : RecyclerView


    lateinit var tvName : TextView
    lateinit var tvFounded : TextView
    lateinit var tvCoach : TextView
    lateinit var tvLocation : TextView
    lateinit var tvWebsite : TextView
    lateinit var iconTeam : ImageView
    lateinit var tvDescription : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_profile)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        tvName = findViewById(R.id.tvName)
        tvFounded = findViewById(R.id.tvFounded)
        tvCoach = findViewById(R.id.tvCoach)
        tvLocation = findViewById(R.id.tvLocation)
        tvWebsite = findViewById(R.id.tvWebsite)
        iconTeam = findViewById(R.id.iconTeam)
        tvDescription = findViewById(R.id.tvDescription)

        CoroutineScope(Dispatchers.Main).launch {
            team = Team().find(intent.getStringExtra("team").toString())
            orgs = Organization().find(team!!.organization)
            players = Player().get(
                filter = Filter.equalTo("team", team!!.id),
                order = arrayOf(arrayOf("team", "ASC"))
            )

            tvName.text = team!!.name
            tvFounded.text = team!!.founded
            tvCoach.text = team!!.coach
            tvLocation.text = orgs!!.location
            tvWebsite.text = orgs!!.website
            tvDescription.text = orgs!!.description

            tvWebsite.setTextColor(resources.getColor(R.color.primary, null))
            tvWebsite.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(orgs!!.website)
                startActivity(intent)
            }

            val logo = StorageHelper().getImageURI(orgs!!.logo, "logo/orgs")

            Picasso.get()
                .load(logo)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(iconTeam, object : Callback{
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        Picasso.get()
                            .load(logo)
                            .placeholder(R.drawable.card_placeholder)
                            .into(iconTeam)
                    }
                })

            rvRoasters = findViewById(R.id.rv_team_roaster)
            rvRoasters.layoutManager = LinearLayoutManager(this@TeamProfile, RecyclerView.HORIZONTAL, false)

            rvRoasters.adapter = PlayersListAdapter(players)
        }

//        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
//        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
//        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
//        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
//        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
    }
}