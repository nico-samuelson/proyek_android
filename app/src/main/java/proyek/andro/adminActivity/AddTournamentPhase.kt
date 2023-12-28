package proyek.andro.adminActivity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.SimpleListAdapter2
import proyek.andro.model.Tournament
import proyek.andro.model.TournamentPhase
import java.util.Calendar
import java.util.UUID

class AddTournamentPhase : AppCompatActivity() {
    private var tournaments: ArrayList<Tournament> = ArrayList()
    private var phase: TournamentPhase? = null

    private var mode: String? = null
    private var name: String? = null

    private lateinit var etName: TextView
    private lateinit var etTournament: AutoCompleteTextView
    private lateinit var etStartDate: TextView
    private lateinit var etEndDate: TextView
    private lateinit var rvDetail: RecyclerView
    private lateinit var etDetail: TextView

    var listDetail = listOf<String>()
    var AdapterDetail: SimpleListAdapter2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tournament_phase)

        etName = findViewById(R.id.etName)
        etTournament = findViewById(R.id.etTournament)
        etStartDate = findViewById(R.id.etStartDate)
        etEndDate = findViewById(R.id.etEndDate)
        etDetail = findViewById(R.id.etDetail)
        rvDetail = findViewById(R.id.rvDetail)

        val backBtn: ImageView = findViewById(R.id.backBtn)
        val submitBtn: MaterialButton = findViewById(R.id.submitBtn)
        val pageTitle: TextView = findViewById(R.id.pageTitle)

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("phase")

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        CoroutineScope(Dispatchers.Main).launch {
            tournaments = Tournament().get()
        }.invokeOnCompletion {
            (etTournament as MaterialAutoCompleteTextView).setSimpleItems(
                tournaments.map { it.name }.toTypedArray()
            )

            etStartDate.setOnClickListener {
                val now = Calendar.getInstance()
                val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                    etStartDate.setText("$year-${month + 1}-$dayOfMonth")
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
                datePicker.show()
            }

            etEndDate.setOnClickListener {
                val now = Calendar.getInstance()
                val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                    etEndDate.setText("$year-${month + 1}-$dayOfMonth")
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
                datePicker.show()
            }

            if (mode == "edit") {
                pageTitle.text = "Edit Tournament Phase"
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
                val startDate = etStartDate.text.toString()
                val endDate = etEndDate.text.toString()

                if (mode == "add") {
                    CoroutineScope(Dispatchers.Main).launch {
                        val newTournamentPhase = TournamentPhase(
                            UUID.randomUUID().toString(),
                            tournaments.filter { it.name == tournament }.first().id,
                            name,
                            startDate,
                            endDate,
                            listDetail
                        )

                        newTournamentPhase.insertOrUpdate()
                        startActivity(Intent(this@AddTournamentPhase, ManagePhases::class.java))
                    }
                } else if (mode == "edit") {
                    phase?.name = name
                    phase?.tournament = tournaments.filter { it.name == tournament }.first().id
                    phase?.start_date = startDate
                    phase?.end_date = endDate
                    phase?.detail = listDetail

                    CoroutineScope(Dispatchers.Main).launch {
                        phase?.insertOrUpdate()
                        startActivity(Intent(this@AddTournamentPhase, ManagePhases::class.java))
                    }
                }
            }
        }
        AdapterDetail = SimpleListAdapter2(listDetail)

        AdapterDetail?.setOnItemClickCallback(object : SimpleListAdapter2.OnItemClickCallback {
            override fun onItemClicked(data: String) {

            }

            override fun delData(pos: Int) {
                listDetail = listDetail.filterIndexed {index, s ->  index != pos  }
                AdapterDetail?.setData(listDetail)
            }
        })

        etDetail.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                listDetail = listDetail + etDetail.text.toString()
                Log.d("venues", listDetail.toString())
                AdapterDetail?.setData(listDetail)
                etDetail.text = null
                etDetail.clearFocus()
            }
            false

        }

        rvDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rvDetail.adapter = AdapterDetail
    }

    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            phase = TournamentPhase().find(name!!)

            Log.d("phase", phase.toString())

            etName.text = phase?.name

            val tournament = tournaments.filter { it.id == phase?.tournament }.first()

            etTournament.setText(tournament.name)

            (etTournament as MaterialAutoCompleteTextView).setSimpleItems(
                tournaments
                    .map { it.name }
                    .toTypedArray()
            )

            etStartDate.text = phase?.start_date
            etEndDate.text = phase?.end_date

            listDetail = phase!!.detail
            AdapterDetail?.setData(listDetail)
        }

    }

    fun validate(): Boolean {
        if (
            etName.text.toString().isEmpty() ||
            etTournament.text.toString().isEmpty() ||
            etStartDate.text.toString().isEmpty() ||
            etEndDate.text.toString().isEmpty() || listDetail.size == 0
        ) {
            return false
        }
        return true
    }
}