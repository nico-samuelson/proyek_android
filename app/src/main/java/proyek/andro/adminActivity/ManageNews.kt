package proyek.andro.adminActivity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.SimpleListAdapter
import proyek.andro.helper.StorageHelper
import proyek.andro.model.News

class ManageNews : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_news)

        val backBtn : ImageView = findViewById(R.id.backBtn)
        val addNewsBtn : FloatingActionButton = findViewById(R.id.addNewsBtn)
        val loadingView : LinearLayout = findViewById(R.id.loading_view)
        val emptyView : LinearLayout = findViewById(R.id.empty_view)
        val rvNews : RecyclerView = findViewById(R.id.viewNews)

        var news : ArrayList<News> = ArrayList()
        val images : ArrayList<String> = ArrayList()
        val names : ArrayList<String> = ArrayList()

        var filteredNews : ArrayList<News> = ArrayList()
        var filteredImages : ArrayList<String> = ArrayList()
        var filteredNames : ArrayList<String> = ArrayList()

        lateinit var adapterN : SimpleListAdapter

        val search_view : SearchView = findViewById(R.id.search_view)
        val etSearch = search_view.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        etSearch.setHintTextColor(resources.getColor(R.color.disabled, null))
        etSearch.setTextColor(resources.getColor(R.color.white, null))

        backBtn.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        // fab click listener
        addNewsBtn.setOnClickListener {
            val intent = Intent(this, AddNews::class.java)
            intent.putExtra("mode", "add")
            startActivity(intent)
        }

        // fetch and show data
        CoroutineScope(Dispatchers.Main).launch {
            news = News().get(limit=1000, order = arrayOf(arrayOf("date", "desc"), arrayOf("title", "ASC")))

            if (news.size == 0) {
                loadingView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }
            else {
                news.forEach {
                    images.add(it.image)
                    names.add(it.title)
                }

                filteredNews.addAll(news)
                filteredImages.addAll(images)
                filteredNames.addAll(names)

                rvNews.layoutManager = LinearLayoutManager(
                    this@ManageNews,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapterN = SimpleListAdapter(filteredImages, filteredNames, "")

                adapterN.setOnItemClickCallback(object : SimpleListAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: String) {
                        val intent = Intent(this@ManageNews, AddNews::class.java)
                        intent.putExtra("mode", "edit")
                        intent.putExtra("name", data)
                        startActivity(intent)
                    }

                    override fun delData(pos: Int) {
                        val selectedNews = filteredNews.get(pos)

                        val alert = MaterialAlertDialogBuilder(this@ManageNews)
                            .setTitle("Delete News")
                            .setMessage("Are you sure you want to delete ${selectedNews.title}?")
                            .setNegativeButton("Cancel") { dialog, which ->
                                dialog.dismiss()
                            }
                            .setPositiveButton("Delete") { dialog, which ->
                                CoroutineScope(Dispatchers.Main).launch {
                                    StorageHelper().deleteFile(selectedNews.image)
                                    selectedNews.delete(selectedNews.id)


                                    images.remove(selectedNews.image)
                                    names.remove(selectedNews.title)
                                    news.remove(selectedNews)
                                    filteredImages.removeAt(pos)
                                    filteredNames.removeAt(pos)
                                    filteredNews.removeAt(pos)

                                    adapterN.setData(images, names)

                                    Snackbar.make(
                                        addNewsBtn,
                                        R.string.newsdeleted,
                                        Snackbar.LENGTH_SHORT
                                    ).apply {
                                        setBackgroundTint(resources.getColor(R.color.light, null))
                                        setTextColor(resources.getColor(R.color.black, null))
                                    }.show()
                                }
                            }

                        val dialog = alert.create()
                        dialog.show()
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                    }
                })

                loadingView.visibility = View.GONE
                rvNews.visibility = View.VISIBLE
                rvNews.adapter = adapterN
            }
        }

        // search players
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // reset rv states
                filteredNews.clear()
                filteredNames.clear()
                filteredImages.clear()

                CoroutineScope(Dispatchers.Main).launch {
                    filteredNews = news.filter {
                        it.title.lowercase().contains(query.toString().lowercase())
                    } as ArrayList<News>
                    filteredImages = filteredNews.map { it.image } as ArrayList<String>
                    filteredNames = filteredNews.map { it.title } as ArrayList<String>

                    adapterN.setData(filteredImages, filteredNames)
                }

                // clear on screen keyboard to prevent this method from being called twice
                search_view.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do something when text changes
                if (newText.toString() == "") {
                    filteredNews.clear()
                    filteredNames.clear()
                    filteredImages.clear()

                    filteredNews.addAll(news)
                    filteredImages.addAll(images)
                    filteredNames.addAll(names)

                    adapterN.setData(filteredImages, filteredNames)
                }
                return true
            }
        })
    }
}