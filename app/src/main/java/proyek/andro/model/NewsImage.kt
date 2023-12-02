package proyek.andro.model

class NewsImage : BaseModel {
    lateinit var id: String
    lateinit var news: String
    lateinit var image: String

    constructor() : super("tbNewsImage") {}

    constructor(id : String, news : String, image : String) : super("tbNewsImage")
    {
        this.id = id
        this.news = news
        this.image = image
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("news", news)
        data.put("image", image)

        return data
    }
}