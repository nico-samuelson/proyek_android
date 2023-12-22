package proyek.andro.model

class Player : BaseModel {
    lateinit var id: String
    lateinit var team : String
    lateinit var name : String
    lateinit var nickname : String
    lateinit var position : String
    lateinit var nationality : String
    lateinit var photo : String
    var status : Long = 0
    var captain : Boolean = false

    constructor() : super("tbPlayer") {}
    constructor(
        id: String,
        team : String,
        name: String,
        nickname: String,
        position: String,
        nationality: String,
        photo: String,
        captain: Boolean,
        status: Long,
    ) : super("tbPlayer") {
        this.id = id
        this.team = team
        this.name = name
        this.nickname = nickname
        this.position = position
        this.nationality = nationality
        this.photo = photo
        this.status = status
        this.captain = captain
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("team", team)
        data.put("name", name)
        data.put("nickname", nickname)
        data.put("position", position)
        data.put("nationality", nationality)
        data.put("photo", photo)
        data.put("status", status)
        data.put("captain", captain)

        return data
    }
}