package proyek.andro.model

class Organization : BaseModel {
    lateinit var id: String
    lateinit var name: String
    lateinit var description: String
    lateinit var logo: String
    lateinit var founded: String
    lateinit var location: String
    lateinit var website: String
    lateinit var ceo: String
    var status : Long = 1

    constructor() : super("tbOrganization") {}

    constructor(id : String, name : String, description : String, logo : String, founded : String, location : String, website : String, ceo : String, status : Long) : super("tbOrganization")
    {
        this.id = id
        this.name = name
        this.description = description
        this.logo = logo
        this.founded = founded
        this.location = location
        this.website = website
        this.ceo = ceo
        this.status = status
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("name", name)
        data.put("description", description)
        data.put("logo", logo)
        data.put("founded", founded)
        data.put("location", location)
        data.put("website", website)
        data.put("ceo", ceo)
        data.put("status", status)

        return data
    }
}