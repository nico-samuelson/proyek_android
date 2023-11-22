package proyek.andro.model

class Match : BaseModel {
    lateinit var id: String
    lateinit var tournament_phase : TournamentPhase
    lateinit var team_1 : Team
    lateinit var team_2 : Team
    lateinit var name : String
    lateinit var winner : Team
    lateinit var time : String
    var status = 0

    constructor() : super("tbMatch") {}
    constructor(
        collection: String,
        id: String,
        tournament_phase: TournamentPhase,
        team_1: Team,
        team_2: Team,
        name: String,
        winner: Team,
        time: String,
        status: Int
    ) : super("tbMatch") {
        this.id = id
        this.tournament_phase = tournament_phase
        this.team_1 = team_1
        this.team_2 = team_2
        this.name = name
        this.winner = winner
        this.time = time
        this.status = status
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("tournament_phase", tournament_phase.convertToMap())
        data.put("team_1", team_1.convertToMap())
        data.put("team_2", team_2.convertToMap())
        data.put("name", name)
        data.put("winner", winner.convertToMap())
        data.put("time", time)
        data.put("status", status)

        return data
    }
}