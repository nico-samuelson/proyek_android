package proyek.andro.userActivity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.helper.StorageHelper
import proyek.andro.model.News
import java.io.Serializable
import kotlin.math.log

class NewsDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        val titleNews = intent.getStringExtra("title")
        val authorNews = intent.getStringExtra("author")
        val dateNews = intent.getStringExtra("date")
        val contentNews = intent.getStringExtra("content")
        val imageNews = intent.getStringExtra("image")
        var imageURI : Uri? = null
        val storageRef = FirebaseStorage.getInstance().reference
        val imageV = findViewById<ImageView>(R.id.imageNews)
        Log.d("imageNews",imageNews!!)
        CoroutineScope(Dispatchers.Main).launch {
            storageRef.child("photos/news/${imageNews!!}")
                .downloadUrl
                .addOnSuccessListener {
                    Picasso.get()
                        .load(it)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imageV, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {}

                            override fun onError(e: Exception?) {
                                Picasso.get()
                                    .load(it)
                                    .into(imageV)
                            }
                        })
                }
        }
        val titleTextView = findViewById<TextView>(R.id.News_title)
        titleTextView.text = titleNews
        val descriptionTextView = findViewById<TextView>(R.id.News_content)
        descriptionTextView.text = contentNews
        val authorTextView = findViewById<TextView>(R.id.News_author)
        authorTextView.text = authorNews
        val dateTextView = findViewById<TextView>(R.id.News_date)
        dateTextView.text = dateNews


    }
}