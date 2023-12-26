package proyek.andro.adminActivity

import android.content.Intent
import android.hardware.camera2.CameraManager.TorchCallback
import android.net.Uri
import android.os.Bundle
import android.support.annotation.ArrayRes
import android.util.Log
import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import proyek.andro.adapter.SimpleListAdapter
import proyek.andro.adapter.SimpleListAdapter2
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Game
import proyek.andro.model.Participant
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import java.util.UUID

class AddTournament : AppCompatActivity() {
    private var games: ArrayList<Game> = ArrayList()
    private var tournament: Tournament? = null

    private var mode: String? = null
    private var name: String? = null

    private lateinit var etName: TextView
    private lateinit var etGame: AutoCompleteTextView
    private lateinit var etStartDate: TextView
    private lateinit var etEndDate: TextView
    private lateinit var etPrizePool: TextView
    private lateinit var etOrganizer: TextView
    private lateinit var etType: AutoCompleteTextView
    private lateinit var etLocation: TextView
    private lateinit var etVenue: TextView
    private lateinit var etDescription: TextView
    private lateinit var etStatus: AutoCompleteTextView
    private lateinit var inputBanner: ImageView
    private lateinit var inputLogo: ImageView
    private lateinit var rvLocation: RecyclerView
    private lateinit var rvVenue: RecyclerView

    var bannerURI: Uri? = null
    var logoURI: Uri? = null

    var locations = listOf<String>()
    var venues = listOf<String>()

    val storageRef = FirebaseStorage.getInstance().reference

    var adapterL: SimpleListAdapter2? = null
    var adapterV: SimpleListAdapter2? = null

