package proyek.andro.model

class Team : BaseModel {
    lateinit var id: String
    lateinit var game_id : String
    lateinit var name: String
    lateinit var logo: String
    lateinit var captain : String
    lateinit var coach : String
    lateinit var founded : String
    lateinit var description : String
    var status : Long = 1

    constructor() : super("tbTeam") {}

    constructor(id : String, game_id : String, name : String, logo : String, captain : String, coach : String, founded : String, description : String, status : Long) : super("tbTeam")
    {
        this.id = id
        this.game_id = game_id
        this.name = name
        this.logo = logo
        this.captain = captain
        this.coach = coach
        this.founded = founded
        this.description = description
        this.status = status
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("game", game_id)
        data.put("name", name)
        data.put("logo", logo)
        data.put("captain", captain)
        data.put("coach", coach)
        data.put("founded", founded)
        data.put("description", description)
        data.put("status", status)

        return data
    }
}