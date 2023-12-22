package proyek.andro.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import proyek.andro.R

class PlayerProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            val intent = Intent(this, UserHomepageFr::class.java)
            startActivity(intent)
        }
    }
}