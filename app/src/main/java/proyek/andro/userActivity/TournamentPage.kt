package proyek.andro.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import proyek.andro.R
import proyek.andro.adapter.TourneyPageAdapter
import proyek.andro.userActivity.TournamentExtension.MoreInfoFr
import proyek.andro.userActivity.TournamentExtension.OverviewFr
import proyek.andro.userActivity.TournamentExtension.ScheduleFr
import proyek.andro.userActivity.TournamentExtension.TourneyString

class TournamentPage : AppCompatActivity() {
    private var juduls : ArrayList<TourneyString> = ArrayList()
    private lateinit var rvJudul : RecyclerView
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

        rvJudul = findViewById(R.id.rvJudulTournament)
        rvJudul.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        juduls.add(TourneyString("ESL Atlanta"))
        juduls.add(TourneyString("Improvised Technical Kill"))

        rvJudul.adapter = TourneyPageAdapter(juduls)

        handler.postDelayed(runnable, 3000)

        val mFragmentManager = supportFragmentManager
        val overviewFr = OverviewFr()

        mFragmentManager.beginTransaction()
            .add(R.id.tournamentFragment, overviewFr)
            .commit()

        val chip1: Chip = findViewById(R.id.chip1)
        val chip2: Chip = findViewById(R.id.chip2)
        val chip3: Chip = findViewById(R.id.chip3)

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

        chip3.setOnClickListener {
            val MoreInfo = MoreInfoFr()

            mFragmentManager.beginTransaction().apply {
                replace(R.id.tournamentFragment, MoreInfo, MoreInfo::class.java.simpleName)
                commit()
            }
        }


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
}