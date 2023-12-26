package proyek.andro.adminActivity

import android.content.Intent
import android.hardware.camera2.CameraManager.TorchCallback
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchUIUtil
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.model.Game
import proyek.andro.model.Tournament
import java.util.UUID

class AddTournament : AppCompatActivity() {
    private var games: ArrayList<Game> = ArrayList()
    private var tournament: Tournament? = null

    private var mode: String? = null
    private var name: String? = null

    private lateinit var etName: TextView
    private lateinit var etGame: AutoCompleteTextView
    private lateinit var etStartDate: TextView
    private lateinit var etEndDate: TextView
    private lateinit var etPrizePool: TextView
    private lateinit var etOrganizer: TextView
    private lateinit var etType: AutoCompleteTextView
    private lateinit var etLocation: TextView
    private lateinit var etVenue: TextView
    private lateinit var etDescription: TextView
    private lateinit var etStatus: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tournament)

        etName = findViewById(R.id.etName)
        etGame = findViewById(R.id.etGame)
        etStartDate = findViewById(R.id.etStart)
        etEndDate = findViewById(R.id.etEnd)
        etPrizePool = findViewById(R.id.etPrizepool)
        etOrganizer = findViewById(R.id.etOrganizer)
        etLocation = findViewById(R.id.etLocation)
        etVenue = findViewById(R.id.etVenue)
        etDescription = findViewById(R.id.etDescription)
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
            games = Game().get(limit = 50)
        }.invokeOnCompletion {
            (etGame as MaterialAutoCompleteTextView).setSimpleItems(
                games.map { it.name }.toTypedArray()
            )
            (etType as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Offline", "Online"))
            (etStatus as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Active", "Inactive"))
            if (mode == "edit") {
                pageTitle.text = "Edit tournament"
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
                val game = etGame.text.toString()
                val start = etStartDate.text.toString()
                val end = etEndDate.text.toString()
                val prizePool = etPrizePool.text.toString()
                val organizer = etOrganizer.text.toString()
                val type = etType.text.toString()
                val location = etLocation.text.toString()
                val venue = etVenue.text.toString()
                val description = etDescription.text.toString()
                val status = etStatus.text.toString()
                val logo = ""
                val banner = ""

                if (mode == "add") {
                    val newTournament = Tournament(
                        UUID.randomUUID().toString(),
                        games.find { it.name == game }?.id!!,
                        name,
                        start,
                        end,
                        prizePool.toLong(),
                        organizer,
                        type,
                        listOf(location),
                        listOf(venue),
                        description,
                        logo,
                        banner,
                        status.toLong()
                    )

                    CoroutineScope(Dispatchers.Main).launch {
                        newTournament.insertOrUpdate()
                        startActivity(Intent(this@AddTournament, ManageTournament::class.java))
                    }
                } else if (mode == "edit") {
                    tournament?.game = games.find { it.name == game }?.id!!
                    tournament?.name = name
                    tournament?.start_date= start
                    tournament?.end_date = end
                    tournament?.prize_pool = prizePool.toLong()
                    tournament?.organizer = organizer
                    tournament?.type = type
                    tournament?.location = listOf(location)
                    tournament?.venue = listOf(venue)
                    tournament?.description = description
                    tournament?.logo = logo
                    tournament?.banner = banner
                    tournament?.status = if (status == "Active") 1 else 0

                    CoroutineScope(Dispatchers.Main).launch {
                        tournament?.insertOrUpdate()
                        startActivity(Intent(this@AddTournament, ManageTournament::class.java))
                    }
                }
            }
        }


    }
    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            tournament = Tournament().get<Tournament>(
                filter = Filter.equalTo("name", name!!),
                limit = 1,
                order = arrayOf(arrayOf("name", "asc"))
            ).firstOrNull()
            if (tournament != null) {
                Log.d("Addtournaments", tournament.toString())
//            Log.d("Addtournaments", "tournament found: $tournament")

                etName.text = tournament!!.name
                etGame.setText(games.find { it.id == tournament?.game }?.name)
                etStartDate.text = tournament!!.start_date
                etEndDate.text = tournament!!.end_date
                etPrizePool.text = tournament!!.prize_pool.toString()
                etOrganizer.text = tournament!!.organizer
                etType.setText(tournament!!.type)
                etLocation.text = tournament!!.location.toString()
                etVenue.text = tournament!!.venue.toString()
                etStatus.setText(if (tournament!!.status == 1L) "Active" else "Inactive", false)
                etDescription.text = tournament!!.description

                (etGame as MaterialAutoCompleteTextView).setSimpleItems(
                    games.map { it.name }.toTypedArray()
                )
                (etType as MaterialAutoCompleteTextView).setSimpleItems(
                    arrayOf(
                        "Offline",
                        "Online"
                    )
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
        if (etName.text.toString().isEmpty() || etGame.text.toString().isEmpty() ||
            etStartDate.text.toString().isEmpty() || etEndDate.text.toString().isEmpty() ||
            etPrizePool.text.toString().isEmpty() || etOrganizer.text.toString().isEmpty() || etType.text.toString().isEmpty() ||
            etLocation.text.toString().isEmpty() || etVenue.text.toString().isEmpty() ||
            etStatus.text.toString().isEmpty() || etDescription.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }
}