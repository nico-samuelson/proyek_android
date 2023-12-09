package proyek.andro.seeder

import proyek.andro.model.Game
import java.util.UUID

class GameSeeder() {
    var games = ArrayList<Game>()

    fun seed() : ArrayList<Game> {
        games.add(
            Game(
                UUID.randomUUID().toString(),
                "Valorant",
                "Riot Games first person shooter",
                "2020-06-02",
                "logo_valorant.png",
                "banner_valorant.jpg",
            )
        )

        games.add(
            Game(
                UUID.randomUUID().toString(),
                "Mobile Legends Bang Bang",
                "Moonton MOBA",
                "2016-07-14",
                "logo_mlbb.png",
                "banner_mlbb.jpg",
            )
        )

        games.add(
            Game(
                UUID.randomUUID().toString(),
                "League of Legends",
                "Riot Games MOBA",
                "2009-10-27",
                "logo_LoL.png",
                "banner_LoL.jpg",
            )
        )

        games.add(
            Game(
                UUID.randomUUID().toString(),
                "Dota 2",
                "Valve MOBA",
                "2013-07-09",
                "logo_dota2.png",
                "banner_dota2.jpg",
            )
        )

        games.add(
            Game(
                UUID.randomUUID().toString(),
                "Counter Strike 2",
                "Valve FPS",
                "2023-09-27",
                "logo_cs2.png",
                "banner_cs2.jpg",
            )
        )

        games.add(
            Game(
                UUID.randomUUID().toString(),
                "PUBG",
                "PUBG Corporation Battle Royale",
                "2017-12-20",
                "logo_pubg.png",
                "banner_pubg.jpg",
            )
        )

        games.add(
            Game(
                UUID.randomUUID().toString(),
                "Fortnite",
                "Epic Games Battle Royale",
                "2017-07-25",
                "logo_fortnite.png",
                "banner_fortnite.jpg",
            )
        )

        return games
    }

    suspend fun run() {
        seed()
        games.forEach {
            it.insertOrUpdate()
        }
    }
}