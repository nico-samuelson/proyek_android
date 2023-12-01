package proyek.andro.model

class UserFavorite : BaseModel {
    lateinit var id: String
    lateinit var user_id : String
    lateinit var game_id : String

    constructor() : super("tbFavorite") {}

    constructor(id : String, user_id : String, game_id : String) : super("tbFavorite")
    {
        this.id = id
        this.user_id = user_id
        this.game_id = game_id
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("user", user_id)
        data.put("game", game_id)

        return data
    }
}