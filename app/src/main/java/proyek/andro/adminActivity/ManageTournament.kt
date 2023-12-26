package proyek.andro.adminActivity

import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import proyek.andro.R
import proyek.andro.adapter.SimpleListAdapter
import proyek.andro.model.Team
import proyek.andro.model.Tournament

class ManageTournament : AppCompatActivity() {
    private var tournament : ArrayList<Tournament> = ArrayList()
    private var filteredTournament : ArrayList<Tournament> = ArrayList()
    private var filteredImages : ArrayList<String> = ArrayList()
    private var filteredNames : ArrayList<String> = ArrayList()

    private lateinit var rvTournament : RecyclerView

    private lateinit var search_view: SearchView
    private lateinit var etSearch: EditText
    private var searchText: String = ""

    private lateinit var adapterP: SimpleListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tournament)
        val backBtn : ImageView = findViewById(R.id.backBtn)
        val addBtn : FloatingActionButton = findViewById(R.id.addTournamentButton)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, AddTournament::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }

        rvTournament = findViewById(R.id.viewTournament)
        CoroutineScope(Dispatchers.Main).launch {
            tournament = Tournament().get(limit = 50)
            filteredImages = tournament.map { it.logo } as ArrayList<String>
            filteredNames = tournament.map { it.name } as ArrayList<String>

            Log.d("filterTeams", tournament.map { it.name }.toString())
            Log.d("filterTeams", filteredNames.toString())
            Log.d("filterTeams", filteredImages.toString())

            adapterP = SimpleListAdapter(filteredImages, filteredNames, "logo/orgs/")

            filterTournament()

            adapterP.setOnItemClickCallback(object : SimpleListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: String) {
                    val intent = Intent(this@ManageTournament, AddTournament::class.java)
                    intent.putExtra("mode", "edit")
                    intent.putExtra("name", data)
                    startActivity(intent)
                }

                override fun delData(pos: Int) {
                    val tour = filteredTournament.get(pos)

                    val alert = MaterialAlertDialogBuilder(this@ManageTournament)
                        .setTitle("Delete Team")
                        .setMessage("Are you sure you want to delete ${tour.name}?")
                        .setNegativeButton("Cancel") { dialog, which ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Delete") { dialog, which ->
                            CoroutineScope(Dispatchers.Main).launch {
                                tour.delete(tour.id)

                                tournament.removeAt(pos)
                                filteredImages.removeAt(pos)
                                filteredNames.removeAt(pos)
                                filteredTournament.removeAt(pos)

                                adapterP.setData(filteredImages, filteredNames)

                                Snackbar.make(
                                    addBtn,
                                    R.string.teamdeleted,
                                    Snackbar.LENGTH_SHORT
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

            rvTournament.adapter = adapterP
            rvTournament.layoutManager = LinearLayoutManager(
                this@ManageTournament,
                LinearLayoutManager.VERTICAL,
                false
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
                    filterTournament()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredImages, filteredNames)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.toString() == "" && searchText != "") {
                    searchText = newText.toString()
                    filterTournament()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredImages, filteredNames)
                    }
                }
                return true
            }
        })
    }
    fun filterTournament() {
        filteredTournament.clear()
        filteredImages.clear()
        filteredNames.clear()

        if (searchText == "") {
            filteredTournament.addAll(
                tournament.filter {
                    it.name != ""
                }
            )
        }
        else {
            filteredTournament = tournament.filter {
                it.name.contains(searchText, true)
            } as ArrayList<Tournament>
        }

        filteredImages = filteredTournament.map { it.logo } as ArrayList<String>
        filteredNames = filteredTournament.map { it.name } as ArrayList<String>

        if (filteredTournament.isEmpty()) {
            rvTournament.visibility = View.GONE
        }
        else {
            rvTournament.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.Main).launch {
            adapterP.setData(filteredImages, filteredNames)
        }
    }
}