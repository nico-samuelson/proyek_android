package proyek.andro.seeder

import proyek.andro.model.Game
import proyek.andro.model.Tournament
import java.util.UUID

class TournamentSeeder() {
    val tournaments = ArrayList<Tournament>()

    suspend fun run() {
        val games : ArrayList<Game> = Game().get()

        tournaments.add(Tournament(
            UUID.randomUUID().toString(),
            games.filter { it.name == "Valorant" }.first().id,
            "Valorant Champions 2023",
            "2023-08-06",
            "2023-08-26",
            2250000,
            "Riot Games",
            "Offline",
            listOf("Los Angeles"),
            listOf("Kia Forum"),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl. Sed euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl.",
            "logo_valorant_champions_23.png",
            "banner_valorant_champions_23.jpg",
            2,
        ))

        tournaments.add(Tournament(
            UUID.randomUUID().toString(),
            games.filter { it.name == "Mobile Legends Bang Bang" }.first().id,
            "M5 World Championship 2023",
            "2023-11-23",
            "2023-12-17",
            900000,
            "Moonton",
            "Offline",
            listOf("Selangor", "Manila"),
            listOf("Jio Space", "EVM Convention Center", "Rizal Memorial Coliseum"),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl. Sed euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl.",
            "logo_m5.png",
            "banner_m5.jpg",
            1,
        ))

        tournaments.add(Tournament(
            UUID.randomUUID().toString(),
            games.filter { it.name == "League of Legends" }.first().id,
            "Worlds 2023",
            "2023-10-10",
            "2023-11-19",
            2250000,
            "Riot Games",
            "Offline",
            listOf("Seoul", "Busan"),
            listOf("Gocheok Sky Dome", "Sajik Arena", "KBS Stadium", "LOL Park"),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl. Sed euismod, nisl eget ultricies aliquam, nisl nisl aliquet nunc, vitae aliquam nisl nisl vitae nisl.",
            "logo_worlds_23.png",
            "banner_worlds_23.png",
            2,
        ))

        tournaments.forEach{
            it.insertOrUpdate()
        }
    }
}