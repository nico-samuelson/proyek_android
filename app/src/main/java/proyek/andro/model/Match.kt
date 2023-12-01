package proyek.andro.model

class Match : BaseModel {
    lateinit var id: String
    lateinit var tournament_phase_id : String
    lateinit var team_id_1 : String
    lateinit var team_id_2 : String
    lateinit var name : String
    lateinit var winner_id : String
    lateinit var time : String
    var status : Long = 0

    constructor() : super("tbMatch") {}
    constructor(
        collection: String,
        id: String,
        tournament_phase_id: String,
        team_id_1: String,
        team__id_2: String,
        name: String,
        winner_id: String,
        time: String,
        status: Long
    ) : super("tbMatch") {
        this.id = id
        this.tournament_phase_id = tournament_phase_id
        this.team_id_1 = team_id_1
        this.team_id_2 = team_id_2
        this.name = name
        this.winner_id = winner_id
        this.time = time
        this.status = status
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("tournament_phase", tournament_phase_id)
        data.put("team_1", team_id_1)
        data.put("team_2", team_id_2)
        data.put("name", name)
        data.put("winner", winner_id)
        data.put("time", time)
        data.put("status", status)

        return data
    }
}