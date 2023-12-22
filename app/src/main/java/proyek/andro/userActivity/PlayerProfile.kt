package proyek.andro.userActivity

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.GameCarouselAdapter
import proyek.andro.adapter.MatchCarouselAdapter
import proyek.andro.adapter.PlayersListAdapter
import proyek.andro.adapter.ScheduleUpcomingAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Game
import proyek.andro.model.Match
import proyek.andro.model.Player
import proyek.andro.model.PlayerHistory
import proyek.andro.model.Team
import java.text.FieldPosition

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

        CoroutineScope(Dispatchers.Main).launch {
            player = Player().find(intent.getStringExtra("player")!!)
            team = Team().find(player!!.team)
            playerHistories = PlayerHistory().get(
                filter = Filter.equalTo("player", player!!.id),
                limit = 10,
                order = arrayOf(arrayOf("player", "ASC"))
            )
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

            if (player!!.captain) ivCaptain.visibility = View.VISIBLE
            else ivCaptain.visibility = View.GONE

            playerHistoryRV = findViewById(R.id.rv_PlayerHistory_carousel)
            playerHistoryRV.layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
            CarouselSnapHelper().attachToRecyclerView(playerHistoryRV)
            playerHistoryRV.adapter = MatchCarouselAdapter(matches, matchTeams)

//            playerHistories.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
//            playerHistories.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
//            playerHistories.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
//            playerHistories.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
//
//            playerHistoryRV.adapter = PlayersListAdapter(playerHistories)
        }
    }
}