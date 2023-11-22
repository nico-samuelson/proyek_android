package proyek.andro.model

class Participant : BaseModel {
    lateinit var id: String
    lateinit var tournament : Tournament
    lateinit var team : Team
    lateinit var group : String
    var rank = 0
    var points = 0

    constructor() : super("tbParticipant") {}

    constructor(id : String, tournament : Tournament, team : Team, group : String, rank : Int, points : Int) : super("tbParticipant")
    {
        this.id = id
        this.tournament = tournament
        this.team = team
        this.group = group
        this.rank = rank
        this.points = points
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("tournament", tournament.convertToMap())
        data.put("team", team.convertToMap())
        data.put("group", group)
        data.put("rank", rank)
        data.put("points", points)

        return data
    }
}