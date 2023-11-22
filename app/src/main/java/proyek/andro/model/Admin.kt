package proyek.andro.model

class Admin : BaseModel {
    lateinit var id : String
    lateinit var name : String
    lateinit var email : String
    lateinit var password : String
    lateinit var phone : String
    var age = 0

    constructor() : super("tbAdmin") {}

    constructor(
        id: String,
        name: String,
        email: String,
        password: String,
        phone: String,
        age: Int
    ) : super("tbAdmin") {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.phone = phone
        this.age = age
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("name", name)
        data.put("email", email)
        data.put("password", password)
        data.put("phone", phone)
        data.put("age", age)

        return data
    }
}