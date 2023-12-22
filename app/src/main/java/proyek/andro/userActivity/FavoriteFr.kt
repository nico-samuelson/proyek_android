package proyek.andro.userActivity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.squareup.picasso.NetworkPolicy
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import proyek.andro.R

import proyek.andro.model.Game
class FavoriteFr : Fragment() {
    lateinit var rvFavorite: RecyclerView
    lateinit var parent: UserActivity
    var favorites = ArrayList<Uri>()

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

        Log.d("user_favorite", parent.getFavorites().toString())

        val favoriteGrid: GridLayout = view.findViewById(R.id.favorite_grid_layout)

        val favorites = parent.getFavorites()
        val storageRef = FirebaseStorage.getInstance().reference

        val params = LinearLayout.LayoutParams(300, 450)
        params.setMargins(0, 0, 32, 32)

        favorites.forEach {
            val imageView = ImageView(parent).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = params
            }

            imageView.setOnClickListener {

            }

            CoroutineScope(Dispatchers.Main).launch {
//                Log.d("banner", Game().find<Game>(it.game).banner)
                storageRef.child("banner/games/" + Game().find<Game>(it.game).banner)
                    .downloadUrl
                    .addOnSuccessListener { uri ->
                        Picasso.get()
                            .load(uri)
                            .placeholder(R.drawable.card_placeholder)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(imageView, object : Callback {
                                override fun onSuccess() {
                                    Log.d("load image", "success")
                                }

                                override fun onError(e: Exception?) {
                                    TODO("Not yet implemented")
                                }
                            })
                    }
            }.invokeOnCompletion {
                favoriteGrid.addView(imageView)
            }
        }

//        rvFavorite = view.findViewById(R.id.favorite_recycler_view)
//        rvFavorite.layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy(), RecyclerView.VERTICAL)
//
//        CarouselSnapHelper().attachToRecyclerView(rvFavorite)
//
//        val adapter = FavoriteRVAdapter(parent.getFavorites())
//        adapter.itemCount
//        rvFavorite.adapter = adapter
    }

    fun showData() {

    }
}