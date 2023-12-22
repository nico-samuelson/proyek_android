package proyek.andro.seeder

import proyek.andro.model.Match
import proyek.andro.model.Player
import proyek.andro.model.PlayerHistory
import proyek.andro.model.Team
import java.util.UUID

class PlayerHistorySeeder {
    val histories : ArrayList<PlayerHistory> = ArrayList()

    suspend fun run() {
        val teams : ArrayList<Team> = Team().get()
        val matches : ArrayList<Match> = Match().get()
        val players : ArrayList<Player> = Player().get()

        matches.forEach { match ->
            val team1 = teams.filter { it.id == match.team1 }.first()
            val team2 = teams.filter { it.id == match.team2 }.first()

            val player1 = players.filter { it.team == team1.id }
            val player2 = players.filter { it.team == team2.id }

            player1.forEach {player ->
                histories.add(PlayerHistory(UUID.randomUUID().toString(), player.id, match.id, team1.id))
            }

            player2.forEach {player ->
                histories.add(PlayerHistory(UUID.randomUUID().toString(), player.id, match.id, team2.id))
            }
        }

        histories.forEach {
            it.insertOrUpdate()
        }
    }
}