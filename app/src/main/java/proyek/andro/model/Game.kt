package proyek.andro.model

class Game : BaseModel{
    lateinit var id: String
    lateinit var name: String
    lateinit var description: String
    lateinit var release_date: String
    lateinit var logo: String
    lateinit var banner : String

    constructor() : super("tbGame") {}

    constructor(id : String, name : String, description : String, release_date : String, logo : String, banner : String) : super("tbGame")
    {
        this.id = id
        this.name = name
        this.description = description
        this.release_date = release_date
        this.logo = logo
        this.banner = banner
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("name", name)
        data.put("description", description)
        data.put("release_date", release_date)
        data.put("logo", logo)
        data.put("banner", banner)

        return data
    }
}


