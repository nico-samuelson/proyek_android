package proyek.andro.seeder

import proyek.andro.model.Game
import proyek.andro.model.Player
import proyek.andro.model.Team

class PlayerSeeder {
    val players : ArrayList<Player> = ArrayList()

    suspend fun run() {
        val games : ArrayList<Game> = Game().get()
        val teams : ArrayList<Team> = Team().get()

        players.forEach {
            it.insertOrUpdate()
        }
    }
}