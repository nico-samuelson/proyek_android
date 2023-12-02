package proyek.andro.model

class Team : BaseModel {
    lateinit var id: String
    lateinit var game : String
    lateinit var organization : String
    lateinit var name: String
    lateinit var logo: String
    lateinit var coach : String
    lateinit var founded : String
    lateinit var description : String
    var status : Long = 1

    constructor() : super("tbTeam") {}

    constructor(id : String, organization : String, game : String, name : String, logo : String, coach : String, founded : String, description : String, status : Long) : super("tbTeam")
    {
        this.id = id
        this.organization = organization
        this.game = game
        this.name = name
        this.logo = logo
        this.coach = coach
        this.founded = founded
        this.description = description
        this.status = status
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("organization", organization)
        data.put("game", game)
        data.put("name", name)
        data.put("logo", logo)
        data.put("coach", coach)
        data.put("founded", founded)
        data.put("description", description)
        data.put("status", status)

        return data
    }
}