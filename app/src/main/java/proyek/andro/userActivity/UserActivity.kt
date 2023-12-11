package proyek.andro.userActivity

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.Login
import proyek.andro.R
import proyek.andro.adminActivity.AdminActivity
import proyek.andro.model.Game
import proyek.andro.model.Match
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import proyek.andro.model.User
import proyek.andro.model.UserFavorite
import java.time.LocalDate

class UserActivity : AppCompatActivity() {
    private var tournaments = ArrayList<Tournament>()
    private var tournamentBanners = ArrayList<Uri>()
    private var tournamentLogos = ArrayList<Uri>()

    private var games = ArrayList<Game>()
    private var gameBanners = ArrayList<Uri>()

    private var teams = ArrayList<Team>()
    private var matches = ArrayList<Match>()

    private var user : User? = null
    private var favorites = ArrayList<UserFavorite>()

    private var selectedGame = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val sp = getSharedPreferences("login_session", MODE_PRIVATE)

        // log in as user
        val job = CoroutineScope(Dispatchers.Main).launch {
            val users = User().get<User>(
                filter = Filter.and(
                    Filter.equalTo("role", 0L),
                    Filter.equalTo("email", sp.getString("email", null)),
                    Filter.equalTo("remember_token", sp.getString("remember_token", null))
                )
            )

            if (users.size == 0) {
                var intent = Intent(this@UserActivity, Login::class.java)
                intent.putExtra("timed_out", true)
                startActivity(intent)
            }
            else {
                user = users.get(0)
                favorites = UserFavorite().get(
                    filter = Filter.equalTo("user", user!!.id),
                    order = arrayOf(arrayOf("user", "desc"))
                )
            }
        }

        job.invokeOnCompletion {
            val mFragmentManager = supportFragmentManager
            val home = UserHomepageFr()

            mFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, home, home::class.java.simpleName)
                .addToBackStack(null)
                .commit()

            // navbar styling
            val navbar: NavigationBarView = findViewById(R.id.bottom_navigation)
            navbar.itemIconTintList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(0)
                ),
                intArrayOf(
                    ContextCompat.getColor(this, R.color.primary),
                    ContextCompat.getColor(this, R.color.disabled)
                )
            )
            navbar.isItemActiveIndicatorEnabled = false
            navbar.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED

            // navbar on click listener
            navbar.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_1 -> {
                        val home = UserHomepageFr()

                        mFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, home, home::class.java.simpleName)
                            .commit()
                        true
                    }

                    R.id.item_2 -> {
                        val explore = ExploreFr()

                        mFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, explore, explore::class.java.simpleName)
                            .commit()
                        true
                    }

                    R.id.item_3 -> {
                        val news = NewsFr()

                        mFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, news, news::class.java.simpleName)
                            .commit()
                        true
                    }

                    R.id.item_4 -> {
                        val profile = ProfileFr()

                        mFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, profile, profile::class.java.simpleName)
                            .commit()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    fun setTournaments(tournaments: ArrayList<Tournament>) {
        this.tournaments = tournaments
    }

    fun setGames(games: ArrayList<Game>) {
        this.games = games
    }

    fun setTeams(teams: ArrayList<Team>) {
        this.teams = teams
    }

    fun setMatches(matches: ArrayList<Match>) {
        this.matches = matches
    }

    fun getTournaments(): ArrayList<Tournament> {
        return this.tournaments
    }

    fun getGames(): ArrayList<Game> {
        return this.games
    }

    fun getTeams(): ArrayList<Team> {
        return this.teams
    }

    fun getMatches(): ArrayList<Match> {
        return this.matches
    }

    fun getTournamentBanners(): ArrayList<Uri> {
        return this.tournamentBanners
    }

    fun getTournamentLogos(): ArrayList<Uri> {
        return this.tournamentLogos
    }

    fun getGameBanners(): ArrayList<Uri> {
        return this.gameBanners
    }

    fun setTournamentBanners(banners: ArrayList<Uri>) {
        this.tournamentBanners = banners
    }

    fun setTournamentLogos(logos: ArrayList<Uri>) {
        this.tournamentLogos = logos
    }

    fun setGameBanners(banners: ArrayList<Uri>) {
        this.gameBanners = banners
    }

    fun setSelectedGame(game: Int) {
        this.selectedGame = game
    }

    fun getSelectedGame(): Int {
        return this.selectedGame
    }

    fun getData(): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            // get data
            teams = Team().get(limit = 100)
            tournaments = Tournament().get(
                filter = Filter.lessThan("status", 3),
                order = arrayOf(arrayOf("status", "asc")),
                limit = 50
            )
            matches = Match().get(
                filter = Filter.inArray("tournament", tournaments.map { it.id }),
                order = arrayOf(arrayOf("time", "desc")),
                limit = 5
            )
            val tempGame : ArrayList<Game> = Game().get()

            val gameFavorites = tempGame.filter { it.id in favorites.map { fav -> fav.game } }
            val gameNonFavorite = tempGame.filter { it.id !in favorites.map { fav -> fav.game } }
            gameFavorites.map { games.add(it) }
            gameNonFavorite.map { games.add(it) }
        }
    }

//    fun getExploreData(game : String): Job {
//        return CoroutineScope(Dispatchers.Main).launch {
//            tournaments = Tournament().get(
//                filter = Filter.and(
//                    Filter.equalTo("game", games.filter { it.name == game }.get(0).id),
//                    Filter.lessThan("status", 3)
//                ),
//                limit = 5,
//                order = arrayOf(arrayOf("status", "asc"), arrayOf("start_date", "desc"))
//            )
//        }
//    }
}