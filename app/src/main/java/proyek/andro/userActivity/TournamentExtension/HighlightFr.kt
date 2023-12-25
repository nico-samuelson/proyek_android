package proyek.andro.userActivity.TournamentExtension

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import proyek.andro.R
import proyek.andro.model.Highlights
import proyek.andro.model.Tournament
import proyek.andro.userActivity.TournamentPage


/**
 * A simple [Fragment] subclass.
 * Use the [HighlightFr.newInstance] factory method to
 * create an instance of this fragment.
 */
class HighlightFr : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var parent: TournamentPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_highlight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parent = super.requireActivity() as TournamentPage
        var Tour = parent.getTournament()
        var highlight = parent.getHighlights()
        val thumbnail = view.findViewById<TextView>(R.id.highlightThumbnail)
        val title = view.findViewById<TextView>(R.id.highlightTitle)
        val webView = view.findViewById<WebView>(R.id.webView)
        Log.d("HighLight", highlight.filter { it.tournament == Tour?.id }.first().link)
        var video: String = "<iframe width=\"100%\" height=\"100%\" src=\""+ highlight.filter { it.tournament == Tour?.id }.first().link +"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen style=\"margin: 0; padding: 0;\"></iframe>"
        webView.loadData(video,"text/html", "utf-8")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        thumbnail.setText(highlight.filter { it.tournament == Tour?.id }.first().thumbnail)
        title.setText(highlight.filter { it.tournament == Tour?.id }.first().title)

    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment HighlightFr.
//         */
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            HighlightFr().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}