package proyek.andro.seeder

import proyek.andro.seeder.GameSeeder
import android.util.Log
import proyek.andro.model.Game
import proyek.andro.model.Tournament
import java.time.LocalDate

class TournamentSeeder() {
    val tournaments = ArrayList<Tournament>()
    val game = GameSeeder().seed()
    val tournament = Tournament()

    fun run() {
        tournaments.add(Tournament(
            "1",
            game.find { it.id === "1" } as Game,
            "Valorant Champions 2023",
            "2023-08-06",
            "2023-08-26",
            2250000,
            "Riot Games",
            "Offline",
            listOf("Los Angeles"),
            listOf("Kia Forum"),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl. Sed euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl.",
            "logo_valorant_champions_23",
            "banner_valorant_champions_23",
            2,
        ))

        tournaments.add(Tournament(
            "2",
            game.find { it.id === "2" } as Game,
            "M5 World Championship 2023",
            "2023-11-23",
            "2023-12-17",
            900000,
            "Moonton",
            "Offline",
            listOf("Selangor", "Manila"),
            listOf("Jio Space", "EVM Convention Center", "Rizal Memorial Coliseum"),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl. Sed euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl.",
            "logo_m5",
            "banner_m5",
            1,
        ))

        tournaments.add(Tournament(
            "3",
            game.find { it.id === "3" } as Game,
            "Worlds 2023",
            "2023-10-10",
            "2023-11-19",
            2250000,
            "Riot Games",
            "Offline",
            listOf("Seoul", "Busan"),
            listOf("Gocheok Sky Dome", "Sajik Arena", "KBS Stadium", "LOL Park"),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl. Sed euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl.",
            "logo_worlds_23",
            "banner_worlds_23",
            2,
        ))

        tournaments.forEach{
            tournament.insert(it.convertToMap())
        }
    }
}