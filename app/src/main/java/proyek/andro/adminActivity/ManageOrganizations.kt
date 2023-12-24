package proyek.andro.adminActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.model.Organization

class ManageOrganizations : AppCompatActivity() {
    private var organizations : ArrayList<Organization> = ArrayList()
    lateinit var organizationRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_organizations)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        val addBtn : FloatingActionButton = findViewById(R.id.addOrgButton)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, AddOrganizations::class.java)
            startActivity(intent)
        }

        organizationRV = findViewById(R.id.viewOrgs)
        CoroutineScope(Dispatchers.Main).launch {
            organizations = Organization().get(limit = 50)
            Log.d("organizations", organizations.toString())
        }
    }
}