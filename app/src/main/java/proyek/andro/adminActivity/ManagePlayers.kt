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
import proyek.andro.model.Tournament

class ManagePlayers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_players)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        val addPlayerBtn : FloatingActionButton = findViewById(R.id.addPlayerBtn)
        val loadingView : LinearLayout = findViewById(R.id.loading_view)
        val emptyView : LinearLayout = findViewById(R.id.empty_view)
        val rvPlayer : RecyclerView = findViewById(R.id.viewPlayers)

        var players : ArrayList<Player> = ArrayList()
        var images : ArrayList<String> = ArrayList()
        var names : ArrayList<String> = ArrayList()

        var filteredPlayer : ArrayList<Player> = ArrayList()
        var filteredImages : ArrayList<String> = ArrayList()
        var filteredNames : ArrayList<String> = ArrayList()

        lateinit var adapterP : SimpleListAdapter

        var search_view : SearchView = findViewById(R.id.search_view)
        val etSearch = search_view.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        etSearch.setHintTextColor(resources.getColor(R.color.disabled, null))
        etSearch.setTextColor(resources.getColor(R.color.white, null))

        backBtn.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // fab click listener
        addPlayerBtn.setOnClickListener {
            val intent = Intent(this, AddPlayer::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }

        // fetch and show data
        CoroutineScope(Dispatchers.Main).launch {
            players = Player().get(limit=1000, order = arrayOf(arrayOf("team", "ASC"), arrayOf("name", "ASC")))

            if (players.size == 0) {
                loadingView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }
            else {
                players.forEach {
                    images.add(Team().find<Team>(it.team).logo)
                    names.add(it.nickname)
                }

                filteredPlayer.addAll(players)
                filteredImages.addAll(images)
                filteredNames.addAll(names)

                rvPlayer.layoutManager = LinearLayoutManager(
                    this@ManagePlayers,
                    LinearLayoutManager.VERTICAL,
                    false
                )

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

                                    images.remove(player.photo)
                                    names.remove(player.nickname)
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

                loadingView.visibility = View.GONE
                rvPlayer.visibility = View.VISIBLE
                rvPlayer.adapter = adapterP
            }
        }

        // search players
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // reset rv states
                filteredPlayer.clear()
                filteredNames.clear()
                filteredImages.clear()

                CoroutineScope(Dispatchers.Main).launch {
                    filteredPlayer = players.filter {
                        it.nickname.lowercase().contains(query.toString().lowercase())
                    } as ArrayList<Player>
                    filteredImages =
                        filteredPlayer.map { Team().find<Team>(it.team).logo } as ArrayList<String>
                    filteredNames = filteredPlayer.map { it.nickname } as ArrayList<String>

                    Log.d("filtered", filteredPlayer.get(0).nickname)
                    adapterP.setData(filteredImages, filteredNames)
                }

                // clear on screen keyboard to prevent this method from being called twice
                search_view.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do something when text changes
                if (newText.toString() == "") {
                    filteredPlayer.clear()
                    filteredNames.clear()
                    filteredImages.clear()

                    filteredPlayer.addAll(players)
                    filteredImages.addAll(images)
                    filteredNames.addAll(names)

                    adapterP.setData(filteredImages, filteredNames)
                }
                return true
            }
        })
    }
}