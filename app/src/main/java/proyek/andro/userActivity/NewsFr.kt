package proyek.andro.userActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.adapter.NewsAdapter
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.model.News
import proyek.andro.model.Tournament

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFr.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFr : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rvNews: RecyclerView
    private var news = ArrayList<News>()

    private lateinit var parent : UserActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        parent = super.requireActivity() as UserActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news = parent.getNews()
        rvNews = view.findViewById(R.id.rvNews)
        rvNews.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var newsAdapter = NewsAdapter(news)
        newsAdapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: News) {
                val intent = Intent(requireContext(),  NewsDetail::class.java)
                intent.putExtra("title", data.title)
                intent.putExtra("author", data.author)
                intent.putExtra("date", data.date)
                intent.putExtra("content", data.content)
                intent.putExtra("image", data.image)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                // do nothing
            }
        })
        rvNews.adapter = newsAdapter

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFr.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

