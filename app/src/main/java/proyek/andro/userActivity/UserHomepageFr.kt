package proyek.andro.userActivity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.CarouselStrategy
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import proyek.andro.R
import proyek.andro.adapter.GameCarouselAdapter
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.model.Game
import proyek.andro.model.Tournament

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

        val storageRef = FirebaseStorage.getInstance().reference

        var tournaments: ArrayList<Tournament>
        var games: ArrayList<Game>

        var rvTournamentCarousel: RecyclerView
        var rvGameCarousel: RecyclerView

        var tournamentAdapter: TournamentCarouselAdapter
        var gameAdapter: GameCarouselAdapter

        rvTournamentCarousel = view.findViewById(R.id.carousel_recycler_view)
        rvGameCarousel = view.findViewById(R.id.games_recycler_view)

        CarouselSnapHelper().attachToRecyclerView(rvTournamentCarousel)

        rvTournamentCarousel.layoutManager = CarouselLayoutManager()
        rvGameCarousel.layoutManager = CarouselLayoutManager(MultiBrowseCarouselStrategy())

        lifecycleScope.launch(Dispatchers.Main) {
            tournaments = Tournament().get()
            games = Game().get()

            var gameBanners = ArrayList<Uri>()

            tournamentAdapter = TournamentCarouselAdapter(tournaments)
            rvTournamentCarousel.adapter = tournamentAdapter

            gameAdapter = GameCarouselAdapter(games, gameBanners)
            rvGameCarousel.adapter = gameAdapter
        }
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