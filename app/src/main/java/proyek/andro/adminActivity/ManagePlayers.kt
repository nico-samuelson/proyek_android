package proyek.andro.adminActivity

import android.content.Intent
import android.content.res.ColorStateList
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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.SimpleListAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Game
import proyek.andro.model.Player
import proyek.andro.model.Team

class ManagePlayers : AppCompatActivity() {
    private var games : ArrayList<Game> = ArrayList()
    private var players : ArrayList<Player> = ArrayList()
    private var teams : ArrayList<ArrayList<Team>> = ArrayList()

    private var filteredPlayer : ArrayList<Player> = ArrayList()
    private var filteredImages : ArrayList<String> = ArrayList()
    private var filteredNames : ArrayList<String> = ArrayList()
    private var filteredGame : Int = 0
    private var searchText : String = ""

    private lateinit var backBtn : ImageView
    private lateinit var addPlayerBtn : FloatingActionButton
    private lateinit var loadingView : LinearLayout
    private lateinit var emptyView : LinearLayout
    private lateinit var rvPlayer : RecyclerView
    private lateinit var search_view : SearchView
    private lateinit var etSearch : EditText

    private lateinit var adapterP : SimpleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_players)

        backBtn = findViewById(R.id.backBtn)
        addPlayerBtn = findViewById(R.id.addPlayerBtn)
        loadingView = findViewById(R.id.loading_view)
        emptyView = findViewById(R.id.empty_view)
        rvPlayer = findViewById(R.id.viewPlayers)

        // fetch and show data
        CoroutineScope(Dispatchers.Main).launch {
            games = Game().get(limit=25)
            players = Player().get(order = arrayOf(arrayOf("team", "ASC"), arrayOf("name", "ASC")))
            games.forEach {
                teams.add(Team().get(
                    filter = Filter.equalTo("game", it.id),
                    order = arrayOf(arrayOf("game", "ASC"))
                ))
            }

            makeGameFilters()
            filterPlayers()

            if (filteredPlayer.size == 0) {
                loadingView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }
            else {
                loadingView.visibility = View.GONE
                emptyView.visibility = View.GONE
                rvPlayer.visibility = View.VISIBLE
            }

            adapterP = SimpleListAdapter(filteredImages, filteredNames, "logo/orgs/")

            adapterP.setOnItemClickCallback(object : SimpleListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: String) {
                    val intent = Intent(this@ManagePlayers, AddPlayer::class.java)
                    intent.putExtra("mode", "edit")
                    intent.putExtra("name", data)
                    startActivity(intent)
                }

                override fun delData(pos: Int) {
                    val player = filteredPlayer.get(pos)

                    val alert = MaterialAlertDialogBuilder(this@ManagePlayers)
                        .setTitle("Delete Player")
                        .setMessage("Are you sure you want to delete ${player.name}?")
                        .setNegativeButton("Cancel") { dialog, which ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Delete") { dialog, which ->
                            CoroutineScope(Dispatchers.Main).launch {
                                StorageHelper().deleteFile(player.photo)
                                player.delete(player.id)

                                players.remove(player)
                                filteredImages.removeAt(pos)
                                filteredNames.removeAt(pos)
                                filteredPlayer.removeAt(pos)

                                adapterP.setData(filteredImages, filteredNames)

                                Snackbar.make(
                                    addPlayerBtn,
                                    R.string.playerdeleted,
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

            rvPlayer.layoutManager = LinearLayoutManager(
                this@ManagePlayers,
                LinearLayoutManager.VERTICAL,
                false
            )
            rvPlayer.adapter = adapterP
        }

        search_view = findViewById(R.id.search_view)
        etSearch = search_view.findViewById(androidx.appcompat.R.id.search_src_text)
        etSearch.setHintTextColor(resources.getColor(R.color.disabled, null))
        etSearch.setTextColor(resources.getColor(R.color.white, null))

        backBtn.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        // fab click listener
        addPlayerBtn.setOnClickListener {
            val intent = Intent(this, AddPlayer::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }

        // search players
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // clear on screen keyboard to prevent this method from being called twice
                search_view.clearFocus()

                if (query.toString() != searchText) {
                    searchText = query.toString()
                    filterPlayers()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredImages, filteredNames)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do something when text changes
                if (newText.toString() == "" && searchText != "") {
                    searchText = newText.toString()
                    filterPlayers()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredImages, filteredNames)
                    }
                }
                return true
            }
        })
    }

    fun makeGameFilters() {
        val chips = findViewById<ChipGroup>(R.id.gameChips)
        val colorState = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(0)
            ),
            intArrayOf(
                ContextCompat.getColor(this@ManagePlayers, R.color.white),
                ContextCompat.getColor(this@ManagePlayers, R.color.disabled)
            )
        )

        games.forEachIndexed { index, game ->
            val chip = Chip(this@ManagePlayers)

            chip.id = index
            chip.setTextColor(resources.getColor(R.color.disabled, null))
            chip.chipStrokeWidth = 0f
            chip.text = game.name
            chip.isCheckable = true
            chip.chipBackgroundColor = ColorStateList(
                arrayOf(intArrayOf(0)),
                intArrayOf(
                    ContextCompat.getColor(this@ManagePlayers, R.color.background)
                )
            )

            if (index == filteredGame) chip.isChecked = true
            if (chip.isChecked) chip.setTextColor(colorState)
            else chip.setTextColor(colorState)

            chip.setOnClickListener {
                if (index != filteredGame) {
                    filteredGame = index
                    filterPlayers()
                    CoroutineScope(Dispatchers.Main).launch {
                        adapterP.setData(filteredImages, filteredNames)
                    }
                }
            }

            chips.addView(chip)
        }
    }

    fun filterPlayers() {
        // reset rv states
        filteredPlayer.clear()
        filteredNames.clear()
        filteredImages.clear()

        if (searchText == "") {
            filteredPlayer.addAll(
                players.filter {
                    it.team in teams.get(filteredGame).map { it.id }
                } as ArrayList<Player>
            )
            filteredPlayer.sortBy { it.team }
        }
        else {
            filteredPlayer = players.filter {
                it.team in teams.get(filteredGame).map { it.id } &&
                it.nickname.lowercase().contains(searchText.lowercase())
            } as ArrayList<Player>
            filteredPlayer.sortBy { it.team }
        }

        filteredImages = filteredPlayer.map { player ->
            teams.get(filteredGame).filter { it.id == player.team }.first().logo
        } as ArrayList<String>
        filteredNames = filteredPlayer.map { it.nickname } as ArrayList<String>

        Log.d("filterPlayers", "Players: " + filteredPlayer.map{it.nickname}.toString())
        Log.d("filterPlayers", "Name: " + filteredNames.toString())
        Log.d("filterPlayers", "Images: " + filteredImages.toString())

        if (filteredPlayer.size == 0) emptyView.visibility = View.VISIBLE
        else emptyView.visibility = View.GONE
    }
}