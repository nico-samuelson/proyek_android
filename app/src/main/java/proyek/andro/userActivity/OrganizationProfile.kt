package proyek.andro.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import proyek.andro.R

class OrganizationProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_profile)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            val intent = Intent(this, UserHomepageFr::class.java)
            startActivity(intent)
        }
    }
}