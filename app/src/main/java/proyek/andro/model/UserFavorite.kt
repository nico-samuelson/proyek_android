package proyek.andro.model

class UserFavorite : BaseModel {
    lateinit var id: String
    lateinit var user : String
    lateinit var game : String

    constructor() : super("tbFavorite") {}

    constructor(id : String, user : String, game : String) : super("tbFavorite")
    {
        this.id = id
        this.user = user
        this.game = game
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("user", user)
        data.put("game", game)

        return data
    }
}