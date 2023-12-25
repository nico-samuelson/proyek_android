package proyek.andro.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.firebase.firestore.Filter
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.MatchCarouselAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Match
import proyek.andro.model.Player
import proyek.andro.model.PlayerHistory
import proyek.andro.model.Team

class PlayerProfile : AppCompatActivity() {
    private var playerHistories : ArrayList<PlayerHistory> = ArrayList()
    private var matches : ArrayList<Match> = ArrayList()
    private var team : Team? = null
    private var player : Player? = null

    lateinit var playerHistoryRV : RecyclerView
    lateinit var parent : UserActivity

    lateinit var tvNickname : TextView
    lateinit var tvName : TextView
    lateinit var tvTeam : TextView
    lateinit var tvNationality : TextView
    lateinit var tvPosition : TextView
    lateinit var ivCaptain : ImageView
    lateinit var ivPlayerPhoto : ImageView

    var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        tvNickname = findViewById(R.id.tvNickname)
        tvName = findViewById(R.id.tvName)
        tvTeam = findViewById(R.id.tvTeam)
        tvNationality = findViewById(R.id.tvNationality)
        tvPosition = findViewById(R.id.tvPosition)
        ivCaptain = findViewById(R.id.ivCaptain)
        ivPlayerPhoto = findViewById(R.id.ivPlayerPhoto)

        tvTeam.setTextColor(resources.getColor(R.color.primary, null))

        CoroutineScope(Dispatchers.Main).launch {
            if (intent.getStringExtra("player") == null) {
                player = Player().get<Player>(
                    filter = Filter.equalTo("nickname", intent.getStringExtra("name")!!),
                    order = arrayOf(arrayOf("nickname", "ASC"))
                )[0]
            } else {
                player = Player().find(intent.getStringExtra("player")!!)
            }

//            player = Player().find(intent.getStringExtra("player")!!)
            team = Team().find(player!!.team)
            playerHistories = PlayerHistory().get(
                filter = Filter.equalTo("player", player!!.id),
                limit = 10,
                order = arrayOf(arrayOf("player", "ASC"))
            )
        }.invokeOnCompletion {
            Log.d("player", "player: ${playerHistories?.size}")
            CoroutineScope(Dispatchers.Main).launch {
                matches = Match().get(
                    filter = Filter.inArray("id", playerHistories.map { it.match }),
                    order = arrayOf(arrayOf("time", "DESC")),
                )
                val matchTeams = Team().get<Team>(
                    filter = Filter.inArray("id", matches.map { it.team1 }.union(matches.map { it.team2 }).toList())
                )

                tvNickname.text = player!!.nickname
                tvName.text = player!!.name
                tvTeam.text = team!!.name
                tvNationality.text = player!!.nationality
                tvPosition.text = player!!.position

                tvTeam.setOnClickListener{
                    val intent = Intent(this@PlayerProfile, TeamProfile::class.java)
                    intent.putExtra("team", team!!.id)
                    startActivity(intent)
                }

                if (player!!.captain) ivCaptain.visibility = View.VISIBLE
                else ivCaptain.visibility = View.GONE

                val imageUri = StorageHelper().getImageURI(player!!.photo, "")
                Picasso.get()
                    .load(imageUri)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(ivPlayerPhoto, object : Callback {
                        override fun onSuccess() {}

                        override fun onError(e: Exception?) {
                            Picasso.get().load(imageUri).into(ivPlayerPhoto)
                        }
                    })

                playerHistoryRV = findViewById(R.id.rv_PlayerHistory_carousel)
                playerHistoryRV.layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
                CarouselSnapHelper().attachToRecyclerView(playerHistoryRV)
                playerHistoryRV.adapter = MatchCarouselAdapter(matches, matchTeams)
            }
        }
    }
}