    var selectedGame : Game? = null
    var teams : ArrayList<Team> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tournament)

        etName = findViewById(R.id.etName)
        etGame = findViewById(R.id.etGame)
        etStartDate = findViewById(R.id.etStart)
        etEndDate = findViewById(R.id.etEnd)
        etPrizePool = findViewById(R.id.etPrizepool)
        etOrganizer = findViewById(R.id.etOrganizer)
        etLocation = findViewById(R.id.etLocation)
        etVenue = findViewById(R.id.etVenue)
        etDescription = findViewById(R.id.etDescription)
        etStatus = findViewById(R.id.etStatus)
        etType = findViewById(R.id.etType)
        inputBanner = findViewById(R.id.inputBanner)
        inputLogo = findViewById(R.id.inputLogo)
        rvLocation = findViewById(R.id.rv_locations)
        rvVenue = findViewById(R.id.rv_venue)

        val backBtn: ImageView = findViewById(R.id.backBtn)
        val submitBtn: MaterialButton = findViewById(R.id.submitBtn)
        val pageTitle: TextView = findViewById(R.id.pageTitle)

        mode = intent.getStringExtra("mode")
        name = intent.getStringExtra("name")

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        CoroutineScope(Dispatchers.Main).launch {
            games = Game().get(limit = 50)
        }.invokeOnCompletion {
            (etGame as MaterialAutoCompleteTextView).setSimpleItems(
                games.map { it.name }.toTypedArray()
            )
            (etType as MaterialAutoCompleteTextView).setSimpleItems(arrayOf("Offline", "Online"))
            (etStatus as MaterialAutoCompleteTextView).setSimpleItems(
                arrayOf(
                    "Upcoming",
                    "Ongoing",
                    "Finished",
                    "Postponed",
                    "Canceled"
                )
            )
            if (mode == "edit") {
                pageTitle.text = "Edit tournament"
                submitBtn.text = "Save"

                showEdit()
            }
        }

        submitBtn.setOnClickListener {
            if (!validate()) {
                Snackbar.make(submitBtn, R.string.fillAll, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            } else {
                val name = etName.text.toString()
                val game = etGame.text.toString()
                val start = etStartDate.text.toString()
                val end = etEndDate.text.toString()
                val prizePool = etPrizePool.text.toString()
                val organizer = etOrganizer.text.toString()
                val type = etType.text.toString()
                val description = etDescription.text.toString()
                val status = etStatus.text.toString()
                val logo = ""
                val banner = ""

                if (mode == "add") {
                    CoroutineScope(Dispatchers.Main).launch {
                        uploadPhoto(bannerURI!!, "banner/tournaments", inputBanner)
                        uploadPhoto(logoURI!!, "logo/tournaments", inputLogo)
                    }.invokeOnCompletion {
                        val status =
                            if (status == "Upcoming") 0 else if (status == "Ongoing") 1 else if (status == "Finished") 2 else if (status == "Postponed") 3 else 4

                        val newTournament = Tournament(
                            UUID.randomUUID().toString(),
                            games.find { it.name == game }?.id!!,
                            name,
                            start,
                            end,
                            prizePool.toLong(),
                            organizer,
                            type,
                            locations,
                            venues,
                            description,
                            logo,
                            banner,
                            status.toLong(),
                        )

                        CoroutineScope(Dispatchers.Main).launch {
                            newTournament.insertOrUpdate()
                            startActivity(Intent(this@AddTournament, ManageTournament::class.java))
                        }
                    }

                } else if (mode == "edit") {
                    if (bannerURI != null) {
                        Log.d("banner", "not null")
                        CoroutineScope(Dispatchers.Main).launch {
                            StorageHelper().deleteFile("banner/tournaments/${tournament?.banner}")
                            val newTournamentBanner =
                                uploadPhoto(bannerURI!!, "banner/tournaments", inputBanner)
                            tournament?.banner = newTournamentBanner
                        }
                    }
                    if (logoURI != null) {
                        Log.d("logo", "not null")
                        CoroutineScope(Dispatchers.Main).launch {
                            StorageHelper().deleteFile("logo/tournaments/${tournament?.logo}")
                            val newTournamentLogo =
                                uploadPhoto(logoURI!!, "logo/tournaments", inputLogo)
                            tournament?.logo = newTournamentLogo
                        }
                    }
                    tournament?.game = games.find { it.name == game }?.id!!
                    tournament?.name = name
                    tournament?.start_date = start
                    tournament?.end_date = end
                    tournament?.prize_pool = prizePool.toLong()
                    tournament?.organizer = organizer
                    tournament?.type = type
                    tournament?.location = locations
                    tournament?.venue = venues
                    tournament?.description = description
                    tournament?.status = if (status == "Active") 1 else 0

                    CoroutineScope(Dispatchers.Main).launch {
                        tournament?.insertOrUpdate()
                        startActivity(Intent(this@AddTournament, ManageTournament::class.java))
                    }
                }
            }
        }

        val getBanner =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                Picasso.get().load(uri).into(inputBanner)
                bannerURI = uri
            }

        val getLogo = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            Picasso.get().load(uri).into(inputLogo)
            logoURI = uri
        }

        inputBanner.setOnClickListener { getBanner.launch("image/*") }
        inputLogo.setOnClickListener { getLogo.launch("image/*") }

        adapterL = SimpleListAdapter2(locations)
        adapterV = SimpleListAdapter2(venues)

        adapterL?.setOnItemClickCallback(object : SimpleListAdapter2.OnItemClickCallback {
            override fun onItemClicked(data: String) {}

            override fun delData(pos: Int) {
                locations = locations.filterIndexed { index, _ -> index != pos }
                adapterL?.setData(locations)
            }
        })

        adapterV?.setOnItemClickCallback(object : SimpleListAdapter2.OnItemClickCallback {
            override fun onItemClicked(data: String) {}

            override fun delData(pos: Int) {
                venues = venues.filterIndexed { index, _ -> index != pos }
                adapterV?.setData(venues)
            }
        })

        etVenue.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                venues = venues + etVenue.text.toString()
                Log.d("venues", venues.toString())
                adapterV?.setData(venues)
                etVenue.text = null
                etVenue.clearFocus()
            }
            false
        }

        etLocation.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                locations = locations + etLocation.text.toString()
                Log.d("locations", locations.toString())
                adapterL?.setData(locations)
                etLocation.text = null
                etLocation.clearFocus()
            }
            false
        }

        etGame.setOnItemClickListener{ parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            selectedGame = games.find { it.name == selectedItem }
            etGame.setText(selectedItem)
            etGame.clearFocus()
            CoroutineScope(Dispatchers.Main).launch {
                teams = Team().get(
                    filter = Filter.equalTo("game", selectedGame?.id),
                    order = arrayOf(arrayOf("game", "asc"))
                )
            }
        }

        rvLocation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvVenue.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rvLocation.adapter = adapterL
        rvVenue.adapter = adapterV
    }

    fun showEdit() {
        CoroutineScope(Dispatchers.Main).launch {
            tournament = Tournament().get<Tournament>(
                filter = Filter.equalTo("name", name!!),
                limit = 1,
                order = arrayOf(arrayOf("name", "asc"))
            ).firstOrNull()

            if (tournament != null) {

                etName.text = tournament!!.name
                etGame.setText(games.find { it.id == tournament?.game }?.name)
                etStartDate.text = tournament!!.start_date
                etEndDate.text = tournament!!.end_date
                etPrizePool.text = tournament!!.prize_pool.toString()
                etOrganizer.text = tournament!!.organizer
                etType.setText(tournament!!.type)
                etStatus.setText(if (tournament!!.status == 1L) "Active" else "Inactive", false)
                etDescription.text = tournament!!.description

                locations = tournament!!.location
                venues = tournament!!.venue

                adapterL?.setData(locations)
                adapterV?.setData(venues)

                val bannerURI =
                    StorageHelper().getImageURI(tournament!!.banner, "banner/tournaments")
                val logoURI = StorageHelper().getImageURI(tournament!!.logo, "logo/tournaments")

                Picasso.get().load(bannerURI).resize(200, 150).centerCrop().into(inputBanner)
                Picasso.get().load(logoURI).fit().into(inputLogo)

                (etGame as MaterialAutoCompleteTextView).setSimpleItems(
                    games.map { it.name }.toTypedArray()
                )
                (etType as MaterialAutoCompleteTextView).setSimpleItems(
                    arrayOf(
                        "Offline",
                        "Online"
                    )
                )
                (etStatus as MaterialAutoCompleteTextView).setSimpleItems(
                    arrayOf(
                        "Upcoming",
                        "Ongoing",
                        "Finished",
                        "Postponed",
                        "Canceled"
                    )
                )
            }
        }
    }

    fun validate(): Boolean {
        if (etName.text.toString().isEmpty() || etGame.text.toString().isEmpty() ||
            etStartDate.text.toString().isEmpty() || etEndDate.text.toString().isEmpty() ||
            etPrizePool.text.toString().isEmpty() || etOrganizer.text.toString()
                .isEmpty() || etType.text.toString().isEmpty() ||
            locations.size == 0 || venues.size == 0 ||
            etStatus.text.toString().isEmpty() || etDescription.text.toString().isEmpty()
        ) {
            return false
        }
        return true
    }

    fun uploadPhoto(uri: Uri, path: String, imageView: ImageView): String {
        val photoID = UUID.randomUUID().toString()
        val fileRef = storageRef.child("${path}/${photoID}")

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener {
                    Picasso.get().load(it).into(imageView)
                }
            }
            .addOnFailureListener {
                Snackbar.make(
                    imageView,
                    R.string.uploadFailed,
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setBackgroundTint(resources.getColor(R.color.light, null))
                    setTextColor(resources.getColor(R.color.black, null))
                }.show()
            }

        return photoID
    }
}