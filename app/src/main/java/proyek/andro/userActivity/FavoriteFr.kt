package proyek.andro.userActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Job
import proyek.andro.R
import proyek.andro.adapter.FavoriteAdapter
import proyek.andro.model.UserFavorite

class FavoriteFr : Fragment() {
    lateinit var rvFavorite: RecyclerView
    lateinit var parent: UserActivity

//    var favorites = ArrayList<Uri>()

    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parent = super.requireActivity() as UserActivity

        if (parent.getMatches().size == 0 || arguments?.getString("refresh") != null)
            job = parent.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addFavBtn : FloatingActionButton = view.findViewById(R.id.AddFavoriteBtn)


        parent.getNavbar().menu.getItem(3).isChecked = true

        rvFavorite = view.findViewById(R.id.favorite_grid_layout)
        rvFavorite.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//        rvFavorite.adapter = FavoriteAdapter(parent.getFavorites(), parent.getGames())

        val loadIndicator: CircularProgressIndicator = view.findViewById(R.id.loading_indicator)
        loadIndicator.visibility = View.GONE

        val mFragmentManager = parentFragmentManager
        val explore = ExploreFr()
        val addFavFr = AddFavoriteFr()

        val rvFavoriteAdapter = FavoriteAdapter(parent.getFavorites(), parent.getGames())
        rvFavoriteAdapter.setOnItemClickCallback(object :
            FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserFavorite) {
                parent.setSelectedGame(parent.getGames().indexOfFirst { it.id == data.game })

                mFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, explore, ExploreFr::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
            }
        })
        addFavBtn.setOnClickListener {
            mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addFavFr, AddFavoriteFr::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }

        rvFavorite.adapter = rvFavoriteAdapter
//        val favoriteGrid: GridLayout = view.findViewById(R.id.favorite_grid_layout)
//
//                val favorites = parent.getFavorites()
//                val storageRef = FirebaseStorage.getInstance().reference
//
//                val params = LinearLayout.LayoutParams(300, 450)
//                params.setMargins(0, 0, 32, 32)
//
//                favorites.forEach {
//                    val imageView = ImageView(parent).apply {
//                        scaleType = ImageView.ScaleType.CENTER_CROP
//                        layoutParams = params
//                    }
//
//                    imageView.setOnClickListener {
//
//                    }
//
//                    CoroutineScope(Dispatchers.Main).launch {
////                Log.d("banner", Game().find<Game>(it.game).banner)
//                        storageRef.child("banner/games/" + Game().find<Game>(it.game).banner)
//                            .downloadUrl
//                            .addOnSuccessListener { uri ->
//                                Picasso.get()
//                                    .load(uri)
//                                    .placeholder(R.drawable.card_placeholder)
//                                    .networkPolicy(NetworkPolicy.OFFLINE)
//                                    .into(imageView, object : Callback {
//                                        override fun onSuccess() {
//                                            Log.d("load image", "success")
//                                        }
//
//                                        override fun onError(e: Exception?) {
//                                            TODO("Not yet implemented")
//                                        }
//                                    })
//                            }
//                    }.invokeOnCompletion {
//                        favoriteGrid.addView(imageView)
//            }
//        }
//
//        rvFavorite = view.findViewById(R.id.favorite_recycler_view)
//        rvFavorite.layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy(), RecyclerView.VERTICAL)
//
//        CarouselSnapHelper().attachToRecyclerView(rvFavorite)
//
//        val adapter = FavoriteRVAdapter(parent.getFavorites())
//        adapter.itemCount
//        rvFavorite.adapter = adapter
    }
}