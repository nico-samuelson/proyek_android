package proyek.andro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.model.Game
import proyek.andro.model.Tournament

class Homepage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        var tournaments : ArrayList<Tournament> = ArrayList()
//        var games : ArrayList<Game> = ArrayList()
        var rvCarousel : RecyclerView
        var adapter : TournamentCarouselAdapter

        rvCarousel = findViewById(R.id.carousel_recycler_view)

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(rvCarousel)
        rvCarousel.layoutManager = CarouselLayoutManager(HeroCarouselStrategy())

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                tournaments = Tournament().get()

                adapter = TournamentCarouselAdapter(this@Homepage, tournaments)
                rvCarousel.adapter = adapter
            }
            catch (e : FirebaseFirestoreException) {
                tournaments = ArrayList()
                Log.e("Error", e.message.toString())

                adapter = TournamentCarouselAdapter(this@Homepage, tournaments)
                rvCarousel.adapter = adapter
            }
        }
    }
}