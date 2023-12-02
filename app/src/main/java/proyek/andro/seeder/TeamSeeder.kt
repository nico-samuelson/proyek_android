package proyek.andro.seeder

import proyek.andro.model.Game
import proyek.andro.model.Organization
import proyek.andro.model.Player
import proyek.andro.model.Team
import java.util.UUID

class TeamSeeder {
    val teams : ArrayList<Team> = ArrayList()

    suspend fun run() {
        val games : ArrayList<Game> = Game().get()
        val orgs : ArrayList<Organization> = Organization().get(100)

        val valorant = games.filter { it.name == "Valorant" }.first()
        val mlbb = games.filter { it.name == "Mobile Legends Bang Bang" }.first()

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Evil Geniuses" }.first().id,
            valorant.id,
            orgs.filter { it.name == "Evil Geniuses" }.first().name,
            orgs.filter { it.name == "Evil Geniuses" }.first().logo,
            "potter",
            "2021",
            orgs.filter { it.name == "Evil Geniuses" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "NRG" }.first().id,
            valorant.id,
            orgs.filter { it.name == "NRG" }.first().name,
            orgs.filter { it.name == "NRG" }.first().logo,
            "Chet",
            "2020",
            orgs.filter { it.name == "NRG" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "LOUD" }.first().id,
            valorant.id,
            orgs.filter { it.name == "LOUD" }.first().name,
            orgs.filter { it.name == "LOUD" }.first().logo,
            "peu",
            "2019",
            orgs.filter { it.name == "LOUD" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "KRÜ Esports" }.first().id,
            valorant.id,
            orgs.filter { it.name == "KRÜ Esports" }.first().name,
            orgs.filter { it.name == "KRÜ Esports" }.first().logo,
            "Atom",
            "2021",
            orgs.filter { it.name == "KRÜ Esports" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Fnatic" }.first().id,
            valorant.id,
            orgs.filter { it.name == "Fnatic" }.first().name,
            orgs.filter { it.name == "Fnatic" }.first().logo,
            "Elmapuddy",
            "2021",
            orgs.filter { it.name == "Fnatic" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Team Liquid" }.first().id,
            valorant.id,
            orgs.filter { it.name == "Team Liquid" }.first().name,
            orgs.filter { it.name == "Team Liquid" }.first().logo,
            "eMIL",
            "2020",
            orgs.filter { it.name == "Team Liquid" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "FUT Esports" }.first().id,
            valorant.id,
            orgs.filter { it.name == "FUT Esports" }.first().name,
            orgs.filter { it.name == "FUT Esports" }.first().logo,
            "GAIS",
            "2020",
            orgs.filter { it.name == "FUT Esports" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Paper Rex" }.first().id,
            valorant.id,
            orgs.filter { it.name == "Paper Rex" }.first().name,
            orgs.filter { it.name == "Paper Rex" }.first().logo,
            "alecks",
            "2020",
            orgs.filter { it.name == "Paper Rex" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "DRX" }.first().id,
            valorant.id,
            orgs.filter { it.name == "DRX" }.first().name,
            orgs.filter { it.name == "DRX" }.first().logo,
            "termi",
            "2022",
            orgs.filter { it.name == "DRX" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "T1" }.first().id,
            valorant.id,
            orgs.filter { it.name == "T1" }.first().name,
            orgs.filter { it.name == "T1" }.first().logo,
            "Autumn",
            "2020",
            orgs.filter { it.name == "T1" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "ZETA DIVISION" }.first().id,
            valorant.id,
            orgs.filter { it.name == "ZETA DIVISION" }.first().name,
            orgs.filter { it.name == "ZETA DIVISION" }.first().logo,
            "Carlão",
            "2020",
            orgs.filter { it.name == "ZETA DIVISION" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "EDward Gaming" }.first().id,
            valorant.id,
            orgs.filter { it.name == "EDward Gaming" }.first().name,
            orgs.filter { it.name == "EDward Gaming" }.first().logo,
            "AfteR",
            "2020",
            orgs.filter { it.name == "EDward Gaming" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Bilibili Gaming" }.first().id,
            valorant.id,
            orgs.filter { it.name == "Bilibili Gaming" }.first().name,
            orgs.filter { it.name == "Bilibili Gaming" }.first().logo,
            "JeXeN",
            "2023",
            orgs.filter { it.name == "Bilibili Gaming" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "FunPlus Phoenix" }.first().id,
            valorant.id,
            orgs.filter { it.name == "FunPlus Phoenix" }.first().name,
            orgs.filter { it.name == "FunPlus Phoenix" }.first().logo,
            "NaThanD",
            "2020",
            orgs.filter { it.name == "FunPlus Phoenix" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Natus Vincere" }.first().id,
            valorant.id,
            orgs.filter { it.name == "Natus Vincere" }.first().name,
            orgs.filter { it.name == "Natus Vincere" }.first().logo,
            "d00mbr0s",
            "2021",
            orgs.filter { it.name == "Natus Vincere" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "AP.Bren" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "AP.Bren" }.first().name,
            orgs.filter { it.name == "AP.Bren" }.first().logo,
            "Ducky",
            "2018",
            orgs.filter { it.name == "AP.Bren" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Blacklist International" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Blacklist International" }.first().name,
            orgs.filter { it.name == "Blacklist International" }.first().logo,
            "Master the Basics",
            "2020",
            orgs.filter { it.name == "Blacklist International" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "ONIC Esports" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "ONIC Esports" }.first().name,
            orgs.filter { it.name == "ONIC Esports" }.first().logo,
            "Yeb",
            "2018",
            orgs.filter { it.name == "ONIC Esports" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Geek Fam ID" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Geek Fam ID" }.first().name,
            orgs.filter { it.name == "Geek Fam ID" }.first().logo,
            "Ervan",
            "2019",
            orgs.filter { it.name == "Geek Fam ID" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "RRQ" }.first().id,
            mlbb.id,
            "RRQ Akira",
            orgs.filter { it.name == "RRQ" }.first().logo,
            "Cabral",
            "2022",
            "RRQ Akira is a Brazilian Mobile Legends team under RRQ, a multi-gaming organization based in Indonesia.",
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Bigetron" }.first().id,
            mlbb.id,
            "Bigetron Sons",
            orgs.filter { it.name == "Bigetron" }.first().logo,
            "Daarkness",
            "2023",
            "Bigetron Sons is an Brazilian professional Mobile Legends team under Bigetron Esports, a multi-gaming organization based in Indonesia.",
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "HomeBois" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "HomeBois" }.first().name,
            orgs.filter { it.name == "HomeBois" }.first().logo,
            "Pabz",
            "2020",
            orgs.filter { it.name == "HomeBois" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Team Flash" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Team Flash" }.first().name,
            orgs.filter { it.name == "Team Flash" }.first().logo,
            "Kayzeepi",
            "2018",
            orgs.filter { it.name == "Team Flash" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "See You Soon" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "See You Soon" }.first().name,
            orgs.filter { it.name == "See You Soon" }.first().logo,
            "Cattt",
            "2020",
            orgs.filter { it.name == "See You Soon" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Triple Esports" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Triple Esports" }.first().name,
            orgs.filter { it.name == "Triple Esports" }.first().logo,
            "",
            "2023",
            orgs.filter { it.name == "Triple Esports" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Deus Vult" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Deus Vult" }.first().name,
            orgs.filter { it.name == "Deus Vult" }.first().logo,
            "SAWO",
            "2019",
            orgs.filter { it.name == "Deus Vult" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Fire Flux Esports" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Fire Flux Esports" }.first().name,
            orgs.filter { it.name == "Fire Flux Esports" }.first().logo,
            "Badgalseph",
            "2023",
            orgs.filter { it.name == "Fire Flux Esports" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "TheOhioBrothers" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "TheOhioBrothers" }.first().name,
            orgs.filter { it.name == "TheOhioBrothers" }.first().logo,
            "",
            "2023",
            orgs.filter { it.name == "TheOhioBrothers" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Burmese Ghouls" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Burmese Ghouls" }.first().name,
            orgs.filter { it.name == "Burmese Ghouls" }.first().logo,
            "LynnXDaddy",
            "2016",
            orgs.filter { it.name == "Burmese Ghouls" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Imperio" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Imperio" }.first().name,
            orgs.filter { it.name == "Imperio" }.first().logo,
            "El Viejo",
            "2023",
            orgs.filter { it.name == "Imperio" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Team SMG" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Team SMG" }.first().name,
            orgs.filter { it.name == "Team SMG" }.first().logo,
            "Pao",
            "2020",
            orgs.filter { it.name == "Team SMG" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Umbrella Squad" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Umbrella Squad" }.first().name,
            orgs.filter { it.name == "Umbrella Squad" }.first().logo,
            "EvilKing",
            "2023",
            orgs.filter { it.name == "Umbrella Squad" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "4Merical Esports" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "4Merical Esports" }.first().name,
            orgs.filter { it.name == "4Merical Esports" }.first().logo,
            "Yabe",
            "2023",
            orgs.filter { it.name == "4Merical Esports" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Niightmare Esports" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Niightmare Esports" }.first().name,
            orgs.filter { it.name == "Niightmare Esports" }.first().logo,
            "",
            "2020",
            orgs.filter { it.name == "Niightmare Esports" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Team Lilgun" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Team Lilgun" }.first().name,
            orgs.filter { it.name == "Team Lilgun" }.first().logo,
            "",
            "2023",
            orgs.filter { it.name == "Team Lilgun" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "Team Falcons" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "Team Falcons" }.first().name,
            orgs.filter { it.name == "Team Falcons" }.first().logo,
            "",
            "2023",
            orgs.filter { it.name == "Team Falcons" }.first().description,
            1
        ))

        teams.add(Team(
            UUID.randomUUID().toString(),
            orgs.filter { it.name == "KeepBest Gaming" }.first().id,
            mlbb.id,
            orgs.filter { it.name == "KeepBest Gaming" }.first().name,
            orgs.filter { it.name == "KeepBest Gaming" }.first().logo,
            "Webby",
            "2023",
            orgs.filter { it.name == "KeepBest Gaming" }.first().description,
            1
        ))

        teams.forEach {
            it.insertOrUpdate()
        }
    }
}