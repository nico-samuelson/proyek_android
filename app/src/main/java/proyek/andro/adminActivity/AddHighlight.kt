package proyek.andro.adminActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.model.Highlights
import proyek.andro.model.Tournament
import java.util.UUID

class AddHighlight : AppCompatActivity() {
    private var tournaments: ArrayList<Tournament> = ArrayList()
    private var highlight: Highlights? = null

    private var mode: String? = null
    private var name: String? = null

    private lateinit var etTitle: TextView
    private lateinit var etTournament: AutoCompleteTextView
    private lateinit var etThumbnail: TextView
    private lateinit var etLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_highlight)

        etTitle = findViewById(R.id.etTitle)
        etTournament = findViewById(R.id.etTournament)
        etThumbnail = findViewById(R.id.etThumbnail)
        etLink = findViewById(R.id.etLink)

        val backBtn: ImageView = findViewById(R.id.backBtn)
        val submitBtn: MaterialButton = findViewById(R.id.submitBtn)
        val pageTitle: TextView = findViewById(R.id.pageTitle)

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("highlight")

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        CoroutineScope(Dispatchers.Main).launch {
            tournaments = Tournament().get()
        }.invokeOnCompletion {
            (etTournament as MaterialAutoCompleteTextView).setSimpleItems(
                tournaments.map { it.name }.toTypedArray()
            )

            if (mode == "edit") {
                pageTitle.text = "Edit Highlight"
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
                val title = etTitle.text.toString()
                val tournament = etTournament.text.toString()
                val thumbnail = etThumbnail.text.toString()
                val link = etLink.text.toString()

                if (mode == "add") {
                    CoroutineScope(Dispatchers.Main).launch {
                        val newTournamentPhase = Highlights(
                            UUID.randomUUID().toString(),
                            tournaments.filter { it.name == tournament }.first().id,
                            title,
                            thumbnail,
                            link
                        )

                        newTournamentPhase.insertOrUpdate()
                        startActivity(Intent(this@AddHighlight, ManageHighlights::class.java))
                    }
                } else if (mode == "edit") {
                    highlight?.title = title
                    highlight?.tournament = tournaments.filter { it.name == tournament }.first().id
                    highlight?.thumbnail = thumbnail
                    highlight?.link = link

                    CoroutineScope(Dispatchers.Main).launch {
                        highlight?.insertOrUpdate()
                        startActivity(Intent(this@AddHighlight, ManageHighlights::class.java))
                    }
                }
            }
        }
    }

    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            highlight = Highlights().find(name!!)

            etTitle.text = highlight?.title

            val tournament = tournaments.filter { it.id == highlight?.tournament }.first()

            etTournament.setText(tournament.name)

            (etTournament as MaterialAutoCompleteTextView).setSimpleItems(
                tournaments
                    .map { it.name }
                    .toTypedArray()
            )

            etThumbnail.text = highlight?.thumbnail
            etLink.text = highlight?.link
        }

    }

    fun validate(): Boolean {
        if (
            etTitle.text.toString().isEmpty() ||
            etTournament.text.toString().isEmpty() ||
            etThumbnail.text.toString().isEmpty() ||
            etLink.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }
}