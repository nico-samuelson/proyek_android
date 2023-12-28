package proyek.andro.userActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.UncontainedCarouselStrategy
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.search.SearchBar
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.ListAdapter
import proyek.andro.adapter.MatchCarouselAdapter
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Match
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

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
    private lateinit var rvOngoingTournaments : RecyclerView
    private lateinit var rvUpcomingTournaments : RecyclerView
    private lateinit var rvOngoingMatches : RecyclerView

    private var tournaments = ArrayList<Tournament>()
    private var ongoingTournaments = ArrayList<Tournament>()
    private var upcomingTournaments = ArrayList<Tournament>()
    private var ongoingMatches = ArrayList<Match>()
    private var matchesTeams = ArrayList<Team>()
    private lateinit var ongoingGroups : LinearLayout
    private lateinit var upcomingGroups : LinearLayout
    private lateinit var parent : UserActivity
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

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parent.getNavbar().menu.getItem(1).isChecked = true
//        _ivFavorite.setOnClickListener {
//            if (!statusFavorite) {
//                _ivFavorite.setImageResource(R.drawable.ic_favorite_32)
//                statusFavorite = true
//                var user = parent.getUser()
//                val selectedGames = parent.getGames().get(parent.getSelectedGame()).id
//                val newFav = UserFavorite(
//                    UUID.randomUUID().toString(),
//                    user!!.id,
//                    selectedGames
//                )
//                CoroutineScope(Dispatchers.Main).launch {
//                    newFav.insertOrUpdate()
//                }
//            } else {
//                _ivFavorite.setImageResource(R.drawable.love)
//                statusFavorite = false
//
//            }
//
//        }
        val search_bar = view.findViewById<SearchBar>(R.id.search_bar)
        search_bar.setOnClickListener {
            val intent = Intent(parent, Search::class.java)
            intent.putExtra("search_type", 0)
            startActivity(intent)
        }

        ongoingGroups = view.findViewById(R.id.ongoing_layout_groups)
        upcomingGroups = view.findViewById(R.id.upcoming_layout_groups)
        ongoingGroups.visibility = View.GONE
        upcomingGroups.visibility = View.GONE

        rvTournamentCarousel = view.findViewById(R.id.tournaments_recycler_view)
        rvOngoingTournaments = view.findViewById(R.id.ongoing_tournaments)
        rvUpcomingTournaments = view.findViewById(R.id.upcoming_tournaments)
        rvOngoingMatches = view.findViewById(R.id.rv_ongoing_match)

        rvTournamentCarousel.layoutManager = CarouselLayoutManager(UncontainedCarouselStrategy())
        rvOngoingTournaments.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvOngoingMatches.layoutManager = CarouselLayoutManager(UncontainedCarouselStrategy())

        CarouselSnapHelper().attachToRecyclerView(rvOngoingMatches)
        CarouselSnapHelper().attachToRecyclerView(rvTournamentCarousel)

        if (job == null && parent.getTournaments().size > 0) {
            tournaments = parent.getTournaments().filter { it.game == parent.getGames().get(parent.getSelectedGame()).id } as ArrayList<Tournament>
            tournaments = tournaments.sortedByDescending { it.start_date }.toMutableList() as ArrayList<Tournament>

            ongoingTournaments = tournaments.filter { it.status == 1L } as ArrayList<Tournament>
            upcomingTournaments = tournaments.filter { it.status == 0L } as ArrayList<Tournament>

            makeChips(view)
            CoroutineScope(Dispatchers.Main).launch {
                if (ongoingTournaments.size > 0) {
                    Log.d("ongoing", ongoingTournaments.size.toString())
                    ongoingMatches = Match().get(
                        filter = Filter.and(
                            Filter.inArray("tournament", ongoingTournaments.map { it.id }),
                            Filter.equalTo("status", 1L),
                        ),
                        limit = 5,
                        order = arrayOf(arrayOf("time", "desc"))
                    )

                    matchesTeams = parent.getTeams().filter { team ->
                        ongoingMatches.map { it.team1 }.contains(team.id) || ongoingMatches.map { it.team2 }.contains(team.id)
                    } as ArrayList<Team>
                }

                showData(view)
            }
        }
        else if (job != null) {
            job?.invokeOnCompletion {
                tournaments = parent.getTournaments().filter { it.game == parent.getGames().get(parent.getSelectedGame()).id } as ArrayList<Tournament>
                tournaments = tournaments.sortedByDescending { it.start_date }.toMutableList() as ArrayList<Tournament>

                ongoingTournaments = tournaments.filter { it.status == 1L } as ArrayList<Tournament>
                upcomingTournaments = tournaments.filter { it.status == 0L } as ArrayList<Tournament>

                makeChips(view)
                CoroutineScope(Dispatchers.Main).launch {
                    if (ongoingTournaments.size > 0) {
                        Log.d("ongoing", ongoingTournaments.size.toString())
                        ongoingMatches = Match().get(
                            filter = Filter.and(
                                Filter.inArray("tournament", ongoingTournaments.map { it.id }),
                                Filter.equalTo("status", 1L),
                            ),
                            limit = 5,
                            order = arrayOf(arrayOf("time", "desc"))
                        )

                        matchesTeams = parent.getTeams().filter { team ->
                            ongoingMatches.map { it.team1 }.contains(team.id) || ongoingMatches.map { it.team2 }.contains(team.id)
                        } as ArrayList<Team>
                    }

                    showData(view)
                    job = null
                }
            }
        }
    }

    fun makeChips(view : View) {
        val chips = view.findViewById<ChipGroup>(R.id.gameChips)
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

            if (index == parent.getSelectedGame()) chip.isChecked = true
            if (chip.isChecked) chip.chipBackgroundColor = colorState
            else chip.chipBackgroundColor = colorState

            chip.setOnClickListener {
                rvTournamentCarousel.visibility = View.GONE
                ongoingGroups.visibility = View.GONE
                upcomingGroups.visibility = View.GONE
                view.findViewById<CircularProgressIndicator>(R.id.loading_indicator).visibility = View.VISIBLE

                CoroutineScope(Dispatchers.Main).launch {
                    parent.setTournamentBanners(ArrayList())
                    parent.setTournamentLogos(ArrayList())
                    parent.setSelectedGame(chip.id)

                    tournaments = parent.getTournaments().filter { it.game == parent.getGames().get(chip.id).id } as ArrayList<Tournament>
                    tournaments = tournaments.sortedByDescending { it.start_date }.toMutableList() as ArrayList<Tournament>
                    ongoingTournaments = tournaments.filter { it.status == 1L } as ArrayList<Tournament>
                    upcomingTournaments = tournaments.filter { it.status == 0L } as ArrayList<Tournament>

                    CoroutineScope(Dispatchers.Main).launch {
                        if (ongoingTournaments.size > 0) {
                            ongoingMatches = Match().get(
                                filter = Filter.and(
                                    Filter.inArray("tournament", ongoingTournaments.map { it.id }),
                                    Filter.equalTo("status", 1L),
                                ),
                                limit = 5,
                                order = arrayOf(arrayOf("time", "desc"))
                            )

                            matchesTeams = parent.getTeams().filter { team ->
                                ongoingMatches.map { it.team1 }.contains(team.id) || ongoingMatches.map { it.team2 }.contains(team.id)
                            } as ArrayList<Team>
                        }

                        showData(view)
                    }
                }
            }

            chips.addView(chip)
        }
    }

    suspend fun showData(view : View) {
        val helper = StorageHelper()

        Log.d("ongoing tourney", ongoingTournaments.size.toString())
        Log.d("upcoming tourney", upcomingTournaments.size.toString())

        // check tournament size to determine render output
        if (tournaments.size == 0) {
            view.findViewById<LinearLayout>(R.id.no_tournaments_layout).visibility = View.VISIBLE
            view.findViewById<CircularProgressIndicator>(R.id.loading_indicator).visibility = View.GONE
            return
        }

        view.findViewById<LinearLayout>(R.id.no_tournaments_layout).visibility = View.GONE
        rvTournamentCarousel.visibility = View.VISIBLE

        // preload images
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

        val tournamentAdapter = TournamentCarouselAdapter(
            tournaments,
            parent.getTournamentBanners(),
            parent.getTournamentLogos()
        )

        tournamentAdapter.setOnItemClickCallback(object : TournamentCarouselAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                val intent = Intent(requireContext(),  TournamentPage::class.java)
                intent.putExtra("tournament", data)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                // do nothing
            }
        })

        rvTournamentCarousel.adapter = tournamentAdapter

        // render ongoing section
        if (ongoingTournaments.size > 0) {
            val ongoingTournamentAdapter = ListAdapter(
                ongoingTournaments.map { it.logo },
                ongoingTournaments.map { it.name },
                ongoingTournaments.map {
                    val start_date = if (it.start_date != "") LocalDate.parse(it.start_date) else LocalDate.now()
                    val end_date = if (it.start_date != "") LocalDate.parse(it.end_date) else LocalDate.now()
                    val start = start_date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + start_date.dayOfMonth
                    val end = end_date.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + end_date.dayOfMonth

                    if (it.start_date != "") "${start} - ${end}, ${end_date.year}" else "TBA"
                },
                "logo/tournaments/"
            )
            val ongoingMatchesAdapter = MatchCarouselAdapter(ongoingMatches, matchesTeams)

            ongoingTournamentAdapter.setOnItemClickCallback(object :
                ListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: String) {
                    val intent = Intent(requireContext(),  TournamentPage::class.java)
                    intent.putExtra("tournament", data)
                    startActivity(intent)
                }

                override fun delData(pos: Int) {
                    // do nothing
                }
            })

            ongoingGroups.visibility = View.VISIBLE
            rvOngoingTournaments.adapter = ongoingTournamentAdapter
            rvOngoingMatches.adapter = ongoingMatchesAdapter
        }
        else {
            ongoingGroups.visibility = View.GONE
        }

        Log.d("upcomingTourney", upcomingTournaments.map { it.name }.toString())
        // render upcoming section
        if (upcomingTournaments.size > 0) {
            val upcomingAdapter = ListAdapter(
                upcomingTournaments.map { it.logo },
                upcomingTournaments.map { it.name },
                upcomingTournaments.map { "${it.start_date} - ${it.end_date}" },
                "logo/tournaments/"
            )

            upcomingGroups.visibility = View.VISIBLE
            rvUpcomingTournaments.adapter = upcomingAdapter
        }
        else {
            upcomingGroups.visibility = View.GONE
        }

        view.findViewById<CircularProgressIndicator>(R.id.loading_indicator).visibility = View.GONE
        rvTournamentCarousel.visibility = View.VISIBLE
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