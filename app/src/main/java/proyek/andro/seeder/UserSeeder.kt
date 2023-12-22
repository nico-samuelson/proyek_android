package proyek.andro.seeder

import org.mindrot.jbcrypt.BCrypt
import proyek.andro.model.User
import java.util.UUID

class UserSeeder {
    var user = ArrayList<User>()

    suspend fun run() {
        user.add(
            User(
                UUID.randomUUID().toString(),
                "Nico",
                "user1@gmail.com",
                BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
                "08123456789",
                "",
                0,
                20
            )
        )

        user.add(User(
            UUID.randomUUID().toString(),
            "Kelvin",
            "user2@gmail.com",
            BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
            "08123456789",
            "",
            0,
            20
        ))

        user.add(User(
            UUID.randomUUID().toString(),
            "Acto",
            "user3@gmail.com",
            BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
            "08123456789",
            "",
            0,
            20
        ))

        user.add(User(
            UUID.randomUUID().toString(),
            "Nico",
            "admin1@gmail.com",
            BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
            "08123456789",
            "",
            1,
            20
        ))

        user.add(User(
            UUID.randomUUID().toString(),
            "Kelvin",
            "admin2@gmail.com",
            BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
            "08123456789",
            "",
            1,
            20
        ))

        user.add(User(
            UUID.randomUUID().toString(),
            "Acto",
            "admin3@gmail.com",
            BCrypt.hashpw("123456789", BCrypt.gensalt(12)),
            "08123456789",
            "",
            1,
            20
        ))

        user.forEach {
            it.insertOrUpdate()
        }
    }
}