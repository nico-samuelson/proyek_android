package proyek.andro.model

class Match : BaseModel {
    lateinit var id: String
    lateinit var tournament_phase : String
    lateinit var team1 : String
    lateinit var team2 : String
    lateinit var name : String
    lateinit var winner : String
    lateinit var time : String
    lateinit var score : String
    lateinit var tournament : String
    var status : Long = 0

    constructor() : super("tbMatch") {}
    constructor(
        id: String,
        tournament_phase: String,
        team1: String,
        team2: String,
        name: String,
        winner: String,
        time: String,
        score : String,
        tournament: String,
        status: Long
    ) : super("tbMatch") {
        this.id = id
        this.tournament_phase = tournament_phase
        this.team1 = team1
        this.team2 = team2
        this.name = name
        this.winner = winner
        this.score = score
        this.time = time
        this.tournament = tournament
        this.status = status
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("tournament_phase", tournament_phase)
        data.put("team1", team1)
        data.put("team2", team2)
        data.put("name", name)
        data.put("winner", winner)
        data.put("time", time)
        data.put("score", score)
        data.put("tournament", tournament)
        data.put("status", status)

        return data
    }
}