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
import proyek.andro.adapter.TeamListAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Game
import proyek.andro.model.Organization
import proyek.andro.model.Team

class OrganizationPage : AppCompatActivity() {
    private var teams : ArrayList<Team> = ArrayList()
    private var games : ArrayList<Game> = ArrayList()
    private var orgs : Organization? = null
    lateinit var rvTeams : RecyclerView

    lateinit var tvName : TextView
    lateinit var tvFounded : TextView
    lateinit var tvCoach : TextView
    lateinit var tvLocation : TextView
    lateinit var tvWebsite : TextView
    lateinit var iconTeam : ImageView
    lateinit var tvDescription : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_page)

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
            orgs = Organization().find(intent.getStringExtra("orgs").toString())
            teams = Team().get(
                filter = Filter.equalTo("organization", orgs!!.id),
                order = arrayOf(arrayOf("organization", "ASC"))
            )
            games = Game().get()

            tvName.text = orgs!!.name
            tvFounded.text = orgs!!.founded
            tvCoach.text = orgs!!.ceo
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

            rvTeams = findViewById(R.id.rv_team_roaster)
            rvTeams.layoutManager = LinearLayoutManager(
                this@OrganizationPage,
                RecyclerView.HORIZONTAL,
                false
            )

            rvTeams.adapter = TeamListAdapter(teams, games)
        }
    }
}