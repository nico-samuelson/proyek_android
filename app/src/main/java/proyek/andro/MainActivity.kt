package proyek.andro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import proyek.andro.seeder.GameSeeder
import proyek.andro.seeder.TournamentSeeder

class MainActivity : AppCompatActivity() {

    var runSeeder = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (runSeeder) {
            GameSeeder().run()
            TournamentSeeder().run()
        }

        startActivity(Intent(this, Homepage::class.java))
    }
}