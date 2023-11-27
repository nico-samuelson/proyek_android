package proyek.andro.seeder

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import proyek.andro.model.Game

class GameSeeder() {
    var games = ArrayList<Game>()
    var gameModel = Game()

    fun seed() : ArrayList<Game> {
        games.add(Game(
            "1",
            "Valorant",
            "Riot Games first person shooter",
            "2020-06-02",
            "logo_valorant"
        ))

        games.add(Game(
            "2",
            "Mobile Legends Bang Bang",
            "Moonton MOBA",
            "2016-07-14",
            "logo_mlbb"
        ))

        games.add(Game(
            "3",
            "League of Legends",
            "Riot Games MOBA",
            "2009-10-27",
            "logo_LoL"
        ))

        games.add(Game(
            "4",
            "Dota 2",
            "Valve MOBA",
            "2013-07-09",
            "logo_dota2"
        ))

        games.add(Game(
            "5",
            "Counter Strike 2",
            "Valve FPS",
            "2023-09-27",
            "logo_cs2"
        ))

        games.add(Game(
            "6",
            "PUBG",
            "PUBG Corporation Battle Royale",
            "2017-12-20",
            "logo_pubg"
        ))

        games.add(Game(
            "7",
            "Fortnite",
            "Epic Games Battle Royale",
            "2017-07-25",
            "logo_fortnite"
        ))

        return games
    }

    suspend fun run() {
        seed()
        games.forEach {
            it.insertOrUpdate()
        }
    }
}