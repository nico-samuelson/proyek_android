package proyek.andro.seeder
import proyek.andro.model.News
import java.util.UUID
class NewsSeeder {
    var listofNews = ArrayList<News>()

    fun seed() : ArrayList<News> {
        listofNews.add(
            News(
                UUID.randomUUID().toString(),
                "Sentinels Era is Back !!",
                "Sentinels have shown it fangs by becoming the first place in AfreecaTV Valorant League. Winning against PRX by 3-0 score.",
                "Nico Samuelson",
                "2023-12-10",
            )
        )

        listofNews.add(
            News(
                UUID.randomUUID().toString(),
                "Valorant Night Market is back",
                "Valorant Night Market has become an event that been awaited by all valorant player. Check your valorant night market, and see what do you get!",
                "Kelvin Sids",
                "2023-12-15",
            )
        )

        listofNews.add(
            News(
                UUID.randomUUID().toString(),
                "M5 Winner",
                "AP.Bren has won Mobile Legend M-Series World Championship twice. That makes AP.Bren become the first team who has won Mobile Legend M-Series World Championship twice.",
                "Vincentius Actonio",
                "2023-12-18",
            )
        )



        return listofNews
    }

    suspend fun run() {
        seed()
        listofNews.forEach {
            it.insertOrUpdate()
        }
    }

}