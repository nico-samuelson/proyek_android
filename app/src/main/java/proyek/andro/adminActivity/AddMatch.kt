package proyek.andro.adminActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import proyek.andro.model.Player
import proyek.andro.model.PlayerHistory
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import proyek.andro.model.TournamentPhase
import java.util.Calendar
import java.util.UUID

class AddMatch : AppCompatActivity() {
    private var phases: ArrayList<TournamentPhase> = ArrayList()
    private var teams: ArrayList<Team> = ArrayList()
    private var tour: ArrayList<Tournament> = ArrayList()
    private var players: ArrayList<Player> = ArrayList()
    private var match: Match? = null

    private var mode: String? = null
    private var name: String? = null

    private lateinit var etName: TextView
    private lateinit var etTeam1: AutoCompleteTextView
    private lateinit var etTeam2: AutoCompleteTextView
    private lateinit var etScore: TextView
    private lateinit var etWinner: AutoCompleteTextView
    private lateinit var etTime: TextView
    private lateinit var etTour: AutoCompleteTextView
    private lateinit var etPhase: AutoCompleteTextView
    private lateinit var etStatus: AutoCompleteTextView

    fun updateWinnerItems(team1: String, team2: String) {
        (etWinner as MaterialAutoCompleteTextView).setSimpleItems(arrayOf(team1, team2))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_match)

