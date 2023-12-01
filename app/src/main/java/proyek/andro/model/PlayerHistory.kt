package proyek.andro.model

class PlayerHistory : BaseModel {
    lateinit var id: String
    lateinit var player_id : String
    lateinit var match_id : String
    lateinit var team_id : String
    constructor() : super("tbPlayerHistory") {}

    constructor(id : String, player_id : String, match_id : String, team_id : String) : super("tbPlayerHistory")
    {
        this.id = id
        this.player_id = player_id
        this.match_id = match_id
        this.team_id = team_id
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("player", player_id)
        data.put("match", match_id)
        data.put("team", team_id)

        return data
    }
}