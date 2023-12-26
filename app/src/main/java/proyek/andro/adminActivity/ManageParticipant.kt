package proyek.andro.adminActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.SimpleListAdapter2
import proyek.andro.model.Participant
import proyek.andro.model.Team
import proyek.andro.model.Tournament

class ManageParticipant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_participant)

        val etTournament = findViewById<MaterialAutoCompleteTextView>(R.id.etTournament)
        val rvParticipant = findViewById<RecyclerView>(R.id.rv_participants)
        val addBtn : FloatingActionButton = findViewById(R.id.addParticipantBtn)

        var tournaments : ArrayList<Tournament> = ArrayList()
        var selectedTournament : Tournament?
        var teams : ArrayList<Team> = ArrayList()
        var participants : ArrayList<Participant> = ArrayList()

        CoroutineScope(Dispatchers.Main).launch {
            tournaments = Tournament().get()
            etTournament.setSimpleItems(tournaments.map { it.name }.toTypedArray())
        }

        findViewById<ImageView>(R.id.backBtn).setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        rvParticipant.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapterP = SimpleListAdapter2(listOf())

        adapterP.setOnItemClickCallback(object : SimpleListAdapter2.OnItemClickCallback {
            override fun onItemClicked(data: String) {}

            override fun delData(pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    val currentPart = participants.get(pos)
                    Log.d("currentPart", teams.filter { it.id == currentPart.team }.first().name)
                    participants.remove(currentPart)
                    currentPart.delete(currentPart.id)


                }.invokeOnCompletion {
                    adapterP.setData(teams.filter { it.id in participants.map { p -> p.team } }.map { it.name })
                }
            }
        })

        rvParticipant.adapter = adapterP

        etTournament.setOnItemClickListener { parent, view, position, id ->
            findViewById<LinearLayout>(R.id.empty_view).visibility = View.GONE

            selectedTournament = tournaments.filter { it.name == parent.getItemAtPosition(position).toString() }.firstOrNull()

            CoroutineScope(Dispatchers.Main).launch {
                participants = Participant().get(
                    filter = Filter.equalTo("tournament", selectedTournament?.id),
                    order = arrayOf(arrayOf("tournament", "ASC"))
                )
                if (participants.size > 0) {
                    teams = Team().get(
                        filter = Filter.inArray("id", participants.map { it.team }),
                    )
                    adapterP.setData(teams.filter { it.id in participants.map { p -> p.team } }.map { it.name })
                }
                else {
                    findViewById<LinearLayout>(R.id.empty_view).visibility = View.VISIBLE
                }
            }
        }

        addBtn.setOnClickListener {
            startActivity(Intent(this, AddParticipant::class.java))
        }
    }
}