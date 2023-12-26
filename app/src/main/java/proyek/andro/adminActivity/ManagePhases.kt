package proyek.andro.adminActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import proyek.andro.R
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.adapter.SimpleListAdapter
import proyek.andro.model.TournamentPhase

class ManagePhases : AppCompatActivity() {
    private var phases: ArrayList<TournamentPhase> = ArrayList()

    private var filteredPhases: ArrayList<TournamentPhase> = ArrayList()
    private var filteredNames: ArrayList<String> = ArrayList()

    private lateinit var rvPhases: RecyclerView

    private lateinit var search_view: SearchView
    private lateinit var etSearch: EditText
    private var searchText: String = ""

    private lateinit var adapterP: SimpleListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_phase)

        val backBtn: ImageView = findViewById(R.id.backBtn)
        val addBtn: FloatingActionButton = findViewById(R.id.addPhaseBtn)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, AddTournamentPhase::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }

        rvPhases = findViewById(R.id.rvPhases)
        CoroutineScope(Dispatchers.Main).launch {
            phases = TournamentPhase().get(limit = 100)
            Log.d("phases", phases.toString())

        }.invokeOnCompletion {
            filteredNames = phases.map {
                it.name
            } as ArrayList<String>

            filterPhase()

            adapterP = SimpleListAdapter(filteredNames, filteredNames, "logo/orgs/")

            adapterP.setOnItemClickCallback(object : SimpleListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: String) {
                    val intent = Intent(this@ManagePhases, AddTournamentPhase::class.java)
                    intent.putExtra("mode", "edit")
                    intent.putExtra("name", data)
                    startActivity(intent)
                }

                override fun delData(pos: Int) {
                    val tournamentPhase = filteredPhases.get(pos)

                    val alert = MaterialAlertDialogBuilder(this@ManagePhases).setTitle("Delete Team")
                        .setMessage("Are you sure you want to delete ${tournamentPhase.name}?")
                        .setNegativeButton("Cancel") { dialog, which ->
                            dialog.dismiss()
                        }.setPositiveButton("Delete") { dialog, which ->
                            CoroutineScope(Dispatchers.Main).launch {

                                tournamentPhase.delete(tournamentPhase.id)

                                phases.removeAt(pos)
                                filteredNames.removeAt(pos)
                                filteredPhases.removeAt(pos)

                                adapterP.setData(filteredNames, filteredNames)

                                Snackbar.make(
                                    addBtn, "Match deleted successfully", Snackbar.LENGTH_SHORT
                                ).apply {
                                    setBackgroundTint(resources.getColor(R.color.light, null))
                                    setTextColor(resources.getColor(R.color.black, null))
                                }.show()
                            }
                        }
                    val dialog = alert.create()
                    dialog.show()
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                }

            })


            rvPhases.adapter = adapterP
            rvPhases.layoutManager = LinearLayoutManager(
                this@ManagePhases, LinearLayoutManager.VERTICAL, false
            )
        }

        search_view = findViewById(R.id.search_view)
        etSearch = search_view.findViewById(androidx.appcompat.R.id.search_src_text)
        etSearch.setHintTextColor(resources.getColor(R.color.disabled, null))
        etSearch.setTextColor(resources.getColor(R.color.white, null))

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_view.clearFocus()

                if (query.toString() != searchText) {
                    searchText = query.toString()
                    filterPhase()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredNames, filteredNames)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.toString() == "" && searchText != "") {
                    searchText = newText.toString()
                    filterPhase()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredNames, filteredNames)
                    }
                }
                return true
            }
        })
    }

    fun filterPhase() {
        filteredPhases.clear()
        filteredNames.clear()

        if (searchText == "") {
            filteredPhases.addAll(phases.filter {
                it.name != ""
            } as ArrayList<TournamentPhase>)
        } else {
            filteredPhases = phases.filter {
                it.name.contains(searchText, ignoreCase = true)
            } as ArrayList<TournamentPhase>
        }

        filteredNames = filteredPhases.map { it.name } as ArrayList<String>

        if (filteredPhases.isEmpty()) {
            rvPhases.visibility = View.GONE
        } else {
            rvPhases.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.Main).launch {
            adapterP.setData(filteredNames, filteredNames)
        }
    }
}