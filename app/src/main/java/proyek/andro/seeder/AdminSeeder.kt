package proyek.andro.seeder

import proyek.andro.model.Admin
import org.mindrot.jbcrypt.BCrypt
import proyek.andro.model.Game
import java.util.UUID

class AdminSeeder {
    var admin = ArrayList<Admin>()

    suspend fun run() {
        admin.add(
            Admin(
            UUID.randomUUID().toString(),
            "Nico",
            "admin1@gmail.com",
            BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
            "08123456789",
            20
        )
        )

        admin.add(Admin(
            UUID.randomUUID().toString(),
            "Kelvin",
            "admin2@gmail.com",
            BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
            "08123456789",
            20
        ))

        admin.add(Admin(
            UUID.randomUUID().toString(),
            "Acto",
            "admin3@gmail.com",
            BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
            "08123456789",
            20
        ))

        admin.forEach {
            it.insertOrUpdate()
        }
    }
}