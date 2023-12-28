package proyek.andro.adminActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import proyek.andro.R
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.adapter.SimpleListAdapter
import proyek.andro.adapter.SimpleListAdapter2
import proyek.andro.model.Tournament
import proyek.andro.model.TournamentPhase

class ManagePhases : AppCompatActivity() {
    private var phases: ArrayList<TournamentPhase> = ArrayList()

    private var filteredPhases: ArrayList<TournamentPhase> = ArrayList()
    private var filteredNames: ArrayList<String> = ArrayList()

    private lateinit var rvPhases: RecyclerView
    private var searchText: String = ""

    private lateinit var adapterP: SimpleListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_phase)

        val etTournament = findViewById<MaterialAutoCompleteTextView>(R.id.etTournament)
        val rvParticipant = findViewById<RecyclerView>(R.id.rv_phases)
        val addBtn : FloatingActionButton = findViewById(R.id.addPhaseBtn)

        var tournaments : ArrayList<Tournament> = ArrayList()
        var selectedTournament : Tournament? = null
        var phases : ArrayList<TournamentPhase> = ArrayList()

        CoroutineScope(Dispatchers.Main).launch {
            tournaments = Tournament().get()
            etTournament.setSimpleItems(tournaments.map { it.name }.toTypedArray())
        }

        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        rvParticipant.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapterP = SimpleListAdapter2(phases.map { it.name })

        adapterP.setOnItemClickCallback(object : SimpleListAdapter2.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                val intent = Intent(this@ManagePhases, AddTournamentPhase::class.java)
                intent.putExtra("mode", "edit")
                intent.putExtra("phase", phases.filter { it.name == data && it.tournament == selectedTournament?.id }.firstOrNull()?.id)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    val currentItem = phases.get(pos)
//                    Log.d("currentItem", teams.filter { it.id == currentItem.team }.first().name)
                    phases.remove(currentItem)
                    currentItem.delete(currentItem.id)
                }.invokeOnCompletion {
                    adapterP.setData(phases.map { it.name })
                }
            }
        })

        rvParticipant.adapter = adapterP

        etTournament.setOnItemClickListener { parent, view, position, id ->
            findViewById<LinearLayout>(R.id.empty_view).visibility = View.GONE

            selectedTournament = tournaments.filter { it.name == parent.getItemAtPosition(position).toString() }.firstOrNull()

            CoroutineScope(Dispatchers.Main).launch {
                phases = TournamentPhase().get(
                    filter = Filter.equalTo("tournament", selectedTournament?.id),
                    order = arrayOf(arrayOf("tournament", "ASC"))
                )
                if (phases.size > 0) {
                    adapterP.setData(phases.map { it.name })
                }
                else {
                    findViewById<LinearLayout>(R.id.empty_view).visibility = View.VISIBLE
                }
            }
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, AddTournamentPhase::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }
    }
}