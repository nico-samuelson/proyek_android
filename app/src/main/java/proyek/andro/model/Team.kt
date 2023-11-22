package proyek.andro.model

class Team : BaseModel {
    lateinit var id: String
    lateinit var game : Game
    lateinit var name: String
    lateinit var logo: String
    lateinit var captain : Player
    lateinit var coach : String
    lateinit var founded : String
    lateinit var description : String
    var status = 1

    constructor() : super("tbTeam") {}

    constructor(id : String, game : Game, name : String, logo : String, captain : Player, coach : String, founded : String, description : String, status : Int) : super("tbTeam")
    {
        this.id = id
        this.game = game
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
        data.put("game", game.convertToMap())
        data.put("name", name)
        data.put("logo", logo)
        data.put("captain", captain.convertToMap())
        data.put("coach", coach)
        data.put("founded", founded)
        data.put("description", description)
        data.put("status", status)

        return data
    }
}