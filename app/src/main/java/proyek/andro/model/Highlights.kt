package proyek.andro.model

class Highlights : BaseModel {
    lateinit var id: String
    lateinit var tournament : String
    lateinit var thumbnail : String
    lateinit var title : String
    lateinit var link : String

    constructor() : super("tbHighlight") {}

    constructor(id : String, tournament : String, thumbnail : String, title : String, link : String) : super("tbHighlight")
    {
        this.id = id
        this.tournament = tournament
        this.thumbnail = thumbnail
        this.title = title
        this.link = link
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("tournament", tournament)
        data.put("thumbnail", thumbnail)
        data.put("title", title)
        data.put("link", link)

        return data
    }
}