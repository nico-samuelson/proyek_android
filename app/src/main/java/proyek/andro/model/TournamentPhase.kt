package proyek.andro.model

import kotlinx.coroutines.tasks.await

class TournamentPhase : BaseModel {
    lateinit var id: String
    lateinit var tournament_id : String
    lateinit var name : String
    lateinit var start_date : String
    lateinit var end_date : String
    lateinit var detail : List<String>

    constructor() : super("tbTournamentPhase") {}

    constructor(id : String, tournament_id : String, name : String, start_date : String, end_date : String, detail : List<String>) : super("tbTournamentPhase")
    {
        this.id = id
        this.tournament_id = tournament_id
        this.name = name
        this.start_date = start_date
        this.end_date = end_date
        this.detail = detail
    }

    fun convertToMap() : Map<String, Any> {
        val data = HashMap<String, Any>()

        data.put("id", id)
        data.put("tournament", tournament_id)
        data.put("name", name)
        data.put("start_date", start_date)
        data.put("end_date", end_date)
        data.put("detail", detail)

        return data
    }

    suspend fun findByTournament(id : String) : ArrayList<TournamentPhase> {
        val data = ArrayList<TournamentPhase>()

        val res = collectionRef.whereEqualTo("tournament_id", id)
            .get()
            .await()

        res.forEach { result ->
            data.add(convertToClass(result.data))
        }

        return data
    }
}