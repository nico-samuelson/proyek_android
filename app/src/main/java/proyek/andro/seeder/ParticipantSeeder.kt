package proyek.andro.seeder

import proyek.andro.model.Game
import proyek.andro.model.Organization
import proyek.andro.model.Participant
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import java.util.UUID

class ParticipantSeeder {
    val participants : ArrayList<Participant> = ArrayList()

    suspend fun run() {
        val tournaments : ArrayList<Tournament> = Tournament().get()
        val teams : ArrayList<Team> = Team().get(limit=1000)
        val games : ArrayList<Game> = Game().get()

        val mlbb = games.filter { it.name == "Mobile Legends Bang Bang" }[0]
        val valorant = games.filter { it.name == "Valorant" }[0]

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "KeepBest Gaming" && it.game == mlbb.id }.first().id,
            "B",
            22,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "4Merical Esports" && it.game == mlbb.id }.first().id,
            "A",
            21,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Umbrella Squad" && it.game == mlbb.id }.first().id,
            "B",
            20,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Team Falcons" && it.game == mlbb.id }.first().id,
            "A",
            19,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Niightmare Esports" && it.game == mlbb.id }.first().id,
            "A",
            18,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Imperio" && it.game == mlbb.id }.first().id,
            "B",
            17,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "TheOhioBrothers" && it.game == mlbb.id }.first().id,
            "D",
            16,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Team Lilgun" && it.game == mlbb.id }.first().id,
            "C",
            15,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "RRQ Akira" && it.game == mlbb.id }.first().id,
            "B",
            14,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Bigetron Sons" && it.game == mlbb.id }.first().id,
            "A",
            13,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "HomeBois" && it.game == mlbb.id }.first().id,
            "D",
            12,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Team Flash" && it.game == mlbb.id }.first().id,
            "C",
            11,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Team SMG" && it.game == mlbb.id }.first().id,
            "B",
            10,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Triple Esports" && it.game == mlbb.id }.first().id,
            "A",
            9,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Burmese Ghouls" && it.game == mlbb.id }.first().id,
            "C",
            8,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Fire Flux Esports" && it.game == mlbb.id }.first().id,
            "B",
            7,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "See You Soon" && it.game == mlbb.id }.first().id,
            "A",
            6,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Geek Fam ID" && it.game == mlbb.id }.first().id,
            "D",
            5,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Deus Vult" && it.game == mlbb.id }.first().id,
            "D",
            4,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "Blacklist International" && it.game == mlbb.id }.first().id,
            "B",
            3,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "ONIC Esports" && it.game == mlbb.id }.first().id,
            "A",
            2,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
            teams.filter { it.name == "AP.Bren" && it.game == mlbb.id }.first().id,
            "C",
            1,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            "D",
            16,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "ZETA DIVISION" && it.game == valorant.id }.first().id,
            "C",
            15,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "FunPlus Phoenix" && it.game == valorant.id }.first().id,
            "B",
            14,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "KRÜ Esports" && it.game == valorant.id }.first().id,
            "A",
            13,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "Natus Vincere" && it.game == valorant.id }.first().id,
            "D",
            12,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "NRG" && it.game == valorant.id }.first().id,
            "C",
            11,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
                    teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "B",
            10,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "Giants Gaming" && it.game == valorant.id }.first().id,
            "A",
            9,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "Bilibili Gaming" && it.game == valorant.id }.first().id,
            "C",
            8,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "FUT Esports" && it.game == valorant.id }.first().id,
            "B",
            7,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "A",
            6,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "D",
            5,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            "C",
            4,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "D",
            3,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "A",
            2,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "B",
            1,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
            teams.filter { it.name == "Fancy United" && it.game == valorant.id }.first().id,
            "B",
            8,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
            teams.filter { it.name == "FULL SENSE" && it.game == valorant.id }.first().id,
            "A",
            7,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "B",
            6,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "A",
            5,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "B",
            4,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            "B",
            3,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "A",
            2,
            0
        ))

        participants.add(Participant(
            UUID.randomUUID().toString(),
            tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
            teams.filter { it.name == "Sentinels" && it.game == valorant.id }.first().id,
            "A",
            1,
            0
        ))

        participants.forEach {
            it.insertOrUpdate()
        }
    }
}