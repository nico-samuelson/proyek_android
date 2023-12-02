package proyek.andro.userActivity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import com.google.firebase.firestore.Filter
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.GameCarouselAdapter
import proyek.andro.adapter.MatchCarouselAdapter
import proyek.andro.adapter.TournamentCarouselAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tournaments : ArrayList<Tournament>
        var games : ArrayList<Game>
        var teams : ArrayList<Team>
        var matches : ArrayList<Match>

        var rvTournamentCarousel : RecyclerView
        var rvGameCarousel : RecyclerView
        var rvMatchCarousel : RecyclerView

        var tournamentAdapter : TournamentCarouselAdapter
        var gameAdapter : GameCarouselAdapter
        var matchAdapter : MatchCarouselAdapter

        rvTournamentCarousel = view.findViewById(R.id.carousel_recycler_view)
        rvGameCarousel = view.findViewById(R.id.games_recycler_view)
        rvMatchCarousel = view.findViewById(R.id.matches_recycler_view)

        CarouselSnapHelper().attachToRecyclerView(rvTournamentCarousel)
        CarouselSnapHelper().attachToRecyclerView(rvMatchCarousel)

        rvTournamentCarousel.layoutManager = CarouselLayoutManager()
        rvGameCarousel.layoutManager = CarouselLayoutManager(MultiBrowseCarouselStrategy())
        rvMatchCarousel.layoutManager = CarouselLayoutManager()

        var gameBanners : ArrayList<RequestCreator>
        var tournamentBanners : ArrayList<RequestCreator>
        var tournamentLogos : ArrayList<RequestCreator>
        var matchTeamLogos : ArrayList<RequestCreator>

        CoroutineScope(Dispatchers.Main).launch {
            // get data
            tournaments = Tournament().get(
                filter= Filter.lessThan("status", 3),
                limit=5,
                order=arrayOf(arrayOf("status", "asc"), arrayOf("start_date", "desc"))
            )
            games = Game().get()
            teams = Team().get(limit=100)
            matches = Match().get(order=arrayOf(arrayOf("time", "desc")))
            Log.d("matches", matches.toString())

            // preload all images
            tournamentBanners = preloadImages(tournaments.map{ it.banner }, "banner/tournaments")
            tournamentLogos = preloadImages(tournaments.map{ it.logo }, "logo/tournaments")
            gameBanners = preloadImages(games.map{ it.banner }, "banner/games")

            matches

            // set adapter
            rvTournamentCarousel.adapter = TournamentCarouselAdapter(tournaments, tournamentBanners, tournamentLogos)
            rvGameCarousel.adapter = GameCarouselAdapter(games, gameBanners)
            rvMatchCarousel.adapter = MatchCarouselAdapter(matches, teams)
        }
    }

    suspend fun preloadImages(images : List<String>, directory : String) : ArrayList<RequestCreator> {
        val gameBanners = ArrayList<RequestCreator>()
        val storageRef = FirebaseStorage.getInstance().reference

        images.forEach { image ->
            suspendCoroutine { continuation ->
                storageRef.child("${directory}/${image}")
                    .downloadUrl
                    .addOnSuccessListener { uri ->
                        gameBanners.add(
                            Picasso.get()
                                .load(uri)
                                .placeholder(R.drawable.card_placeholder)
                                .networkPolicy(NetworkPolicy.OFFLINE)
                        )
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener {
                        Log.e("preload image", it.message.toString())
                        continuation.resume(Unit)
                    }
            }
        }

        return gameBanners
    }

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