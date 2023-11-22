package proyek.andro.model

class NewsImage : BaseModel {
    lateinit var id: String
    lateinit var news_id: String
    lateinit var image: String

    constructor() : super("tbNewsImage") {}

    constructor(id : String, news_id : String, image : String) : super("tbNewsImage")
    {
        this.id = id
        this.news_id = news_id
        this.image = image
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("news_id", news_id)
        data.put("image", image)

        return data
    }
}