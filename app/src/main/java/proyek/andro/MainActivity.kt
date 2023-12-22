package proyek.andro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import proyek.andro.seeder.AdminSeeder
import proyek.andro.seeder.GameSeeder
import proyek.andro.seeder.MatchSeeder
import proyek.andro.seeder.NewsSeeder
import proyek.andro.seeder.OrganizationSeeder
import proyek.andro.seeder.TeamSeeder
import proyek.andro.seeder.TournamentPhaseSeeder
import proyek.andro.seeder.TournamentSeeder
import proyek.andro.seeder.UserSeeder
import proyek.andro.userActivity.UserActivity

class MainActivity : AppCompatActivity() {

    var runSeeder = false// JANGAN DIJADIIN TRUE KALAU DATA SUDAH ADA DI FIREBASE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (runSeeder) {
            runBlocking {
                launch {
//                     AdminSeeder().run()
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
//                    NewsSeeder().run()
                }
            }
        }

        startActivity(Intent(this, UserActivity::class.java))
    }
}