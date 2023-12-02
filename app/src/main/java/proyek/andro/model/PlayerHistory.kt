package proyek.andro.model

class PlayerHistory : BaseModel {
    lateinit var id: String
    lateinit var player : String
    lateinit var match : String
    lateinit var team : String
    constructor() : super("tbPlayerHistory") {}

    constructor(id : String, player : String, match : String, team : String) : super("tbPlayerHistory")
    {
        this.id = id
        this.player = player
        this.match = match
        this.team = team
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("player", player)
        data.put("match", match)
        data.put("team", team)

        return data
    }
}