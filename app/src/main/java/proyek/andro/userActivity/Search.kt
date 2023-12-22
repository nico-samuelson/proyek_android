package proyek.andro.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.ListAdapter
import proyek.andro.model.Game
import proyek.andro.model.Player
import proyek.andro.model.Tournament

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // back button pressed
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // arraylist to hold data
        var games : ArrayList<Game> = ArrayList()
        var tournaments : ArrayList<Tournament> = ArrayList()
        var players : ArrayList<Player> = ArrayList()

        // search result layout groups
        val tournament_group : LinearLayout = findViewById(R.id.tournament_result)
        val player_group : LinearLayout = findViewById(R.id.player_result)
        val game_group : LinearLayout = findViewById(R.id.game_result)
        val empty_result : LinearLayout = findViewById(R.id.empty_search_layout)

        player_group.visibility = LinearLayout.GONE
        tournament_group.visibility = LinearLayout.GONE
        game_group.visibility = LinearLayout.GONE

        // search result rv
        val tournament_result : RecyclerView = findViewById(R.id.tournament_search_results)
        val player_result : RecyclerView = findViewById(R.id.players_search_results)
        val game_result : RecyclerView = findViewById(R.id.games_search_results)

        // set rv layout manager
        tournament_result.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        player_result.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        game_result.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // create rv adapter
        var adapterT = ListAdapter(tournaments.map { it.logo }, tournaments.map { it.name }, tournaments.map { "Tournament" }, "logo/tournaments")
        var adapterP = ListAdapter(players.map { it.photo }, players.map { it.name }, players.map { "Player" }, "")
        var adapterG = ListAdapter(games.map { it.logo }, games.map { it.name }, games.map { "Game" }, "logo/games")

        // set adapter onclick
        adapterT.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
//                val intent = Intent(this@Search, TournamentDetail::class.java)
//                intent.putExtra("title", data)
//                startActivity(intent)
            }

            override fun delData(pos: Int) {}
        })
        adapterP.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
//                val intent = Intent(this@Search, PlayerDetail::class.java)
//                intent.putExtra("title", data)
//                startActivity(intent)
            }

            override fun delData(pos: Int) {}
        })
        adapterG.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
//                val intent = Intent(this@Search, GameDetail::class.java)
//                intent.putExtra("title", data)
//                startActivity(intent)
            }

            override fun delData(pos: Int) {}
        })

        tournament_result.adapter = adapterT
        player_result.adapter = adapterP
        game_result.adapter = adapterG

        val search_view = findViewById<SearchView>(R.id.search_view)
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // reset rv states
                games.clear()
                tournaments.clear()
                players.clear()
                player_group.visibility = LinearLayout.GONE
                tournament_group.visibility = LinearLayout.GONE
                game_group.visibility = LinearLayout.GONE
                empty_result.visibility = LinearLayout.GONE

                // fetch data
                CoroutineScope(Dispatchers.Main).launch {
                    games = Game().get(
                        filter = Filter.and(
                            Filter.greaterThanOrEqualTo("name", query.toString()),
                            Filter.lessThanOrEqualTo("name", query.toString() + "\uf8ff")
                        ),
                        limit = 100,
                        order = arrayOf(arrayOf("name", "asc"))
                    )
                    tournaments = Tournament().get(
                        filter = Filter.and(
                            Filter.greaterThanOrEqualTo("name", query.toString()),
                            Filter.lessThanOrEqualTo("name", query.toString() + "\uf8ff")
                        ),
                        limit = 100,
                        order = arrayOf(arrayOf("name", "asc"))
                    )
                    players = Player().get(
                        filter = Filter.and(
                            Filter.greaterThanOrEqualTo("nickname", query.toString()),
                            Filter.lessThanOrEqualTo("nickname", query.toString() + "\uf8ff")
                        ),
                        limit = 100,
                        order = arrayOf(arrayOf("nickname", "asc"))
                    )

                    Log.d("search_query", games.toString())
                    Log.d("search_query", tournaments.toString())
                    Log.d("search_query", players.toString())
                    if (games.size == 0 && tournaments.size == 0 && players.size == 0) {
                        empty_result.visibility = LinearLayout.VISIBLE
                    }
                    else {
                        if (players.size > 0) player_group.visibility = LinearLayout.VISIBLE
                        if (tournaments.size > 0) tournament_group.visibility = LinearLayout.VISIBLE
                        if (games.size > 0) game_group.visibility = LinearLayout.VISIBLE
                    }

                    Log.d("search_query", games.map { it.logo }.toString())
                    adapterT.setData(tournaments.map { it.logo }, tournaments.map { it.name }, tournaments.map { "Tournament" })
                    adapterP.setData(players.map { it.photo }, players.map { it.nickname }, players.map { "Player" })
                    adapterG.setData(games.map { it.logo }, games.map { it.name }, games.map { "Game" })
                }

                // clear on screen keyboard to prevent this method from being called twice
                search_view.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do something when text changes
//                Log.d("search_query", newText.toString())
                return true
            }
        })
    }
}