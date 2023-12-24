package proyek.andro.adminActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.model.Game
import proyek.andro.model.Organization
import proyek.andro.model.Team
import java.util.Locale
import java.util.UUID

class AddTeams : AppCompatActivity() {
    private var games : ArrayList<Game> = ArrayList()
    private var organizations : ArrayList<Organization> = ArrayList()
    private var team : Team? = null

    private lateinit var etName : TextView
    private lateinit var etGame : AutoCompleteTextView
    private lateinit var etFounded : TextView
    private lateinit var etCoach : TextView
    private lateinit var etStatus : AutoCompleteTextView
    private lateinit var etOrg : AutoCompleteTextView
    private lateinit var etDescription : TextView

    private val storageRef = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_teams)

        etName = findViewById(R.id.etName)
        etGame = findViewById(R.id.etGame)
        etFounded = findViewById(R.id.etFounded)
        etCoach = findViewById(R.id.etCoach)
        etStatus = findViewById(R.id.etStatus)
        etOrg = findViewById(R.id.etOrg)
        etDescription = findViewById(R.id.etDescription)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        val submitBtn : MaterialButton = findViewById(R.id.submitBtn)

        (etStatus as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Active", "Inactive"))

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        CoroutineScope(Dispatchers.Main).launch {
            games = Game().get(limit = 50)
            organizations = Organization().get(limit = 50)
        }.invokeOnCompletion {
            (etGame as MaterialAutoCompleteTextView).setSimpleItems(
                games.map { it.name }.toTypedArray()
            )
            (etOrg as MaterialAutoCompleteTextView).setSimpleItems(
                organizations.map { it.name }.toTypedArray()
            )
        }

        submitBtn.setOnClickListener {
            if (!validate()) {
                Snackbar.make(submitBtn, R.string.fillAll, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            }
            else {
                val name = etName.text.toString()
                val game = etGame.text.toString()
                val founded = etFounded.text.toString()
                val coach = etCoach.text.toString()
                val status = etStatus.text.toString()
                val org = etOrg.text.toString()
                val description = etDescription.text.toString()

                val orgLogo = organizations.filter { it.name == org }.map { it.logo }.firstOrNull()
                if (orgLogo == null) {
                    Log.d("AddTeams", "Organization logo not found")
                    return@setOnClickListener
                }
                Log.d("AddTeams", "Organization logo found: $orgLogo")

                val newTeam = Team(
                    UUID.randomUUID().toString(),
                    game,
                    org,
                    name,
                    orgLogo,
                    coach,
                    founded,
                    description,
                    if (status == "Active") 1 else 0,
                )

                CoroutineScope(Dispatchers.Main).launch {
                    newTeam.insertOrUpdate()
                    startActivity(Intent(this@AddTeams, ManageOrganizations::class.java))
                }
            }
        }
    }

    fun validate() : Boolean {
        if (etName.text.toString().isEmpty() || etGame.text.toString().isEmpty() ||
            etFounded.text.toString().isEmpty() || etCoach.text.toString().isEmpty() ||
            etStatus.text.toString().isEmpty() || etOrg.text.toString().isEmpty() || etDescription.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }
}