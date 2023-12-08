package proyek.andro.userActivity

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.ListAdapter
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Tournament

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFr.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFr : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var rvTournamentCarousel: RecyclerView
    private lateinit var rvOngoing : RecyclerView
    private lateinit var rvUpcoming : RecyclerView
    private lateinit var parent : UserActivity
    private var tournaments = ArrayList<Tournament>()
    private var ongoing = ArrayList<Tournament>()
    private var upcoming = ArrayList<Tournament>()
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        parent = super.requireActivity() as UserActivity

        if (parent.getTournaments().size == 0 || arguments?.getString("refresh") != null)
            job = parent.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTournamentCarousel = view.findViewById(R.id.tournaments_recycler_view)
        rvOngoing = view.findViewById(R.id.ongoing_tournaments)
        rvUpcoming = view.findViewById(R.id.upcoming_tournaments)

        rvTournamentCarousel.layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
        rvOngoing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        CarouselSnapHelper().attachToRecyclerView(rvTournamentCarousel)

//        var selectedChip : Chip? = null

        if (job == null && parent.getTournaments().size > 0) {
            tournaments = parent.getTournaments().filter { it.game == parent.getGames().get(parent.getSelectedGame()).id } as ArrayList<Tournament>
            tournaments = tournaments.sortedByDescending { it.start_date }.toMutableList() as ArrayList<Tournament>

            ongoing = tournaments.filter { it.status == 1L } as ArrayList<Tournament>

            makeChips(view)
            CoroutineScope(Dispatchers.Main).launch {showData()}
        }
        else if (job != null) {
            job?.invokeOnCompletion {
                tournaments = parent.getTournaments().filter { it.game == parent.getGames().get(parent.getSelectedGame()).id } as ArrayList<Tournament>
                tournaments = tournaments.sortedByDescending { it.start_date }.toMutableList() as ArrayList<Tournament>

                ongoing = tournaments.filter { it.status == 1L } as ArrayList<Tournament>
                upcoming = tournaments.filter { it.status == 0L } as ArrayList<Tournament>

                makeChips(view)
                CoroutineScope(Dispatchers.Main).launch {
                    showData()
                    job = null
                }
            }
        }
    }

    fun makeChips(view : View) {
        var chips = view.findViewById<ChipGroup>(R.id.gameChips)
        val hsv : HorizontalScrollView = view.findViewById(R.id.horizontalScrollView)
        val colorState = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(0)
            ),
            intArrayOf(
                ContextCompat.getColor(parent, R.color.primary),
                ContextCompat.getColor(parent, R.color.background)
            )
        )

        parent.getGames().forEachIndexed { index, game ->
            val chip = Chip(context)

            chip.id = index
            chip.setTextColor(resources.getColor(R.color.white, null))
            chip.chipStrokeColor = resources.getColorStateList(R.color.primary, null)
            chip.minHeight = 56
            chip.chipCornerRadius = 50f
            chip.text = game.name
            chip.isCheckable = true

            if (index == parent.getSelectedGame()) {
                chip.isChecked = true
//                selectedChip = chip
            }
            if (chip.isChecked) chip.chipBackgroundColor = colorState
            else chip.chipBackgroundColor = colorState

            chip.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    parent.setTournamentBanners(ArrayList())
                    parent.setTournamentLogos(ArrayList())
                    parent.setSelectedGame(chip.id)

                    tournaments = parent.getTournaments().filter { it.game == parent.getGames().get(chip.id).id } as ArrayList<Tournament>
                    tournaments = tournaments.sortedByDescending { it.start_date }.toMutableList() as ArrayList<Tournament>
                    ongoing = tournaments.filter { it.status == 1L } as ArrayList<Tournament>
                    upcoming = tournaments.filter { it.status == 0L } as ArrayList<Tournament>

                    CoroutineScope(Dispatchers.Main).launch {showData()}
                }
            }

            chips.addView(chip)
        }
    }

    suspend fun showData() {
        val helper = StorageHelper()

        if (parent.getTournamentBanners().size == 0 || arguments?.getString("refresh") != null) {
            parent.setTournamentBanners(
                helper.preloadImages(
                    tournaments.map { it.banner },
                    "banner/tournaments/"
                )
            )
            parent.setTournamentLogos(
                helper.preloadImages(
                    tournaments.map { it.logo },
                    "logo/tournaments/"
                )
            )
        }

        var tournamentAdapter = TournamentCarouselAdapter(
            tournaments,
            parent.getTournamentBanners(),
            parent.getTournamentLogos()
        )
        val ongoingAdapter = ListAdapter(
            ongoing.map { it.logo },
            ongoing.map { it.name },
            ongoing.map { "${it.start_date} - ${it.end_date}" },
            "logo/tournaments/"
        )
        val upcomingAdapter = ListAdapter(
            upcoming.map { it.logo },
            upcoming.map { it.name },
            upcoming.map { "${it.start_date} - ${it.end_date}" },
            "logo/tournaments/"
        )

        ongoingAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                Log.d("Clicked", data)
            }

            override fun delData(pos: Int) {
                // do nothing
            }
        })

        rvTournamentCarousel.adapter = tournamentAdapter
        rvOngoing.adapter = ongoingAdapter
        rvUpcoming.adapter = upcomingAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExploreFr.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreFr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}