package proyek.andro.userActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.Filter
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.TourneyPageAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Match
import proyek.andro.model.Participant
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import proyek.andro.userActivity.TournamentExtension.MoreInfoFr
import proyek.andro.userActivity.TournamentExtension.OverviewFr
import proyek.andro.userActivity.TournamentExtension.ScheduleFr
import proyek.andro.userActivity.TournamentExtension.TourneyString

class TournamentPage : AppCompatActivity() {
    private var juduls : ArrayList<TourneyString> = ArrayList()
    private lateinit var rvJudul : RecyclerView
    private var tournament : Tournament? = null
    private var participants : ArrayList<Participant> = ArrayList()
    private var teams : ArrayList<Team> = ArrayList()
    private var matches : ArrayList<Match> = ArrayList()

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val currentPosition = (rvJudul.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val nextPosition = if (currentPosition + 1 < juduls.size) currentPosition + 1 else 0
            rvJudul.smoothScrollToPosition(nextPosition)
            handler.postDelayed(this, 3000) // Scrolls every 3 seconds
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_page)

        val backBtn: ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        var imageURI : ArrayList<Uri> = ArrayList()

        CoroutineScope(Dispatchers.Main).launch {
            tournament = Tournament().get<Tournament>(
                filter = Filter.equalTo("name", intent.getStringExtra("tournament")!!),
                order = arrayOf(arrayOf("name", "ASC"))
            ).firstOrNull()
            participants = Participant().get(
                filter = Filter.equalTo("tournament", tournament?.id),
                limit = 100,
                order = arrayOf(arrayOf("tournament", "ASC"))
            )
            teams = Team().get(
                filter = Filter.inArray("id", participants.map { it.team }),
                limit = 100
            )
            matches = Match().get(
                filter = Filter.equalTo("tournament", tournament?.id),
                limit = 500,
                order = arrayOf(arrayOf("tournament", "ASC"))
            )

            Log.d("tournament", "tournament: ${tournament?.name}")
            Log.d("participants", "participants: ${participants.size}")
            Log.d("teams", "teams: ${teams.size}")
            Log.d("matches", "matches: ${matches.size}")

            imageURI = StorageHelper().preloadImages(listOf(tournament?.banner!!), "banner/tournaments")
        }.invokeOnCompletion {
            var banner = findViewById<ImageView>(R.id.tournament_banner)

            if (imageURI.size > 0) {
                Picasso.get()
                    .load(imageURI[0])
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(banner, object : Callback {
                        override fun onSuccess() {
                            // Do nothing
                        }

                        override fun onError(e: Exception?) {
                            Picasso.get()
                                .load(imageURI[0])
                                .into(banner)
                        }
                    })
            }

            rvJudul = findViewById(R.id.rvJudulTournament)
            rvJudul.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

            juduls.add(TourneyString(tournament?.name ?: ""))
            juduls.add(TourneyString(tournament?.description ?: ""))

            rvJudul.adapter = TourneyPageAdapter(juduls)

            handler.postDelayed(runnable, 3000)

            val mFragmentManager = supportFragmentManager
            val overviewFr = OverviewFr()

            mFragmentManager.beginTransaction()
                .add(R.id.tournamentFragment, overviewFr)
                .commit()

            val chip1: Chip = findViewById(R.id.chip1)
            val chip2: Chip = findViewById(R.id.chip2)
//        val chip3: Chip = findViewById(R.id.chip3)

            chip1.setOnClickListener {
                val OverView = OverviewFr()

                mFragmentManager.beginTransaction().apply {
                    replace(R.id.tournamentFragment, OverView, OverView::class.java.simpleName)
                    commit()
                }
            }

            chip2.setOnClickListener {
                val Schedule = ScheduleFr()

                mFragmentManager.beginTransaction().apply {
                    replace(R.id.tournamentFragment, Schedule, Schedule::class.java.simpleName)
                    commit()
                }
            }
        }




//        chip3.setOnClickListener {
//            val MoreInfo = MoreInfoFr()
//
//            mFragmentManager.beginTransaction().apply {
//                replace(R.id.tournamentFragment, MoreInfo, MoreInfo::class.java.simpleName)
//                commit()
//            }
//        }


//        val chips: ChipGroup = findViewById(R.id.tournamentChips)
//
//        chips.setOnCheckedChangeListener { group, checkedId ->
//            when (checkedId) {
//                R.id.chip1 -> {
//                    val OverView = OverviewFr()
//
//                    mFragmentManager.beginTransaction().apply {
//                        replace(R.id.tournamentFragment, OverView, OverView::class.java.simpleName)
//                        commit()
//                    }
//                }
//
//                R.id.chip2 -> {
//                    val Schedule = ScheduleFr()
//
//                    mFragmentManager.beginTransaction().apply {
//                        replace(R.id.tournamentFragment, Schedule, Schedule::class.java.simpleName)
//                        commit()
//                    }
//                }
//
//                R.id.chip3 -> {
//                    val MoreInfo = MoreInfoFr()
//
//                    mFragmentManager.beginTransaction().apply {
//                        replace(R.id.tournamentFragment, MoreInfo, MoreInfo::class.java.simpleName)
//                        commit()
//                        true
//                    }
//                }
//            }
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Stop scrolling when the activity is destroyed
    }

    fun getTournament() : Tournament? {
        return tournament
    }

    fun getParticipants() : ArrayList<Participant> {
        return participants
    }

    fun getTeams() : ArrayList<Team> {
        return teams
    }

    fun getMatches() : ArrayList<Match> {
        return matches
    }
}