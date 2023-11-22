package proyek.andro.model

class TournamentPhase : BaseModel {
    lateinit var id: String
    lateinit var tournament : Tournament
    lateinit var name : String
    lateinit var start_date : String
    lateinit var end_date : String
    lateinit var format : String

    constructor() : super("tbTournamentPhase") {}

    constructor(id : String, tournament : Tournament, name : String, start_date : String, end_date : String, format : String) : super("tbTournamentPhase")
    {
        this.id = id
        this.tournament = tournament
        this.name = name
        this.start_date = start_date
        this.end_date = end_date
        this.format = format
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("tournament", tournament.convertToMap())
        data.put("name", name)
        data.put("start_date", start_date)
        data.put("end_date", end_date)
        data.put("format", format)

        return data
    }
}