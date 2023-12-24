package proyek.andro.adminActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import proyek.andro.R

class ManageTeams : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_teams)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        val addBtn : FloatingActionButton = findViewById(R.id.addTeamButton)

        addBtn.setOnClickListener {
            val intent = Intent(this, AddTeams::class.java)
            startActivity(intent)
        }
    }
}