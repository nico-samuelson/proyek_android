package proyek.andro.model

class User : BaseModel {
    lateinit var id: String
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String
    lateinit var phone: String
    var age : Long = 0

    constructor() : super("tbUser") {}

    constructor(id : String, name : String, email : String, password : String, phone : String, age : Long) : super("tbUser")
    {
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