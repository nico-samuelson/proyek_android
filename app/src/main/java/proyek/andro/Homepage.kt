package proyek.andro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.model.Game
import proyek.andro.model.Tournament
import proyek.andro.seeder.GameSeeder
import proyek.andro.seeder.TournamentSeeder
import java.time.LocalDate
import java.util.Date

class Homepage : AppCompatActivity() {
    private var tournaments = ArrayList<Tournament>()
    private var games = ArrayList<Game>()
    private lateinit var rvCarousel : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val adapter = TournamentCarouselAdapter(this, tournaments)

        rvCarousel = findViewById(R.id.carousel_recycler_view)

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(rvCarousel)

        rvCarousel.layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
        rvCarousel.adapter = adapter
    }
}