package proyek.andro.userActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.FavoriteAdapter
import proyek.andro.adapter.ManageFavoriteAdapter
import proyek.andro.adapter.NewsAdapter
import proyek.andro.model.Game
import proyek.andro.model.News
import proyek.andro.model.UserFavorite
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFavoriteFr.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFavoriteFr : Fragment() {
    lateinit var rvAddFavorite: RecyclerView
    lateinit var parent: UserActivity
    private var games = ArrayList<Game>()
    private var favorites = ArrayList<UserFavorite>()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parent = super.requireActivity() as UserActivity
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
        return inflater.inflate(R.layout.fragment_add_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        games = parent.getGames()
        favorites = parent.getFavorites()
        rvAddFavorite = view.findViewById(R.id.rvManageFav)
        rvAddFavorite.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        var gamesAdapter = ManageFavoriteAdapter(games,favorites)
        val backBtn: ImageView = view.findViewById(R.id.backBtn)

        backBtn.setOnClickListener {
            parent.onBackPressedDispatcher.onBackPressed()
        }
        gamesAdapter.setOnItemClickCallback(object : ManageFavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Game, holder : ManageFavoriteAdapter.ListViewHolder) {
                if (data.id !in favorites.map { it.game }) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val newFavorite = UserFavorite(
                            UUID.randomUUID().toString(),
                            parent.getUser()!!.id,
                            data.id
                        )
                        newFavorite.insertOrUpdate()
//                        Log.d("favorite", newFavorite.user)
                        favorites.add(newFavorite)
                        gamesAdapter = ManageFavoriteAdapter(games,favorites)
                        parent.setFavorites(favorites)
                        holder.manage.setImageResource(R.drawable.ic_delete_24)
                    }
                } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            val delFav : UserFavorite? = favorites.filter { it.game == data.id }.first()
                            delFav!!.delete(favorites.filter { it.game == data.id }.first().id)
                            favorites = favorites.filter { it.game != data.id } as ArrayList<UserFavorite>
                            parent.setFavorites(favorites)

//                            gamesAdapter.notifyDataSetChanged()
                            holder.manage.setImageResource(R.drawable.add)
                        }
                }
            }

            override fun delData(pos: Int) {


            }
        })
        rvAddFavorite.adapter = gamesAdapter
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFavoriteFr.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFavoriteFr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}