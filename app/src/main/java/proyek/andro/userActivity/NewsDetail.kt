package proyek.andro.userActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import proyek.andro.R
import proyek.andro.model.News
import java.io.Serializable

class NewsDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        val titleNews = intent.getStringExtra("title")
        val authorNews = intent.getStringExtra("author")
        val dateNews = intent.getStringExtra("date")
        val contentNews = intent.getStringExtra("content")
        val imageNews = intent.getStringExtra("image")


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