        etName = findViewById(R.id.etName)
        etTeam1 = findViewById(R.id.etTeam1)
        etTeam2 = findViewById(R.id.etTeam2)
        etScore = findViewById(R.id.etScore)
        etWinner = findViewById(R.id.etWinner)
        etTime = findViewById(R.id.etTime)
        etTour = findViewById(R.id.etTournament)
        etPhase = findViewById(R.id.etTournamentPhase)
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
            tour = Tournament().get(limit = 10)
            players = Player().get(limit = 500)
        }.invokeOnCompletion {
            Log.d("AddMatch", "Phases: " + phases.map { it.name }.toString())
            Log.d("AddMatch", "Teams: " + teams.map { it.name }.toString())
            Log.d("AddMatch", "Tours: " + tour.map { it.name }.toString())
            (etTeam1 as MaterialAutoCompleteTextView).setSimpleItems(
                teams.map { "${it.name} - ${it.id}" }.toTypedArray()
            )
            (etTeam2 as MaterialAutoCompleteTextView).setSimpleItems(
                teams.map { "${it.name} - ${it.id}" }.toTypedArray()
            )
            etTeam1.setOnItemClickListener { _, _, position, _ ->
                val selectedTeam1 = etTeam1.adapter.getItem(position) as String
                val otherTeams =
                    teams.map { "${it.name} - ${it.id}" }.filter { it != selectedTeam1 }
                        .toTypedArray()
                (etTeam2 as MaterialAutoCompleteTextView).setSimpleItems(otherTeams)
                updateWinnerItems(selectedTeam1, etTeam2.text.toString())
            }

            etTeam2.setOnItemClickListener { _, _, position, _ ->
                val selectedTeam2 = etTeam2.adapter.getItem(position) as String
                val otherTeams =
                    teams.map { "${it.name} - ${it.id}" }.filter { it != selectedTeam2 }
                        .toTypedArray()
                (etTeam1 as MaterialAutoCompleteTextView).setSimpleItems(otherTeams)
                updateWinnerItems(etTeam1.text.toString(), selectedTeam2)
            }

            (etTour as MaterialAutoCompleteTextView).setSimpleItems(
                tour.map { "${it.name} - ${it.id}" }.toTypedArray()
            )

            etTime.setOnClickListener {
                val now = Calendar.getInstance()
                val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                    val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
                        etTime.setText("$year-${month + 1}-$dayOfMonth $hourOfDay:$minute UTC")
                    }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
                    timePicker.show()
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
                datePicker.show()
            }

            etTour.setOnItemClickListener { _, _, position, _ ->
                val selectedTour = etTour.adapter.getItem(position) as String
                val tourId = selectedTour.split(" - ")[1]
                val filteredPhases = phases.filter { it.tournament == tourId }

                (etPhase as MaterialAutoCompleteTextView).setSimpleItems(
                    filteredPhases.map { it.name + " - " + it.id }.toTypedArray()
                )
            }

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
                var winnerId: String = ""

                val name = etName.text.toString()
                val team1 = etTeam1.text.toString()
                val team1ID = team1.split(" - ")[1]
                val team2 = etTeam2.text.toString()
                val team2ID = team2.split(" - ")[1]
                val score = etScore.text.toString()

                val winner = etWinner.text.toString()
                Log.d("AddMatch", winner)
                if (winner.isEmpty()) winnerId = ""
                else {
                    winnerId = winner.split(" - ")[1]
                    Log.d("AddMatch", winnerId)

                }

                val time = etTime.text.toString()
                val tournament = etTour.text.toString()
                val tournamentId = tournament.split(" - ")[1]
                val phase = etPhase.text.toString()
                val phaseId = phase.split(" - ")[1]
                val status = etStatus.text.toString()

                if (mode == "add") {
                    CoroutineScope(Dispatchers.Main).launch {
                        val matchID = UUID.randomUUID().toString()
                        val newTeam = Match(
                            matchID,
                            phaseId,
                            team1ID,
                            team2ID,
                            name,
                            winnerId,
                            time,
                            score,
                            tournamentId,
                            if (status == "Active") 1 else 0,
                        )

                        var filteredPlayersTeam1 = players.filter {
                            it.team == team1ID
                        }

                        val playerHistoryTeam1 = filteredPlayersTeam1.map {
                            PlayerHistory(
                                UUID.randomUUID().toString(),
                                it.id,
                                matchID,
                                team1ID
                            )
                        }

                        playerHistoryTeam1.forEach {
                            it.insertOrUpdate()
                        }

                        var filteredPlayersTeam2 = players.filter {
                            it.team == team2ID
                        }

                        val playerHistoryTeam2 = filteredPlayersTeam2.map {
                            PlayerHistory(
                                UUID.randomUUID().toString(),
                                it.id,
                                matchID,
                                team2ID
                            )
                        }

                        playerHistoryTeam2.forEach {
                            it.insertOrUpdate()
                        }

                        newTeam.insertOrUpdate()
                        startActivity(Intent(this@AddMatch, ManageMatch::class.java))
                    }
                } else if (mode == "edit") {
                    match?.name = name
                    match?.tournament_phase = phaseId
                    match?.team1 = team1ID
                    match?.team2 = team2ID
                    match?.winner = winnerId
                    match?.score = score
                    match?.tournament = tournamentId
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
                filter = Filter.equalTo("name", name),
                limit = 1,
                order = arrayOf(arrayOf("name", "asc"))
            ).firstOrNull()
            if (match != null) {
//                Log.d("Addmatchs", match.toString())
                etName.text = match?.name
                etTour.setText(
                    tour.filter { it.id == match?.tournament }.map { "${it.name} - ${it.id}" }
                        .firstOrNull()
                )
                etPhase.setText(match?.name + " - " + match?.tournament_phase)
                etTeam1.setText(
                    teams.filter { it.id == match?.team1 }.map { "${it.name} - ${it.id}" }
                        .firstOrNull()
                )
                etTeam2.setText(
                    teams.filter { it.id == match?.team2 }.map { "${it.name} - ${it.id}" }
                        .firstOrNull()
                )
                etWinner.setText(
                    teams.filter { it.id == match?.winner }.map { "${it.name} - ${it.id}" }
                        .firstOrNull()
                )

                etScore.text = match?.score
                etTime.text = match?.time
                etStatus.setText(if (match?.status == 1L) "Active" else "Inactive", false)

                (etTeam1 as MaterialAutoCompleteTextView).setSimpleItems(
                    teams.map { "${it.name} - ${it.id}" }.toTypedArray()
                )
                (etTeam2 as MaterialAutoCompleteTextView).setSimpleItems(
                    teams.map { "${it.name} - ${it.id}" }.toTypedArray()
                )

                (etPhase as MaterialAutoCompleteTextView).setSimpleItems(
                    phases.filter { it.tournament == match?.tournament }.map { it.name + " - " + it.id }
                        .toTypedArray()
                )

                (etWinner as MaterialAutoCompleteTextView).setSimpleItems(
                    teams.filter {
                        it.id == match?.team1 || it.id == match?.team2
                    }.map { "${it.name} - ${it.id}" }.toTypedArray()
                )

                (etTour as MaterialAutoCompleteTextView).setSimpleItems(
                    tour.map { "${it.name} - ${it.id}" }.toTypedArray()
                )
                etTour.setOnItemClickListener { _, _, position, _ ->
                    val selectedTour = etTour.adapter.getItem(position) as String
                    val tourId = selectedTour.split(" - ")[1]
                    val filteredPhases = phases.filter { it.tournament == tourId }

                    (etPhase as MaterialAutoCompleteTextView).setSimpleItems(
                        filteredPhases.map { it.name }.toTypedArray()
                    )
                }
            }
        }
    }


    fun validate(): Boolean {
        if (
            etName.text.toString().isEmpty() || etPhase.text.toString().isEmpty() ||
            etTeam1.text.toString().isEmpty() || etTeam2.text.toString()
                .isEmpty() || etScore.text.toString().isEmpty() ||
            etTime.text.toString().isEmpty() || etTour.text.toString().isEmpty() ||
            etStatus.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }
}