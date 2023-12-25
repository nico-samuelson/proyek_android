package proyek.andro.seeder

import proyek.andro.model.Game
import proyek.andro.model.Highlights
import proyek.andro.model.Tournament
import java.util.UUID

class HighLightSeeder {
    var listofHighlights = ArrayList<Highlights>()

    suspend fun run() {
        val listTournament : ArrayList<Tournament> = Tournament().get()
        listofHighlights.add(
            Highlights(
                UUID.randomUUID().toString(),
                listTournament.filter { it.name == "M5 World Championship 2023" }.first().id,
                "Onic Esport Last Struggle",
                "TOP 10 PLAYS FROM M5 WORLD CHAMPIONSHIP",
                "https://www.youtube.com/watch?v=Ki3LiiGvsK8&ab_channel=DJY"
            )
        )

        listofHighlights.add(
            Highlights(
                UUID.randomUUID().toString(),
                listTournament.filter { it.name == "Valorant Champions 2023" }.first().id,
                "Best Valorant Plays of VCT 2023",
                "The Best 15 Plays Of VALORANT Champions Tour 2023",
                "https://www.youtube.com/watch?v=zufMUTl-hu4&ab_channel=VALORANTChampionsTour"
            )
        )

        listofHighlights.add(
            Highlights(
                UUID.randomUUID().toString(),
                listTournament.filter { it.name == "Worlds 2023" }.first().id,
                "Highlight Finals",
                "WBG vs T1 | FULL DAY HIGHLIGHTS | The Finals | Worlds 2023",
                "https://www.youtube.com/watch?v=g3NcF5Uv-w4&ab_channel=LoLEsports"
            )
        )
        listofHighlights.forEach {
            it.insertOrUpdate()
        }
    }


}