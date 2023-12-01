package proyek.andro.model

class Participant : BaseModel {
    lateinit var id: String
    lateinit var tournament_id : String
    lateinit var team_id : String
    lateinit var group : String
    var rank : Long = 0
    var points : Long = 0

    constructor() : super("tbParticipant") {}

    constructor(id : String, tournament_id : String, team_id : String, group : String, rank : Long, points : Long) : super("tbParticipant")
    {
        this.id = id
        this.tournament_id = tournament_id
        this.team_id = team_id
        this.group = group
        this.rank = rank
        this.points = points
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("tournament", tournament_id)
        data.put("team", team_id)
        data.put("group", group)
        data.put("rank", rank)
        data.put("points", points)

        return data
    }
}