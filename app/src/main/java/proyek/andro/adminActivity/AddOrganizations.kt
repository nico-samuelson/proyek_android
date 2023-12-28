package proyek.andro.adminActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.firestore.Filter
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Organization
import java.util.UUID

class AddOrganizations : AppCompatActivity() {
    private lateinit var inputLogo: ImageView
    private lateinit var etName: TextView
    private lateinit var etCEO: TextView
    private lateinit var etFounded: TextView
    private lateinit var etLocation: TextView
    private lateinit var etWebsite: TextView
    private lateinit var etDescription: TextView
    private lateinit var etStatus: AutoCompleteTextView

    private var mode: String? = null
    private var name: String? = null
    private var organization: Organization? = null

    private var imageURI: Uri? = null

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
        etStatus = findViewById(R.id.etStatus)

        val backBtn: ImageView = findViewById(R.id.backBtn)
        val submitBtn: MaterialButton = findViewById(R.id.submitBtn)
        val pageTitle: TextView = findViewById(R.id.pageTitle)

        (etStatus as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Active", "Inactive"))

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("name")

        if (mode == "edit") {
            pageTitle.text = "Edit Organization"
            submitBtn.text = "Save"

            showEdit()
        }

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        submitBtn.setOnClickListener {
            if (!validate()) {
                Snackbar.make(submitBtn, R.string.fillAll, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            } else {
                val name = etName.text.toString()
                val ceo = etCEO.text.toString()
                val founded = etFounded.text.toString()
                val location = etLocation.text.toString()
                val website = etWebsite.text.toString()
                val description = etDescription.text.toString()
                val status = etStatus.text.toString()

                if (mode == "add") {
                    if (imageURI != null) {
                        CoroutineScope(Dispatchers.Main).launch {
//                            val logo = uploadPhoto(imageURI!!)
                            val newOrg = Organization(
                                UUID.randomUUID().toString(),
                                name,
                                description,
                                name,
                                founded,
                                location,
                                website,
                                ceo,
                                if (status == "Active") 1 else 0,
                            )

                            newOrg.insertOrUpdate()
                            startActivity(
                                Intent(
                                    this@AddOrganizations,
                                    ManageOrganizations::class.java
                                )
                            )
                        }
                    } else {
                        val newOrg = Organization(
                            UUID.randomUUID().toString(),
                            name,
                            description,
                            "",
                            founded,
                            location,
                            website,
                            ceo,
                            if (status == "Active") 1 else 0,
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            newOrg.insertOrUpdate()
                            startActivity(
                                Intent(
                                    this@AddOrganizations,
                                    ManageOrganizations::class.java
                                )
                            )
                        }
                    }
                } else if (mode == "edit") {
                    if (imageURI != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            StorageHelper().deleteFile(organization?.logo ?: "")
                            organization?.logo = "/logo/orgs/${uploadPhoto(imageURI!!)}"
                        }
                    }
                    organization?.name = name
                    organization?.ceo = ceo
                    organization?.founded = founded
                    organization?.location = location
                    organization?.website = website
                    organization?.description = description
                    organization?.status = if (status == "Active") 1 else 0

                    CoroutineScope(Dispatchers.Main).launch {
                        organization?.insertOrUpdate()
                        startActivity(
                            Intent(
                                this@AddOrganizations,
                                ManageOrganizations::class.java
                            )
                        )
                    }
                }
            }
        }

        // file upload listener
        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                Picasso.get().load(uri).into(inputLogo)
                imageURI = uri
            }

        inputLogo.setOnClickListener { getContent.launch("image/*") }
    }

    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            organization = Organization().get<Organization>(
                filter = Filter.equalTo("name", name),
                limit = 1,
                order = arrayOf(arrayOf("name", "asc"))
            ).firstOrNull()

            if (organization != null) {
                etName.text = organization?.name
                etCEO.text = organization?.ceo
                etFounded.text = organization?.founded
                etLocation.text = organization?.location
                etWebsite.text = organization?.website
                etDescription.text = organization?.description
                etStatus.setText(if (organization?.status == 1L) "Active" else "Inactive")

                var signal = true
                val formattedLogo =
                    "logo_" + organization?.name?.replace(" ", "_")?.lowercase() + ".png"

                storageRef.child("logo/orgs/${formattedLogo}").downloadUrl
                    .addOnSuccessListener {
                        Log.d("editOrg", it.toString())
                        Picasso.get().load(it).into(inputLogo)
                        signal = false
                    }

                if (signal) {
                    storageRef.child("logo/orgs/${organization?.logo}").downloadUrl
                        .addOnSuccessListener {
                            Log.d("editOrg", it.toString())
                            Picasso.get().load(it).into(inputLogo)
                            signal = false
                        }
                }
//                    .addOnFailureListener {
//                        // Case 1: Use the default logo when the download fails
//                        Picasso.get().load("logo/orgs/${organization?.logo}").into(inputLogo)
//
//                        // Case 2: Use the photo ID from uploadPhoto function when the download fails
////                        val photoID = uploadPhoto(imageURI!!)
////                        Picasso.get().load("logo/orgs/${photoID}").into(inputLogo)
//
//                        // Case 3: Add your case here
            }
        }
    }

    fun uploadPhoto(uri: Uri): String {
        val photoID = UUID.randomUUID().toString()
        val fileRef = storageRef.child("logo/orgs/${etName.text.toString()}")

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

    fun validate(): Boolean {
        if (etName.text.toString().isEmpty() || etCEO.text.toString().isEmpty() ||
            etFounded.text.toString().isEmpty() || etLocation.text.toString().isEmpty() ||
            etWebsite.text.toString().isEmpty() || etDescription.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }


}