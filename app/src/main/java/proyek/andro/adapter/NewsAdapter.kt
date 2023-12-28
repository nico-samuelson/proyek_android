package proyek.andro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import proyek.andro.R
import proyek.andro.model.News
class NewsAdapter (
    private val listNews: ArrayList<News>
) : RecyclerView.Adapter<NewsAdapter.ListViewHolder> () {
    private lateinit var onItemClickCallback: OnItemClickCallback
    val storageRef = FirebaseStorage.getInstance().reference
    interface OnItemClickCallback {
        fun onItemClicked(data : News)
        fun delData(pos: Int)
    }
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _newsTitle : TextView = itemView.findViewById(R.id.News_title)
        var _newsAuthor : TextView = itemView.findViewById(R.id.News_author)
        var _newsDate : TextView = itemView.findViewById(R.id.News_date)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_news, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ListViewHolder, position: Int) {
        val news = listNews[position]

        holder._newsTitle.setText(news.title)
        holder._newsAuthor.setText(news.author)
        holder._newsDate.setText(news.date)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(news) }
    }

    override fun getItemCount(): Int {
        return listNews.size
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }




}