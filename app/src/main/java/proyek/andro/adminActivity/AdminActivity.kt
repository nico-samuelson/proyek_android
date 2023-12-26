package proyek.andro.adminActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.Login
import proyek.andro.R
import proyek.andro.model.User
import proyek.andro.model.UserFavorite
import proyek.andro.userActivity.UserActivity

class AdminActivity : AppCompatActivity() {

    private var user: User? = null

    //    lateinit var parent : UserActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val CRUDPlayer: MaterialCardView = findViewById(R.id.managePlayer)
        val CRUDNews: MaterialCardView = findViewById(R.id.manageNews)
        val CRUDOrgs: MaterialCardView = findViewById(R.id.manageOrganization)
        val CRUDTeams: MaterialCardView = findViewById(R.id.manageTeams)
        val CRUDTourneys: MaterialCardView = findViewById(R.id.manageTournaments)
        val CRUDPhases: MaterialCardView = findViewById(R.id.managePhases)
        val CRUDMatches: MaterialCardView = findViewById(R.id.manageMatches)
        val CRUDParticipants : MaterialCardView = findViewById(R.id.manageParticipant)

        val pageTitle: TextView = findViewById(R.id.pageTitle)
        val logoutBtn : MaterialButton = findViewById(R.id.logoutBtn)

        val sp = getSharedPreferences("login_session", MODE_PRIVATE)

        // log in as admin
        val job = CoroutineScope(Dispatchers.Main).launch {
            val users = User().get<User>(
                filter = Filter.and(
                    Filter.equalTo("role", 1L),
                    Filter.equalTo("email", sp.getString("email", null)),
                    Filter.equalTo("remember_token", sp.getString("remember_token", null))
                )
            )

            if (users.size == 0) {
                val intent = Intent(this@AdminActivity, Login::class.java)
                intent.putExtra("timed_out", true)
                startActivity(intent)
            } else {
                user = users.get(0)
                pageTitle.text = "Welcome back, ${user?.name}"
//                Log.d("user", user?.name.toString())
            }
        }

        logoutBtn.setOnClickListener {
            val editor = sp.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            intent.putExtra("timed_out", true)
            startActivity(intent)
        }

        CRUDPlayer.setOnClickListener {
            val intent = Intent(this, ManagePlayers::class.java)
            startActivity(intent)
        }

        CRUDNews.setOnClickListener {
            val intent = Intent(this, ManageNews::class.java)
            startActivity(intent)
        }

        CRUDOrgs.setOnClickListener {
            val intent = Intent(this, ManageOrganizations::class.java)
            startActivity(intent)

        }

        CRUDTeams.setOnClickListener {
            val intent = Intent(this, ManageTeams::class.java)
            startActivity(intent)
        }

        CRUDTourneys.setOnClickListener {
            val intent = Intent(this, ManageTournament::class.java)
            startActivity(intent)
        }

        CRUDPhases.setOnClickListener {
            val intent = Intent(this, ManagePhase::class.java)
            startActivity(intent)
        }

        CRUDMatches.setOnClickListener {
            val intent = Intent(this, ManageMatch::class.java)
            startActivity(intent)
        }

        CRUDParticipants.setOnClickListener {
            val intent = Intent(this, ManageParticipant::class.java)
            startActivity(intent)
        }
    }

    fun getUser(): User? {
        return this.user
    }
}