package proyek.andro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import proyek.andro.adminActivity.AdminActivity
import proyek.andro.seeder.GameSeeder
import proyek.andro.seeder.MatchSeeder
import proyek.andro.seeder.OrganizationSeeder
import proyek.andro.seeder.TeamSeeder
import proyek.andro.seeder.TournamentPhaseSeeder
import proyek.andro.seeder.TournamentSeeder
import proyek.andro.seeder.UserFavoriteSeeder
import proyek.andro.seeder.UserSeeder
import proyek.andro.userActivity.UserActivity
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    var runSeeder = false // JANGAN DIJADIIN TRUE KALAU DATA SUDAH ADA DI FIREBASE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (runSeeder) {
            runBlocking {
                launch {
//                     UserSeeder().run()
//                     GameSeeder().run()
//                     OrganizationSeeder().run()
//                     TeamSeeder().run()
//                     PlayerSeeder().run()
//                     TournamentSeeder().run()
//                     TournamentPhaseSeeder().run()
//                     ParticipantSeeder().run()
//                     MatchSeeder().run()
//                     PlayerHistorySeeder().run()
//                    UserFavoriteSeeder().run()
                }
            }
        }

        val sp = getSharedPreferences("login_session", MODE_PRIVATE)

        // if user is not logged in or session has expired
        if (
            sp.getString("email", null) == null ||
            sp.getString("remember_token", null) == null ||
            sp.getString("expires_at", null) == null ||
            LocalDate.now() > LocalDate.parse(sp.getString("expires_at", null))
        ) {
            var intent = Intent(this, Login::class.java)
            intent.putExtra("timed_out", true)
            startActivity(intent)
        }

        else {
            // logged in as admin
            if (sp.getInt("role", 2) == 1)
                startActivity(Intent(this, AdminActivity::class.java))

            // logged in as user
            else if (sp.getInt("role", 2) == 0)
                startActivity(Intent(this, UserActivity::class.java))

            // unknown role
            else {
                var intent = Intent(this, Login::class.java)
                intent.putExtra("timed_out", true)
                startActivity(intent)
            }
        }
    }
}