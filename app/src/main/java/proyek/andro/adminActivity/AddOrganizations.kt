package proyek.andro.adminActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import proyek.andro.R
import proyek.andro.model.Organization
import java.util.UUID

class AddOrganizations : AppCompatActivity() {
    private lateinit var inputLogo : ImageView
    private lateinit var etName : TextView
    private lateinit var etCEO : TextView
    private lateinit var etFounded : TextView
    private lateinit var etLocation : TextView
    private lateinit var etWebsite : TextView
    private lateinit var etDescription : TextView

    private var imageURI : Uri? = null

    private val storageRef = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_organizations)

        inputLogo = findViewById(R.id.inputLogo)
        etName = findViewById(R.id.etName)
        etCEO = findViewById(R.id.etCEO)
        etFounded = findViewById(R.id.etFounded)
        etLocation = findViewById(R.id.etLocation)
        etWebsite = findViewById(R.id.etWebsite)
        etDescription = findViewById(R.id.etDescription)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        val submitBtn : MaterialButton = findViewById(R.id.submitBtn)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        submitBtn.setOnClickListener {
            if (!validate()) {
                Snackbar.make(submitBtn, R.string.fillAll, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            }
            else {
                val name = etName.text.toString()
                val ceo = etCEO.text.toString()
                val founded = etFounded.text.toString()
                val location = etLocation.text.toString()
                val website = etWebsite.text.toString()
                val description = etDescription.text.toString()

                var logo = ""
                if (imageURI != null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        logo = uploadPhoto(imageURI!!)
                    }.invokeOnCompletion {
                        val newOrg = Organization(
                            UUID.randomUUID().toString(),
                            name,
                            description,
                            "logo/orgs/${logo}",
                            founded,
                            location,
                            website,
                            ceo,
                            1L
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            newOrg.insertOrUpdate()
                            startActivity(Intent(this@AddOrganizations, ManageOrganizations::class.java))
                        }
                    }
                }
            }
        }

        // file upload listener
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            Picasso.get().load(uri).into(inputLogo)
            imageURI = uri
        }

        inputLogo.setOnClickListener { getContent.launch("image/*") }
    }

    fun uploadPhoto(uri : Uri) : String{
        val photoID = UUID.randomUUID().toString()
        val fileRef = storageRef.child("logo/orgs/${photoID}")

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener {
                    Picasso.get().load(it).into(inputLogo)
                }
            }
            .addOnFailureListener {
                Snackbar.make(
                    inputLogo,
                    R.string.uploadFailed,
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            }

        return photoID
    }

    fun validate() : Boolean {
        if (etName.text.toString().isEmpty() || etCEO.text.toString().isEmpty() ||
            etFounded.text.toString().isEmpty() || etLocation.text.toString().isEmpty() ||
            etWebsite.text.toString().isEmpty() || etDescription.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }


}