package proyek.andro.seeder

import proyek.andro.model.Organization
import proyek.andro.model.Participant

class ParticipantSeeder {
    val participants : ArrayList<Participant> = ArrayList()

    suspend fun run() {
        val orgs : ArrayList<Organization> = Organization().get()
    }
}