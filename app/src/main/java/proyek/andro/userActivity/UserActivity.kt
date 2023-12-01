package proyek.andro.userActivity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.TournamentCarouselAdapter
import proyek.andro.model.Tournament

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val mFragmentManager = supportFragmentManager
        val home = UserHomepageFr()

        // show default fragment
        mFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, home, home::class.java.simpleName)
            .commit()

        // navbar styling
        val navbar : NavigationBarView = findViewById(R.id.bottom_navigation)
        navbar.itemIconTintList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(0)
            ),
            intArrayOf(
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.disabled)
            )
        )
        navbar.isItemActiveIndicatorEnabled = false
        navbar.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED

        // navbar on click listener
        navbar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    val home = UserHomepageFr()

                    mFragmentManager.beginTransaction().replace(R.id.fragmentContainer, home, home::class.java.simpleName)
                        .commit()
                    true
                }
                R.id.item_2 -> {
                    val explore = ExploreFr()

                    mFragmentManager.beginTransaction().replace(R.id.fragmentContainer, explore, explore::class.java.simpleName)
                        .commit()
                    true
                }
                R.id.item_3 -> {
                    val news = NewsFr()

                    mFragmentManager.beginTransaction().replace(R.id.fragmentContainer, news, news::class.java.simpleName)
                        .commit()
                    true
                }
                R.id.item_4 -> {
                    val profile = ProfileFr()

                    mFragmentManager.beginTransaction().replace(R.id.fragmentContainer, profile, profile::class.java.simpleName)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}