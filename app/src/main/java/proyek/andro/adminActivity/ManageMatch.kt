package proyek.andro.adminActivity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.SimpleListAdapter
import proyek.andro.model.Match
import proyek.andro.model.PlayerHistory
import proyek.andro.model.Tournament

class ManageMatch : AppCompatActivity() {
    private var match : ArrayList<Match> = ArrayList()
    private var playerHistories : ArrayList<PlayerHistory> = ArrayList()

    private var filteredMatch : ArrayList<Match> = ArrayList()
    private var filteredNames : ArrayList<String> = ArrayList()

    private lateinit var rvMatch : RecyclerView

    private lateinit var search_view: SearchView
    private lateinit var etSearch: EditText
    private var searchText: String = ""

    private lateinit var adapterP: SimpleListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_match)
        val backBtn : ImageView = findViewById(R.id.backBtn)
        val addBtn : FloatingActionButton = findViewById(R.id.addMatchButton)
        val loadingView : CircularProgressIndicator = findViewById(R.id.loading_indicator)

        backBtn.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, AddMatch::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }

        rvMatch = findViewById(R.id.viewMatch)

        loadingView.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            match = Match().get(limit = 50)
            playerHistories = PlayerHistory().get(limit = 3000)

            filteredNames = match.map { it.name } as ArrayList<String>

            Log.d("filterTeams", match.map { it.name }.toString())
            Log.d("filterTeams", filteredNames.toString())
            Log.d("filterTeams", filteredNames.toString())

            filterMatch()

            adapterP = SimpleListAdapter(filteredNames, filteredNames, "logo/orgs/")
            adapterP.setOnItemClickCallback(object : SimpleListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: String) {
                    val intent = Intent(this@ManageMatch, AddMatch::class.java)
                    intent.putExtra("mode", "edit")
                    intent.putExtra("name", data)
                    startActivity(intent)
                }

                override fun delData(pos: Int) {
                    val tour = filteredMatch.get(pos)

                    val alert = MaterialAlertDialogBuilder(this@ManageMatch)
                        .setTitle("Delete Team")
                        .setMessage("Are you sure you want to delete ${tour.name}?")
                        .setNegativeButton("Cancel") { dialog, which ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Delete") { dialog, which ->
                            CoroutineScope(Dispatchers.Main).launch {
                                val matchId = tour.id
                                val playerHistory = playerHistories.filter { it.match == matchId }
                                Log.d("playerHistory", playerHistory.size.toString())
                                playerHistory.forEach {
                                    PlayerHistory().delete(it.id)
                                }

                                tour.delete(tour.id)

                                match.removeAt(pos)

                                filteredNames.removeAt(pos)
                                filteredMatch.removeAt(pos)

                                adapterP.setData(filteredNames, filteredNames)

                                Snackbar.make(
                                    addBtn,
                                    "Match deleted successfully",
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

            loadingView.visibility = View.GONE

            rvMatch.adapter = adapterP
            rvMatch.layoutManager = LinearLayoutManager(
                this@ManageMatch,
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
                    filterMatch()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredNames, filteredNames)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.toString() == "" && searchText != "") {
                    searchText = newText.toString()
                    filterMatch()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredNames, filteredNames)
                    }
                }
                return true
            }
        })

    }
    fun filterMatch() {
        filteredMatch.clear()
        filteredNames.clear()

        if (searchText == "") {
            filteredMatch.addAll(
                match.filter {
                    it.name != ""
                } as ArrayList<Match>
            )
        }
        else {
            filteredMatch = match.filter {
                it.name.lowercase().contains(searchText, true)
            } as ArrayList<Match>
        }
        filteredNames = filteredMatch.map { it.name } as ArrayList<String>

        if (filteredMatch.isEmpty()) {
            rvMatch.visibility = View.GONE
        }
        else {
            rvMatch.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.Main).launch {
            adapterP.setData(filteredNames, filteredNames)
        }
    }
}