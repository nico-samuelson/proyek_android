package proyek.andro.model

class PlayerHistory : BaseModel {
    lateinit var id: String
    lateinit var player : Player
    lateinit var match : Match
    lateinit var team : Team
    constructor() : super("tbPlayerHistory") {}

    constructor(id : String, player : Player, match : Match, team : Team) : super("tbPlayerHistory")
    {
        this.id = id
        this.player = player
        this.match = match
        this.team = team
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("player", player.convertToMap())
        data.put("match", match.convertToMap())
        data.put("team", team.convertToMap())

        return data
    }
}