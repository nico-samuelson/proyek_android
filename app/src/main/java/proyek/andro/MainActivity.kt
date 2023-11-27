package proyek.andro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import proyek.andro.seeder.GameSeeder
import proyek.andro.seeder.TournamentSeeder

class MainActivity : AppCompatActivity() {

    var runSeeder = false // JANGAN DIJADIIN TRUE KALAU DATA SUDAH ADA DI FIREBASE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (runSeeder) {
            runBlocking {
                launch {
                    GameSeeder().run()
                    TournamentSeeder().run()
                }
            }
        }

        startActivity(Intent(this, Homepage::class.java))
    }
}