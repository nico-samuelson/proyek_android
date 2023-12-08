package proyek.andro.seeder

import proyek.andro.model.Tournament
import proyek.andro.model.TournamentPhase
import java.util.UUID

class TournamentPhaseSeeder {
    var phases = ArrayList<TournamentPhase>()

    suspend fun run() {
        val tournaments: ArrayList<Tournament> = Tournament().get()

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
                "Group Stage",
                "2023-08-06",
                "2023-08-13",
                listOf(
                    "4 groups of four teams in a double-elimination (GSL) format.",
                    "All matches are Bo3",
                    "Top 2 teams from each group advance to the Playoffs"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "Valorant Champions 2023" }.first().id,
                "Playoffs",
                "2023-08-16",
                "2023-08-26",
                listOf(
                    "Double-Elimination bracket",
                    "All matches (excl. Lower Bracket Final and Grand Final) are Bo3",
                    "Lower Bracket Final and Grand Final are Bo5"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
                "Wildcard",
                "2023-11-23",
                "2023-11-25",
                listOf(
                    "Eight teams will be randomly drawn into two groups",
                    "Single Round Robin",
                    "All matches are Bo3",
                    "Top 2 teams from each group play in a Crossover Match",
                    "Bottom 2 teams from each group are eliminated"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
                "Crossover Match",
                "2023-11-26",
                "2023-11-26",
                listOf(
                    "First place teams compete in a Bo5 against the other group's runner-up",
                    "The winners of each match advance to Group Stage",
                    "The losers are eliminated"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
                "Group Stage",
                "2023-12-02",
                "2023-12-07",
                listOf(
                    "16 teams are divided into 4 groups",
                    "Single Round Robin",
                    "All matches are Bo3",
                    "Top 2 teams from each group advance to Knockout Stage",
                    "Bottom 2 teams from each group are eliminated"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "M5 World Championship 2023" }.first().id,
                "Knockout Stage",
                "2023-12-09",
                "2023-12-17",
                listOf(
                    "Double Elimination Bracket",
                    "All Matches are Bo5 until the Grand Finals",
                    "Grand Final is Bo7"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "Worlds 2023" }.first().id,
                "Play-In Round 1",
                "2023-10-10",
                "2023-10-14",
                listOf(
                    "Eight teams are divided into two groups where they play a Double Elimination bracket",
                    "Top 2 teams in each group advance to Play-In Round 2",
                    "Bottom 2 teams in each group are eliminated",
                    "All matches are Bo3"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "Worlds 2023" }.first().id,
                "Play-In Round 2",
                "2023-10-15",
                "2023-10-15",
                listOf(
                    "Four teams from Play-In: Round 1",
                    "The Upper Bracket winner from each group compete against Lower Bracket winners of the other group",
                    "All matches are played in a Bo5",
                    "Two winners will advance to Swiss"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "Worlds 2023" }.first().id,
                "Swiss",
                "2023-10-19",
                "2023-10-29",
                listOf(
                    "Two teams from the Play-In Stage join fourteen teams with direct entry from China, South Korea, Europe and North America",
                    "Round 1 teams will be paired against teams from a different region",
                    "Rounds 2 to 5 are seeded based on each team win-loss record",
                    "Elimination and Advancement matches are Bo3",
                    "All other matches are Bo1",
                    "Teams that reach 3 wins in swiss advance to Knockout Stage",
                    "Teams that reach 3 losses in swiss are eliminated",
                    "Throughout the Swiss Stage, side selection will be determined by initial draw pool - the higher pool will receive side selection",
                    "In matches between teams from the same draw pool, side selection will be determined by team's drawing order"
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "Worlds 2023" }.first().id,
                "Knockout Stage",
                "2023-11-02",
                "2023-11-19",
                listOf(
                    "Eight teams play in a single elimination bracket.",
                    "Teams with Side Selection Privilege can choose the side in game 1, after that, the losing team can choose the side in the next game.",
                    "In Quarterfinals, Side Selection Privilege will be granted to teams with better match record in Swiss Stage. In the case where both teams have the same match record, Side Selection Privilege will be determined by the order teams are drawn in Quarterfinals.",
                    "In the following matches, the Side Selection Privilege will be determined by coin flip.",
                    "All matches are Bo5."
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
                "Group Stage",
                "2023-12-05",
                "2023-12-08",
                listOf(
                    "Two Double-Elimination Format (GSL) Groups",
                    "Each group has 4 teams",
                    "All matches are Bo3",
                    "Top 2 teams from each group advance to Playoffs",
                )
            )
        )

        phases.add(
            TournamentPhase(
                UUID.randomUUID().toString(),
                tournaments.filter { it.name == "AfreecaTV Valorant League" }.first().id,
                "Playoffs",
                "2023-12-09",
                "2023-12-10",
                listOf(
                    "Single-Elimination bracket",
                    "Semifinals are Bo3",
                    "Grand Final is Bo5",
                )
            )
        )

        phases.forEach {
            it.insertOrUpdate()
        }
    }
}