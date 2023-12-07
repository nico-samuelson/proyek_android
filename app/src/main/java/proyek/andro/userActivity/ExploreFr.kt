package proyek.andro.userActivity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.GameCarouselAdapter
import proyek.andro.adapter.MatchCarouselAdapter
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.helper.StorageHelper

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
    private lateinit var parent : UserActivity
    private lateinit var tournamentBanners: ArrayList<Uri>
    private lateinit var tournamentLogos: ArrayList<Uri>
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        parent = super.requireActivity() as UserActivity

        if (parent.getTournaments().size == 0 || arguments?.getString("refresh") != null)
            job = parent.getExploreData()
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

        var chips = view.findViewById<ChipGroup>(R.id.gameChips)

        parent.getGames().forEachIndexed { index, game ->
            val chip = Chip(context)
            chip.chipBackgroundColor = resources.getColorStateList(R.color.background, null)
            chip.setTextColor(resources.getColor(R.color.white, null))
            chip.chipStrokeColor = resources.getColorStateList(R.color.primary, null)
            chip.minHeight = 56
            chip.chipCornerRadius = 50f
            chip.text = game.name
            chip.isCheckable = true
            if (index == 0) chip.isChecked = true

            chips.addView(chip)
        }

        rvTournamentCarousel = view.findViewById(R.id.tournaments_recycler_view)
        rvTournamentCarousel.layoutManager = CarouselLayoutManager()
        CarouselSnapHelper().attachToRecyclerView(rvTournamentCarousel)

        if (job == null && parent.getTournaments().size > 0) {
            Log.d("fetch data", "data already fetched")
            CoroutineScope(Dispatchers.Main).launch {
                showData()
            }
        } else {
            Log.d("fetch data", "data not yet fetched")
            job?.invokeOnCompletion {
                CoroutineScope(Dispatchers.Main).launch {
                    showData()
                    job = null
                }
            }
        }
    }

    suspend fun showData() {
        val helper = StorageHelper()

        if (parent.getTournamentBanners().size == 0 || arguments?.getString("refresh") != null) {
            parent.setTournamentBanners(helper.preloadImages(parent.getTournaments().map { it.banner }, "banner/tournaments/"))
            parent.setTournamentLogos(helper.preloadImages(parent.getTournaments().map { it.logo }, "logo/tournaments/"))

            rvTournamentCarousel.adapter = TournamentCarouselAdapter(
                parent.getTournaments(),
                parent.getTournamentBanners(),
                parent.getTournamentLogos()
            )
            rvTournamentCarousel.recycledViewPool.setMaxRecycledViews(2, 0)
        }
        else {
            rvTournamentCarousel.adapter = TournamentCarouselAdapter(
                parent.getTournaments(),
                parent.getTournamentBanners(),
                parent.getTournamentLogos()
            )
            rvTournamentCarousel.recycledViewPool.setMaxRecycledViews(2, 0)
        }

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