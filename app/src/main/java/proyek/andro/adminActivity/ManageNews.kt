package proyek.andro.adminActivity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
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
import proyek.andro.model.Player
import proyek.andro.model.Team

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
        var images : ArrayList<String> = ArrayList()
        var names : ArrayList<String> = ArrayList()

        backBtn.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

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

                rvNews.layoutManager = LinearLayoutManager(
                    this@ManageNews,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                val adapterP = SimpleListAdapter(images, names, "")

                adapterP.setOnItemClickCallback(object : SimpleListAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: String) {
                        val intent = Intent(this@ManageNews, AddNews::class.java)
                        intent.putExtra("mode", "edit")
                        intent.putExtra("name", data)
                        startActivity(intent)
                    }

                    override fun delData(pos: Int) {
                        val selectedNews = news.get(pos)

                        val alert = MaterialAlertDialogBuilder(this@ManageNews)
                            .setTitle("Delete Player")
                            .setMessage("Are you sure you want to delete ${selectedNews.title}?")
                            .setNegativeButton("Cancel") { dialog, which ->
                                dialog.dismiss()
                            }
                            .setPositiveButton("Delete") { dialog, which ->
                                CoroutineScope(Dispatchers.Main).launch {
                                    StorageHelper().deleteFile(selectedNews.image)
                                    selectedNews.delete(selectedNews.id)

                                    news.removeAt(pos)
                                    images.removeAt(pos)
                                    names.removeAt(pos)
                                    adapterP.setData(images, names)

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
                rvNews.adapter = adapterP
            }
        }
    }
}