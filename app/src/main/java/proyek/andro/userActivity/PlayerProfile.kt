package proyek.andro.userActivity

import android.content.Intent
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.GameCarouselAdapter
import proyek.andro.adapter.MatchCarouselAdapter
import proyek.andro.adapter.PlayersListAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Game
import proyek.andro.model.Player
import proyek.andro.model.PlayerHistory

class PlayerProfile : AppCompatActivity() {
    private var playerHistories : ArrayList<Player> = ArrayList()
    lateinit var playerHistoryRV : RecyclerView
    lateinit var parent : UserActivity
    var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
//            val intent = Intent(this, TeamProfile::class.java)
//            startActivity(intent)
        }

        playerHistoryRV = findViewById(R.id.rv_PlayerHistory_carousel)
        playerHistoryRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        playerHistories.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
        playerHistories.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
        playerHistories.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
        playerHistories.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))

        playerHistoryRV.adapter = PlayersListAdapter(playerHistories)
    }
}