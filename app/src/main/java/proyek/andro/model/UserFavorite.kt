package proyek.andro.model

class UserFavorite : BaseModel {
    lateinit var id: String
    lateinit var user : User
    lateinit var game : Game

    constructor() : super("tbFavorite") {}

    constructor(id : String, user : User, game : Game) : super("tbFavorite")
    {
        this.id = id
        this.user = user
        this.game = game
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("user", user.convertToMap())
        data.put("game", game.convertToMap())

        return data
    }
}