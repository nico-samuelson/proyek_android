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
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Game
import proyek.andro.model.Player
import proyek.andro.model.Team
import java.util.UUID

class AddPlayer : AppCompatActivity() {
    private lateinit var etName : TextView
    private lateinit var etNickname : TextView
    private lateinit var etPosition : TextView
    private lateinit var etNationality : TextView
    private lateinit var etTeam : AutoCompleteTextView
    private lateinit var etStatus : AutoCompleteTextView
    private lateinit var etCaptain : AutoCompleteTextView
    private lateinit var inputPhoto : ImageView

    private var teams : ArrayList<Team> = ArrayList()
    private var games : ArrayList<Game> = ArrayList()
    private var mode : String? = null
    private var name : String? = null
    private var player : Player? = null
    private var imageURI : Uri? = null
    private val storageRef = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("name")

        etName = findViewById(R.id.etName)
        etNickname = findViewById(R.id.etNickname)
        etPosition = findViewById(R.id.etPosition)
        etNationality = findViewById(R.id.etNationality)
        etTeam = findViewById(R.id.etTeam)
        etStatus = findViewById(R.id.etActive)
        etCaptain = findViewById(R.id.etCaptain)
        inputPhoto = findViewById(R.id.inputPhoto)

        val submitBtn : MaterialButton = findViewById(R.id.submitBtn)
        val backBtn : ImageView = findViewById(R.id.backBtn)
        val pageTitle : TextView = findViewById(R.id.pageTitle)

        backBtn.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // get needed data
        CoroutineScope(Dispatchers.Main).launch {
            teams = Team().get(limit=1000)
            games = Game().get(limit=50)
        }.invokeOnCompletion {
            (etStatus as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Active", "Inactive"))
            (etCaptain as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Yes", "No"))
            (etTeam as MaterialAutoCompleteTextView).setSimpleItems(
                teams.map { team -> team.name + " - " +  games.find{ team.game == it.id }?.name }.toTypedArray()
            )
            if (mode == "edit") {
                pageTitle.text = "Edit Player"
                submitBtn.text = "Save"

                showEdit()
            }
        }

        // submit button click listener
        submitBtn.setOnClickListener {
            // make sure all fields are filled
            if (!validate()) {
                Snackbar.make(submitBtn, R.string.fillAll, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            }

            else {
                val team = etTeam.text.toString().split(" - ")[0]
                val game = games.find{it.name == etTeam.text.toString().split(" - ")[1]}?.id.toString()
                val name = etName.text.toString()
                val nickname = etNickname.text.toString()
                val position = etPosition.text.toString()
                val nationality = etNationality.text.toString()
                val status = etStatus.text.toString()
                val captain = etCaptain.text.toString()
                var photo = ""

                // add new player
                if (mode == "add") {
                    if (imageURI != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            photo = uploadPhoto(imageURI!!)
                        }.invokeOnCompletion {
                            val newPlayer = Player(
                                UUID.randomUUID().toString(),
                                teams.find { it.name == team && it.game == game }?.id.toString(),
                                name,
                                nickname,
                                position,
                                nationality,
                                "/photos/players/${photo}",
                                captain == "Yes",
                                if (status == "Active") 1 else 0,
                            )

                            CoroutineScope(Dispatchers.Main).launch {
                                newPlayer.insertOrUpdate()
                                startActivity(Intent(this@AddPlayer, ManagePlayers::class.java))
                            }
                        }
                    }
                    else {
                        val newPlayer = Player(
                            UUID.randomUUID().toString(),
                            teams.find { it.name == team && it.game == game }?.id.toString(),
                            name,
                            nickname,
                            position,
                            nationality,
                            "",
                            captain == "Yes",
                            if (status == "Active") 1 else 0,
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            newPlayer.insertOrUpdate()
                            startActivity(Intent(this@AddPlayer, ManagePlayers::class.java))
                        }
                    }
                }

                // edit existing player
                else if (mode == "edit") {
                    if (imageURI != null) {
                        CoroutineScope(Dispatchers.Main).launch {
                            StorageHelper().deleteFile(player?.photo ?: "")
                            player?.photo = "/photos/players/${uploadPhoto(imageURI!!)}"
                        }
                    }

                    player?.team = teams.find { it.name == team && it.game == game }?.id.toString()
                    player?.name = name
                    player?.nickname = nickname
                    player?.position = position
                    player?.nationality = nationality
                    player?.captain = captain == "Yes"
                    player?.status = if (status == "Active") 1 else 0

                    CoroutineScope(Dispatchers.Main).launch {
                        player?.insertOrUpdate()
                        startActivity(Intent(this@AddPlayer, ManagePlayers::class.java))
                    }
                }
            }
        }

        // file upload listener
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            Picasso.get().load(uri).into(inputPhoto)
            imageURI = uri
        }

        inputPhoto.setOnClickListener { getContent.launch("image/*") }
    }

    fun uploadPhoto(uri : Uri) : String{
        val photoID = UUID.randomUUID().toString()
        val fileRef = storageRef.child("photos/players/${photoID}")

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

    fun validate() : Boolean {
        if (etName.text.toString().isEmpty() || etNickname.text.toString().isEmpty() ||
            etPosition.text.toString().isEmpty() || etNationality.text.toString().isEmpty() ||
            etTeam.text.toString().isEmpty() || etStatus.text.toString().isEmpty() ||
            etCaptain.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }

    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            player = Player().get<Player>(
                filter = Filter.equalTo("nickname", name),
                limit = 1,
                order = arrayOf(arrayOf("nickname", "asc"))
            ).firstOrNull()

            if (player != null) {
                val team = teams.find { it.id == player?.team }

                etName.text = player?.name
                etNickname.text = player?.nickname
                etPosition.text = player?.position
                etNationality.text = player?.nationality
                etTeam.setText(team?.name + " - " + games.find { it.id == team?.game }?.name)
                etStatus.setText(if (player?.status == 1L) "Active" else "Inactive")
                etCaptain.setText(if (player?.captain == true) "Yes" else "No")

                (etStatus as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Active", "Inactive"))
                (etCaptain as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Yes", "No"))
                (etTeam as MaterialAutoCompleteTextView).setSimpleItems(
                    teams.map { team -> team.name + " - " +  games.find{ team.game == it.id }?.name }.toTypedArray()
                )

                CoroutineScope(Dispatchers.Main).launch {
                    storageRef.child(player!!.photo)
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