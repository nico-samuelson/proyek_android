package proyek.andro.seeder

import com.google.firebase.firestore.FirebaseFirestore
import proyek.andro.model.Game
import proyek.andro.model.Match
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import proyek.andro.model.TournamentPhase
import java.util.UUID

class MatchSeeder {
    val matches = ArrayList<Match>()

    suspend fun run() {
        val tournamentPhases : ArrayList<TournamentPhase> = TournamentPhase().get(limit=100)
        val tournaments : ArrayList<Tournament> = Tournament().get(limit=100)
        val teams : ArrayList<Team> = Team().get(limit=100)
        val games : ArrayList<Game> = Game().get()

        val valorant = games.filter { it.name == "Valorant" }.first()
        val mlbb = games.filter { it.name == "Mobile Legends Bang Bang" }.first()
        val champions = tournaments.filter { it.name == "Valorant Champions 2023" }.first()
        val avl = tournaments.filter { it.name == "AfreecaTV Valorant League" }.first()

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Natus Vincere" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup D",
            teams.filter { it.name == "Natus Vincere" && it.game == valorant.id }.first().id,
            "2023-08-06 19:15 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup D",
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "2023-08-06 22:20 UTC",
            "2-1",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "FUT Esports" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup B",
            teams.filter { it.name == "FUT Esports" && it.game == valorant.id }.first().id,
            "2023-08-07 19:10 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "FunPlus Phoenix" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup B",
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "2023-08-07 21:10 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Natus Vincere" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup D",
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "2023-08-07 23:20 UTC",
            "1-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "NRG" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Bilibili Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup C",
            teams.filter { it.name == "Bilibili Gaming" && it.game == valorant.id }.first().id,
            "2023-08-08 19:10 UTC",
            "0-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "ZETA DIVISION" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup C",
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            "2023-08-08 21:40 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "FUT Esports" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup B",
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "2023-08-08 23:45 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "KRÜ Esports" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup A",
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "2023-08-09 19:10 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Giants Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup A",
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "2023-08-09 21:20 UTC",
            "2-1",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Bilibili Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup C",
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            "2023-08-09 23:10 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup D",
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "2023-08-10 19:10 UTC",
            "0-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "FunPlus Phoenix" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup B",
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "2023-08-10 21:30 UTC",
            "0-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup A",
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "2023-08-10 23:50 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "KRÜ Esports" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Giants Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup A",
            teams.filter { it.name == "Giants Gaming" && it.game == valorant.id }.first().id,
            "2023-08-11 19:10 UTC",
            "0-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "ZETA DIVISION" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "NRG" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup C",
            teams.filter { it.name == "NRG" && it.game == valorant.id }.first().id,
            "2023-08-11 21:50 UTC",
            "0-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Natus Vincere" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup D",
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "2023-08-12 19:10 UTC",
            "1-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "FUT Esports" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup B",
            teams.filter { it.name == "FUT Esports" && it.game == valorant.id }.first().id,
            "2023-08-12 23:00 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Giants Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup A",
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "2023-08-13 19:10 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Bilibili Gaming" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "NRG" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGroup CC",
            teams.filter { it.name == "Bilibili Gaming" && it.game == valorant.id }.first().id,
            "2023-08-13 22:15 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nUpper Bracket Quarterfinals",
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "2023-08-16 19:10 UTC",
            "0-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "FUT Esports" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nUpper Bracket Quarterfinals",
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "2023-08-16 21:25 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Bilibili Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nUpper Bracket Quarterfinals",
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "2023-08-17 19:10 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nUpper Bracket Quarterfinals",
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "2023-08-17 21:25 UTC",
            "2-1",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "FUT Esports" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nLower Bracket Round 1",
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            "2023-08-18 19:10 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Bilibili Gaming" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nLower Bracket Round 1",
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "2023-08-18 21:15 UTC",
            "1-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nUpper Bracket Semifinals",
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "2023-08-19 19:10 UTC",
            "1-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nUpper Bracket Semifinals",
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "2023-08-19 22:25 UTC",
            "2-0",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nLower Bracket Quarterfinals",
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "2023-08-20 19:10 UTC",
            "2-1",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nLower Bracket Quarterfinals",
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            "2023-08-20 22:25 UTC",
            "1-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nUpper Bracket Finals",
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "2023-08-24 19:10 UTC",
            "2-1",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Fnatic" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nLower Bracket Semifinals",
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "2023-08-24 22:50 UTC",
            "1-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "LOUD" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nLower Bracket Final",
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "2023-08-25 19:10 UTC",
            "3-2",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == champions.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "Valorant Champions 2023\nGrand Final",
            teams.filter { it.name == "Evil Geniuses" && it.game == valorant.id }.first().id,
            "2023-08-26 19:30 UTC",
            "1-3",
            champions.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "Sentinels" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup A",
            teams.filter { it.name == "Sentinels" && it.game == valorant.id }.first().id,
            "2023-12-05 08:00 UTC",
            "2-1",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "FULL SENSE" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup A",
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "2023-12-05 11:00 UTC",
            "1-2",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "Sentinels" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup A",
            teams.filter { it.name == "Sentinels" && it.game == valorant.id }.first().id,
            "2023-12-07 05:00 UTC",
            "2-0",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "FULL SENSE" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup A",
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "2023-12-07 08:00 UTC",
            "2-0",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "DRX" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup A",
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "2023-12-07 11:20 UTC",
            "0-2",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "Fancy United" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup B",
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            "2023-12-06 08:00 UTC",
            "0-2",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup B",
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "2023-12-06 10:25 UTC",
            "2-0",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup B",
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            "2023-12-08 05:00 UTC",
            "1-2",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "Fancy United" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup B",
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "2023-12-08 09:45 UTC",
            "1-2",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Group Stage" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "EDward Gaming" && it.game == valorant.id }.first().id,
            "AVL 2023\nGroup B",
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            "2023-12-08 14:00 UTC",
            "2-0",
            avl.id,
            2
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "Sentinels" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Team Liquid" && it.game == valorant.id }.first().id,
            "AVL 2023\nPlayoffs",
            "",
            "2023-12-09 08:00 UTC",
            "1-0",
            avl.id,
            1
        ))

        matches.add(Match(
            UUID.randomUUID().toString(),
            tournamentPhases.filter { it.name == "Playoffs" && it.tournament == avl.id }.first().id,
            teams.filter { it.name == "T1" && it.game == valorant.id }.first().id,
            teams.filter { it.name == "Paper Rex" && it.game == valorant.id }.first().id,
            "AVL 2023\nPlayoffs",
            "",
            "2023-12-09 11:00 UTC",
            "",
            avl.id,
            0
        ))

        matches.forEach {
            it.insertOrUpdate()
        }
    }
}