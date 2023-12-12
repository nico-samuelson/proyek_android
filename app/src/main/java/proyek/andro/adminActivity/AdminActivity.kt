package proyek.andro.adminActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.card.MaterialCardView
import proyek.andro.R

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val CRUDPlayer : MaterialCardView = findViewById(R.id.managePlayer)
        val CRUDNews : MaterialCardView = findViewById(R.id.manageNews)
        val CRUDOrgs : MaterialCardView = findViewById(R.id.manageOrganization)

        CRUDPlayer.setOnClickListener {
            val intent = Intent(this, ManagePlayers::class.java)
            startActivity(intent)
        }

        CRUDNews.setOnClickListener {
            val intent = Intent(this, ManageNews::class.java)
            startActivity(intent)
        }
    }
}