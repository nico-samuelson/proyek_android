package proyek.andro.adminActivity

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Filter
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.News
import proyek.andro.model.User
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.UUID

class AddNews : AppCompatActivity() {
    private lateinit var etTitle: TextView
    private lateinit var etContent: TextView
    private lateinit var inputPhoto: ImageView
    private var mode: String? = null
    private var name: String? = null
    private var selectedNews: News? = null
    private var imageURI: Uri? = null
    private val storageRef = FirebaseStorage.getInstance().reference
    var sp : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)

        sp = getSharedPreferences("login_session", MODE_PRIVATE)

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("name")

        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        inputPhoto = findViewById(R.id.inputPhoto)

        val submitBtn: MaterialButton = findViewById(R.id.submitBtn)
        val backBtn: ImageView = findViewById(R.id.backBtn)
        val pageTitle: TextView = findViewById(R.id.pageTitle)

        backBtn.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // get needed data
        if (mode == "edit") {
            pageTitle.text = "Edit News"
            submitBtn.text = "Save"
            showEdit()
        }

        // submit button click listener
        submitBtn.setOnClickListener {
            // make sure all fields are filled
            if (!validate()) {
                Snackbar.make(submitBtn, R.string.fillAll, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            } else {
                val title = etTitle.text.toString()
                val content = etContent.text.toString()
                var photo = ""
                var admin : User? = null

                // add new player
                if (mode == "add") {
                    if (imageURI != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            photo = uploadPhoto(imageURI!!)
                            admin = User().get<User>(
                                filter = Filter.and(
                                    Filter.equalTo("role", 1),
                                    Filter.equalTo("email", sp?.getString("email", ""))
                                ),
                                limit = 1,
                                order = arrayOf(arrayOf("email", "asc"))
                            ).firstOrNull()
                        }.invokeOnCompletion {
                            val newNews = News(
                                UUID.randomUUID().toString(),
                                title,
                                content,
                                admin?.id ?: "",
                                LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")).toString(),
                                "/photos/news/${photo}"
                            )

                            CoroutineScope(Dispatchers.Main).launch {
                                newNews.insertOrUpdate()
                                startActivity(Intent(this@AddNews, ManageNews::class.java))
                            }
                        }
                    } else {
                        val newNews = News(
                            UUID.randomUUID().toString(),
                            title,
                            content,
                            admin?.id ?: "",
                            LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")).toString(),
                            "/photos/news/${photo}"
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            newNews.insertOrUpdate()
                            startActivity(Intent(this@AddNews, ManageNews::class.java))
                        }
                    }
                }

                // edit existing player
                else if (mode == "edit") {
                    if (imageURI != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            StorageHelper().deleteFile(selectedNews?.image ?: "")
                            selectedNews?.image = uploadPhoto(imageURI!!)
                        }
                    }

                    selectedNews?.title = title
                    selectedNews?.content = content


                    CoroutineScope(Dispatchers.Main).launch {
                        selectedNews?.insertOrUpdate()
                        startActivity(Intent(this@AddNews, ManageNews::class.java))
                    }
                }
            }
        }

        // file upload listener
        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                Picasso.get().load(uri).into(inputPhoto)
                imageURI = uri
            }

        inputPhoto.setOnClickListener { getContent.launch("image/*") }
    }

    fun uploadPhoto(uri: Uri): String {
        val photoID = UUID.randomUUID().toString()
        val fileRef = storageRef.child("photos/news/${photoID}")

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener {
                    Picasso.get().load(it).into(inputPhoto)
                }
            }
            .addOnFailureListener {
                Snackbar.make(
                    inputPhoto,
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
        if (etTitle.text.toString().isEmpty() || etContent.text.toString().isEmpty()) {
            return false
        }
        return true
    }

    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            selectedNews = News().get<News>(
                filter = Filter.equalTo("title", name),
                limit = 1,
                order = arrayOf(arrayOf("title", "asc"))
            ).firstOrNull()

            if (selectedNews != null) {

                etTitle.text = selectedNews?.title
                etContent.text = selectedNews?.content

                CoroutineScope(Dispatchers.Main).launch {
                    storageRef.child(selectedNews!!.image)
                        .downloadUrl
                        .addOnSuccessListener {
                            Picasso.get()
                                .load(it)
                                .networkPolicy(NetworkPolicy.OFFLINE)
                                .into(inputPhoto, object : com.squareup.picasso.Callback {
                                    override fun onSuccess() {}

                                    override fun onError(e: Exception?) {
                                        Picasso.get()
                                            .load(it)
                                            .into(inputPhoto)
                                    }
                                })
                        }
                }
            }
        }
    }
}