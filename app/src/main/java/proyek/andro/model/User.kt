package proyek.andro.model

class User : BaseModel {
    lateinit var id: String
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String
    lateinit var phone: String
    lateinit var remember_token: String
    var role : Long = 0
    var age : Long = 0

    constructor() : super("tbUser") {}

    constructor(
        id : String,
        name : String,
        email : String,
        password : String,
        phone : String,
        remember_token : String,
        role : Long,
        age : Long
    ) : super("tbUser")
    {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.phone = phone
        this.age = age
        this.role = role
        this.remember_token = remember_token
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("name", name)
        data.put("email", email)
        data.put("password", password)
        data.put("phone", phone)
        data.put("age", age)
        data.put("role", role)
        data.put("remember_token", remember_token)

        return data
    }
}