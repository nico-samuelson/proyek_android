package proyek.andro.adminActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.model.Game
import proyek.andro.model.Match
import proyek.andro.model.Organization
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import proyek.andro.model.TournamentPhase
import java.util.UUID

class AddMatch : AppCompatActivity() {
    private var phases : ArrayList<TournamentPhase> = ArrayList()
    private var teams: ArrayList<Team> = ArrayList()
    private var tour: ArrayList<Tournament> = ArrayList()
    private var match: Match? = null

    private var mode: String? = null
    private var name: String? = null

    private lateinit var etName: TextView
    private lateinit var etPhase: AutoCompleteTextView
    private lateinit var etTeam1: AutoCompleteTextView
    private lateinit var etTeam2: AutoCompleteTextView
    private lateinit var etWinner: AutoCompleteTextView
    private lateinit var etTime: TextView
    private lateinit var etScore: TextView
    private lateinit var etTour: AutoCompleteTextView
    private lateinit var etStatus: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_match)

        etName = findViewById(R.id.etName)
        etPhase = findViewById(R.id.etPhase)
        etTeam1 = findViewById(R.id.etTeam1)
        etTeam2 = findViewById(R.id.etTeam2)
        etWinner = findViewById(R.id.etWinner)
        etTime = findViewById(R.id.etTime)
        etScore = findViewById(R.id.etScore)
        etTour = findViewById(R.id.etTournament)
        etStatus = findViewById(R.id.etStatus)

        val backBtn: ImageView = findViewById(R.id.backBtn)
        val submitBtn: MaterialButton = findViewById(R.id.submitBtn)
        val pageTitle: TextView = findViewById(R.id.pageTitle)

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("name")

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        CoroutineScope(Dispatchers.Main).launch {
            phases = TournamentPhase().get(limit = 50)
            teams = Team().get(limit = 50)
            tour = Tournament().get(limit = 50)
        }.invokeOnCompletion {
            (etPhase as MaterialAutoCompleteTextView).setSimpleItems(
                phases.map { it.name }.toTypedArray()
            )
            (etTeam1 as MaterialAutoCompleteTextView).setSimpleItems(
                teams.map { it.name }.toTypedArray()
            )
            (etTeam2 as MaterialAutoCompleteTextView).setSimpleItems(
                teams.map { it.name }.toTypedArray()
            )
            (etWinner as MaterialAutoCompleteTextView).setSimpleItems(
                teams.map { it.name }.toTypedArray()
            )
            (etTour as MaterialAutoCompleteTextView).setSimpleItems(
                tour.map { it.name }.toTypedArray()
            )
            (etStatus as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Active", "Inactive"))
            if (mode == "edit") {
                pageTitle.text = "Edit Match"
                submitBtn.text = "Save"

                showEdit()
            }
        }

        submitBtn.setOnClickListener {
            if (!validate()) {
                Snackbar.make(submitBtn, R.string.fillAll, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            } else {
                val name = etName.text.toString()
                val phase = etPhase.text.toString()
                val team1 = etTeam1.text.toString()
                val team2 = etTeam2.text.toString()
                val winner = etWinner.text.toString()
                val time = etTime.text.toString()
                val score = etScore.text.toString()
                val tournament = etTour.text.toString()
                val status = etStatus.text.toString()

//                val orgLogo = organizations.filter { it.name == org }.map { it.logo }.firstOrNull()
//                if (orgLogo == null) {
//                    Log.d("AddTeams", "Team logo not found")
//                    return@setOnClickListener
//                }
//                Log.d("AddTeams", "Team logo found: $orgLogo")

                if (mode == "add") {
                    val newTeam = Match(
                        UUID.randomUUID().toString(),
                        phase,
                        team1,
                        team2,
                        name,
                        winner,
                        time,
                        score,
                        tournament,
                        if (status == "Active") 1 else 0,
                    )

                    CoroutineScope(Dispatchers.Main).launch {
                        newTeam.insertOrUpdate()
                        startActivity(Intent(this@AddMatch, ManageMatch::class.java))
                    }
                } else if (mode == "edit") {
                    match?.name = name
                    match?.tournament_phase = phase
                    match?.team1 = team1
                    match?.team2 = team2
                    match?.winner= winner
                    match?.score = score
                    match?.tournament = tournament
                    match?.status = if (status == "Active") 1 else 0

                    CoroutineScope(Dispatchers.Main).launch {
                        match?.insertOrUpdate()
                        startActivity(Intent(this@AddMatch, ManageMatch::class.java))
                    }
                }
            }
        }
    }
    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            match = Match().get<Match>(
                filter = Filter.equalTo("name", name!!),
                limit = 1,
                order = arrayOf(arrayOf("name", "asc"))
            ).firstOrNull()
            if (match != null) {
                Log.d("Addmatchs", match.toString())
//            Log.d("Addmatchs", "match found: $match")
//                etName = findViewById(R.id.etName)
//                etPhase = findViewById(R.id.etPhase)
//                etmatch1 = findViewById(R.id.etTeam1)
//                etmatch2 = findViewById(R.id.etTeam2)
//                etWinner = findViewById(R.id.etWinner)
//                etTime = findViewById(R.id.etTime)
//                etScore = findViewById(R.id.etScore)
//                etTour = findViewById(R.id.etTournament)
//                etStatus = findViewById(R.id.etStatus)

                etName.text = match!!.name
                etPhase.setText(match!!.tournament_phase)
                etTeam1.setText(match!!.team1)
                etTeam2.setText(match!!.team2)
                etWinner.setText(match!!.winner)
                etScore.text = match!!.score
                etTime.text = match!!.time
                etTour.setText(match!!.tournament)
                etStatus.setText(if (match!!.status == 1L) "Active" else "Inactive", false)


//                (etTeam1 as MaterialAutoCompleteTextView).setSimpleItems(
//                    teams.map { it.name }.toTypedArray()
//                )

                (etTour as MaterialAutoCompleteTextView).setSimpleItems(
                    tour.map { it.name }.toTypedArray()
                )

                (etStatus as MaterialAutoCompleteTextView).setSimpleItems(
                    arrayOf(
                        "Active",
                        "Inactive"
                    )
                )
            }
        }
    }

    fun validate(): Boolean {
        if (etName.text.toString().isEmpty() || etPhase.text.toString().isEmpty() ||
            etTeam1.text.toString().isEmpty() || etTeam2.text.toString().isEmpty() ||
            etWinner.text.toString().isEmpty() || etScore.text.toString().isEmpty() ||
            etTime.text.toString().isEmpty() || etTour.text.toString().isEmpty() ||
            etStatus.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }
}