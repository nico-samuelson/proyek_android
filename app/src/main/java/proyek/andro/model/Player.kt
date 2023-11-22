package proyek.andro.model

class Player : BaseModel {
    lateinit var id: String
    lateinit var team : Team
    lateinit var name : String
    lateinit var nickname : String
    lateinit var position : String
    lateinit var nationality : String
    lateinit var photo : String
    var status = 0

    constructor() : super("tbPlayer") {}
    constructor(
        id: String,
        team: Team,
        name: String,
        nickname: String,
        position: String,
        nationality: String,
        photo: String,
        status: Int
    ) : super("tbGame") {
        this.id = id
        this.team = team
        this.name = name
        this.nickname = nickname
        this.position = position
        this.nationality = nationality
        this.photo = photo
        this.status = status
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("team", team.convertToMap())
        data.put("name", name)
        data.put("nickname", nickname)
        data.put("position", position)
        data.put("nationality", nationality)
        data.put("photo", photo)
        data.put("status", status)

        return data
    }
}