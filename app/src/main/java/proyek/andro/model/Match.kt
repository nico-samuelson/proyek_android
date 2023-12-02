package proyek.andro.model

class Match : BaseModel {
    lateinit var id: String
    lateinit var tournament_phase : String
    lateinit var team1 : String
    lateinit var team2 : String
    lateinit var name : String
    lateinit var winner : String
    lateinit var time : String
    var status : Long = 0

    constructor() : super("tbMatch") {}
    constructor(
        id: String,
        tournament_phase_id: String,
        team1: String,
        team2: String,
        name: String,
        winner: String,
        time: String,
        status: Long
    ) : super("tbMatch") {
        this.id = id
        this.tournament_phase = tournament_phase_id
        this.team1 = team1
        this.team2 = team2
        this.name = name
        this.winner = winner
        this.time = time
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
        data.put("status", status)

        return data
    }
}