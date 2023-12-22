package proyek.andro.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.adapter.PlayersListAdapter
import proyek.andro.model.Player

class TeamProfile : AppCompatActivity() {
    private var players : ArrayList<Player> = ArrayList()
    lateinit var rvRoasters : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_profile)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            val intent = Intent(this, TournamentPage::class.java)
            startActivity(intent)
        }

        rvRoasters = findViewById(R.id.rv_team_roaster)
        rvRoasters.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))
        players.add(Player("1", "NaVi", "S1mple", "S1mple", "AWP", "Ukraina", "https://liquipedia.net/commons/images/thumb/1/1d/S1mple_at_BLAST_Premier_Spring_Series_2020.jpg/600px-S1mple_at_BLAST_Premier_Spring_Series_2020.jpg", true, 1))

        rvRoasters.adapter = PlayersListAdapter(players)
    }
}