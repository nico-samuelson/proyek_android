package proyek.andro.model

class News : BaseModel {
    lateinit var id: String
    lateinit var title: String
    lateinit var content: String
    lateinit var author : String
    lateinit var date : String

    constructor() : super("tbNews") {}

    constructor(id : String, title : String, content : String, author : String, date : String) : super("tbNews")
    {
        this.id = id
        this.title = title
        this.content = content
        this.author = author
        this.date = date
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("title", title)
        data.put("content", content)
        data.put("author", author)
        data.put("date", date)

        return data
    }
}