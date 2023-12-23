package proyek.andro.seeder

import proyek.andro.model.Game
import proyek.andro.model.User
import proyek.andro.model.UserFavorite
import java.util.UUID

class UserFavoriteSeeder {
    var favorites = ArrayList<UserFavorite>()

    suspend fun run() {
        var users : ArrayList<User> = User().get(limit=10)
        var games : ArrayList<Game> = Game().get(limit=10)

        favorites.add(UserFavorite(
            UUID.randomUUID().toString(),
            users.filter { it.name == "Nico" && it.role == 0L }.first().id,
            games.filter { it.name == "Valorant" }.first().id
        ))

        favorites.add(UserFavorite(
            UUID.randomUUID().toString(),
            users.filter { it.name == "Nico" && it.role == 0L }.first().id,
            games.filter { it.name == "Mobile Legends Bang Bang" }.first().id
        ))

        favorites.add(UserFavorite(
            UUID.randomUUID().toString(),
            users.filter { it.name == "Kelvin" && it.role == 0L }.first().id,
            games.filter { it.name == "Valorant" }.first().id
        ))

        favorites.add(UserFavorite(
            UUID.randomUUID().toString(),
            users.filter { it.name == "Kelvin" && it.role == 0L }.first().id,
            games.filter { it.name == "League of Legends" }.first().id
        ))

        favorites.add(UserFavorite(
            UUID.randomUUID().toString(),
            users.filter { it.name == "Acto" && it.role == 0L }.first().id,
            games.filter { it.name == "Mobile Legends Bang Bang" }.first().id
        ))

        favorites.add(UserFavorite(
            UUID.randomUUID().toString(),
            users.filter { it.name == "Acto" && it.role == 0L }.first().id,
            games.filter { it.name == "League of Legends" }.first().id
        ))

        favorites.forEach {
            it.insertOrUpdate()
        }
    }
}