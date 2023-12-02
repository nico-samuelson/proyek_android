package proyek.andro.model

class Participant : BaseModel {
    lateinit var id: String
    lateinit var tournament : String
    lateinit var team : String
    lateinit var group : String
    var rank : Long = 0
    var points : Long = 0

    constructor() : super("tbParticipant") {}

    constructor(id : String, tournament : String, team : String, group : String, rank : Long, points : Long) : super("tbParticipant")
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
        data.put("tournament", tournament)
        data.put("team", team)
        data.put("group", group)
        data.put("rank", rank)
        data.put("points", points)

        return data
    }
}