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
import proyek.andro.adapter.SimpleListAdapter2
import proyek.andro.model.Highlights
import proyek.andro.model.Tournament

class ManageHighlights : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_highlights)

        val etTournament = findViewById<MaterialAutoCompleteTextView>(R.id.etTournament)
        val rvHighlight = findViewById<RecyclerView>(R.id.rv_highlights)
        val addBtn : FloatingActionButton = findViewById(R.id.addHighlightBtn)

        var tournaments : ArrayList<Tournament> = ArrayList()
        var selectedTournament : Tournament? = null
        var highlights : ArrayList<Highlights> = ArrayList()

        CoroutineScope(Dispatchers.Main).launch {
            tournaments = Tournament().get()
            etTournament.setSimpleItems(tournaments.map { it.name }.toTypedArray())
        }

        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        rvHighlight.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapterP = SimpleListAdapter2(highlights.map { it.title })

        adapterP.setOnItemClickCallback(object : SimpleListAdapter2.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                val intent = Intent(this@ManageHighlights, AddHighlight::class.java)
                intent.putExtra("mode", "edit")
                intent.putExtra("highlight", highlights.filter { it.title == data && it.tournament == selectedTournament?.id }.firstOrNull()?.id)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    val currentItem = highlights.get(pos)
                    highlights.remove(currentItem)
                    currentItem.delete(currentItem.id)
                }.invokeOnCompletion {
                    adapterP.setData(highlights.map { it.title })
                }
            }
        })

        rvHighlight.adapter = adapterP

        etTournament.setOnItemClickListener { parent, view, position, id ->
            findViewById<LinearLayout>(R.id.empty_view).visibility = View.GONE

            selectedTournament = tournaments.filter { it.name == parent.getItemAtPosition(position).toString() }.firstOrNull()

            CoroutineScope(Dispatchers.Main).launch {
                highlights = Highlights().get(
                    filter = Filter.equalTo("tournament", selectedTournament?.id),
                    order = arrayOf(arrayOf("tournament", "ASC"))
                )
                if (highlights.size > 0) {
                    adapterP.setData(highlights.map { it.title })
                }
                else {
                    findViewById<LinearLayout>(R.id.empty_view).visibility = View.VISIBLE
                }
            }
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, AddHighlight::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }
    }
}