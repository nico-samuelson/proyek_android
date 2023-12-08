package proyek.andro.userActivity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import com.google.android.material.carousel.UncontainedCarouselStrategy
import com.google.firebase.firestore.Filter
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.GameCarouselAdapter
import proyek.andro.adapter.MatchCarouselAdapter
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.Game
import proyek.andro.model.Match
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserHomepageFr.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserHomepageFr : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var rvGameCarousel: RecyclerView
    lateinit var rvMatchCarousel: RecyclerView
    lateinit var parent: UserActivity
    var gameBanners = ArrayList<Uri>()
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        parent = super.requireActivity() as UserActivity

        if (parent.getMatches().size == 0 || arguments?.getString("refresh") != null)
            job = parent.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_homepage, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvGameCarousel = view.findViewById(R.id.games_recycler_view)
        rvMatchCarousel = view.findViewById(R.id.matches_recycler_view)

        rvGameCarousel.layoutManager = CarouselLayoutManager(UncontainedCarouselStrategy())
        rvMatchCarousel.layoutManager = CarouselLayoutManager(UncontainedCarouselStrategy())
        CarouselSnapHelper().attachToRecyclerView(rvMatchCarousel)

        if (job == null && parent.getMatches().size > 0) {
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
        if (parent.getGameBanners().size == 0 || arguments?.getString("refresh") != null) {
            parent.setGameBanners(
                StorageHelper().preloadImages(
                    parent.getGames().map { it.banner },
                    "banner/games/"
                )
            )
        }

        val gameCarouselAdapter = GameCarouselAdapter(parent.getGames(), parent.getGameBanners())
        val mFragmentManager = parentFragmentManager
        val explore = ExploreFr()

        gameCarouselAdapter.setOnItemClickCallback(object :
            GameCarouselAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Game) {
                Log.d("game clicked", data.toString())
                parent.setSelectedGame(parent.getGames().indexOfFirst { it.id == data.id })
                mFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, explore, explore::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
            }
        })

        rvGameCarousel.adapter = gameCarouselAdapter
        rvMatchCarousel.adapter = MatchCarouselAdapter(parent.getMatches(), parent.getTeams())

        rvGameCarousel.recycledViewPool.setMaxRecycledViews(1, 0)
        rvMatchCarousel.recycledViewPool.setMaxRecycledViews(3, 0)
    }
//        else {
//            val gameCarouselAdapter = GameCarouselAdapter(parent.getGames(), parent.getGameBanners())
//            gameCarouselAdapter.setOnItemClickCallback(object : GameCarouselAdapter.OnItemClickCallback {
//                override fun onItemClicked(data: Game) {
//                    Log.d("game clicked", data.toString())
//                    parent.setSelectedGame(parent.getGames().indexOfFirst { it.id == data.id })
//                    mFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainer, explore, explore::class.java.simpleName)
//                        .addToBackStack(null)
//                        .commit()
//                }
//            })
//
//            rvGameCarousel.adapter = gameCarouselAdapter
//            rvMatchCarousel.adapter = MatchCarouselAdapter(parent.getMatches(), parent.getTeams())
//
//            rvGameCarousel.recycledViewPool.setMaxRecycledViews(1, 0)
//            rvMatchCarousel.recycledViewPool.setMaxRecycledViews(3, 0)
//        }
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserHomepageFr.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserHomepageFr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}