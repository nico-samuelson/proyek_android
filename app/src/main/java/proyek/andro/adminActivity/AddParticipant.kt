package proyek.andro.adminActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.firestore.Filter
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Game
import proyek.andro.model.Participant
import proyek.andro.model.Player
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import java.util.UUID

class AddParticipant : AppCompatActivity() {
    private lateinit var etGroup : TextView
    private lateinit var etRank : TextView
    private lateinit var etPoints : TextView
    private lateinit var etTournament : MaterialAutoCompleteTextView
    private lateinit var etTeam : MaterialAutoCompleteTextView

    private var teams : ArrayList<Team> = ArrayList()
    private var tournaments : ArrayList<Tournament> = ArrayList()
    private var mode : String? = null
    private var name : String? = null
    private var player : Player? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_participant)

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("name")

        etGroup = findViewById(R.id.etGroup)
        etRank = findViewById(R.id.etRank)
        etPoints = findViewById(R.id.etPoints)
        etTournament = findViewById(R.id.etTournament)
        etTeam = findViewById(R.id.etTeam)

        val submitBtn : MaterialButton = findViewById(R.id.submitBtn)
        val backBtn : ImageView = findViewById(R.id.backBtn)

        backBtn.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // get needed data
        CoroutineScope(Dispatchers.Main).launch {
            tournaments = Tournament().get()
            etTournament.setSimpleItems(tournaments.map { it.name }.toTypedArray())
        }

        etTournament.setOnItemClickListener{ parent, view, position, id ->
            val selectedTournament = tournaments.filter { it.name == parent.getItemAtPosition(position).toString() }.firstOrNull()

            CoroutineScope(Dispatchers.Main).launch {
                teams = Team().get(
                    filter = Filter.and(
                        Filter.equalTo("game", selectedTournament?.game),
                    ),
                    order = arrayOf(arrayOf("game", "ASC"))
                )

                etTeam.setSimpleItems(teams.map { team -> team.name }.toTypedArray())
            }
        }

        // submit button click listener
        submitBtn.setOnClickListener {
            // make sure all fields are filled
            if (!validate()) {
                Snackbar.make(submitBtn, R.string.fillAll, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            }

            else {
                val group = etGroup.text.toString()
                val rank = etRank.text.toString().toLong()
                val points = etPoints.text.toString().toLong()
                val tournament = tournaments.find { it.name == etTournament.text.toString() }?.id.toString()
                val team = teams.find { it.name == etTeam.text.toString() }?.id.toString()

                // add new participant
                val newParticipant = Participant(
                    UUID.randomUUID().toString(),
                    tournament,
                    team,
                    group,
                    rank,
                    points
                )

                CoroutineScope(Dispatchers.Main).launch {
                    newParticipant.insertOrUpdate()
                    startActivity(Intent(this@AddParticipant, ManageParticipant::class.java))
                }
            }
        }
    }

    fun validate() : Boolean {
        if (etTeam.text.toString().isEmpty() || etTournament.text.toString().isEmpty()) {
            return false
        }
        return true
    }
}