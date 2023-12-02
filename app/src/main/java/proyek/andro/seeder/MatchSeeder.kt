package proyek.andro.seeder

import proyek.andro.model.Game
import proyek.andro.model.Match
import proyek.andro.model.Tournament
import proyek.andro.model.TournamentPhase
import java.util.UUID

class MatchSeeder {
    val matches = ArrayList<Match>()

    suspend fun run() {
        matches.forEach {
            it.insertOrUpdate()
        }
    }
}