package proyek.andro.adminActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.model.Tournament
import proyek.andro.model.TournamentPhase
import java.util.Calendar
import java.util.UUID

class AddTournamentPhase : AppCompatActivity() {
    private var tournaments: ArrayList<Tournament> = ArrayList()
    private var phase: TournamentPhase? = null

    private var mode: String? = null
    private var name: String? = null

    private lateinit var detail: List<String>

    private lateinit var etName: TextView
    private lateinit var etTournament: AutoCompleteTextView
    private lateinit var etStartDate: TextView
    private lateinit var etEndDate: TextView

    //    private lateinit var etDetail : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tournament_phase)

        etName = findViewById(R.id.etName)
        etTournament = findViewById(R.id.etTournament)
        etStartDate = findViewById(R.id.etStartDate)
        etEndDate = findViewById(R.id.etEndDate)

//        detail = listOf(
//            "16 teams are divided into 4 groups", "Single Round Robin",
//            "Kelvin",
//            "Kelvin",
//            "Kelvin",
//        )
//
//        etDetail = findViewById(R.id.etDetail)
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, detail)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        etDetail.adapter = adapter

        val backBtn: ImageView = findViewById(R.id.backBtn)
        val submitBtn: MaterialButton = findViewById(R.id.submitBtn)
        val pageTitle: TextView = findViewById(R.id.pageTitle)

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("name")

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        CoroutineScope(Dispatchers.Main).launch {
            tournaments = Tournament().get(limit = 100)
        }.invokeOnCompletion {
            (etTournament as MaterialAutoCompleteTextView).setSimpleItems(
                tournaments.map { "${it.name} - ${it.id}" }.toTypedArray()
            )

            etStartDate.setOnClickListener {
                val now = Calendar.getInstance()
                val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                    val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
                        etStartDate.setText("$year-${month + 1}-$dayOfMonth $hourOfDay:$minute UTC")
                    }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
                    timePicker.show()
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
                datePicker.show()
            }

            etEndDate.setOnClickListener {
                val now = Calendar.getInstance()
                val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                    val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
                        etEndDate.setText("$year-${month + 1}-$dayOfMonth $hourOfDay:$minute UTC")
                    }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
                    timePicker.show()
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
                datePicker.show()
            }

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
                val tournament = etTournament.text.toString()
                val tournamentId = tournament.split(" - ")[1]
                val startDate = etStartDate.text.toString()
                val endDate = etEndDate.text.toString()

                if (mode == "add") {
                    CoroutineScope(Dispatchers.Main).launch {
                        val newTournamentPhase = TournamentPhase(
                            UUID.randomUUID().toString(),
                            tournamentId,
                            name,
                            startDate,
                            endDate,
                            detail = listOf()
                        )

                        newTournamentPhase.insertOrUpdate()
                        startActivity(Intent(this@AddTournamentPhase, ManagePhase::class.java))
                    }
                } else if (mode == "edit") {
                    phase?.name = name
                    phase?.tournament = tournamentId
                    phase?.start_date = startDate
                    phase?.end_date = endDate
                }
            }
        }
    }

    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            phase = TournamentPhase().findByTournament(
                tournaments.find {
                    it.name == name
                }!!.id
            ).get(0)

            Log.d("phase", phase.toString())

            etName.text = phase?.name
            etTournament.setText(phase?.tournament)
            etStartDate.text = phase?.start_date
            etEndDate.text = phase?.end_date
        }
    }

    fun validate(): Boolean {
        if (
            etName.text.toString().isEmpty() ||
            etTournament.text.toString().isEmpty() ||
            etStartDate.text.toString().isEmpty() ||
            etEndDate.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }
}