package proyek.andro.seeder

import proyek.andro.model.Organization
import java.util.UUID

class OrganizationSeeder {
    var orgs = ArrayList<Organization>()

    suspend fun run() {
        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Team Liquid",
            "Team Liquid is a professional team, founded in the Netherlands in 2000. Originally a Brood War clan, the team switched to StarCraft II during the SC2 Beta in 2010, and became one of the most successful foreign teams. On January 13, 2015, it was announced that the team had formed a Counter-Strike: Global Offensive division.",
            "logo_team_liquid.png",
            "2000",
            "Netherlands",
            "https://www.teamliquid.com/",
            "Victor Goossens",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Evil Geniuses",
            "Evil Geniuses (EG) is an esports organization based in the United States. Founded in 1999, the organization is known as being the premiere North American electronic sports organization, boasting highly successful players across each competitive genre. Evil Geniuses was also considered as one of the best Quake clans in the late 90s and early 2000s.",
                "logo_evil_geniuses.png",
            "1999",
            "United States",
            "https://www.evilgeniuses.gg/",
            "Nicole LaPointe Jameson",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "NRG",
            "NRG Esports is an American esports organization, formed after co-owners of the NBA's Sacramento Kings purchased the LCS spot of Team Coast's League of Legends team. The team was first seen in the promotion tournament, where they easily swept Team Coast's academy team Cloud9 Tempest, qualifying for the Summer Split.",
            "logo_nrg.png",
            "2015",
            "United States",
            "https://www.nrg.gg/",
            "Andy Miller",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "LOUD",
            "LOUD is a Brazilian esports organization. They currently field teams in Clash Royale, Free Fire, League of Legends, and Brawl Stars.",
            "logo_loud.png",
            "2019",
            "Brazil",
            "https://www.loud.gg/",
            "Bruno Playhard",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "KRÜ Esports",
            "KRÜ Esports is a Brazilian esports organization. They currently field teams in Clash Royale, Free Fire, League of Legends, and Brawl Stars.",
            "logo_kru_esports.png",
            "2019",
            "Brazil",
            "https://www.kru.gg/",
            "Bruno Playhard",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Fnatic",
            "Fnatic is a professional esports organization consisting of players from around the world across a variety of games. On March 14, 2011, Fnatic entered the League of Legends scene with the acquisition of myRevenge. Fnatic is one of the strongest European teams since the early days of competitive League of Legends, having been the champion of the Riot Season 1 Championship.",
            "logo_fnatic.png",
            "2004",
            "United Kingdom",
            "https://fnatic.com/",
            "Sam Mathews",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "FUT Esports",
            "FUT Esports (formerly known as Futbolist) is a Turkish esports organisation who entered VALORANT in October 2020 after signing LOL-. They have teams in PUBG, Hearthstone, FIFA and Wildrift.",
            "logo_fut_esports.png",
            "2017",
            "Turkey",
            "https://www.futesports.com/",
            "Sinan Dursunoglu",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Giants Gaming",
            "Giants Gaming is a Spanish esports organization with teams in League of Legends, Call of Duty, Rocket League, Rainbow Six, and FIFA. They were previously known as x6tence.",
            "logo_giants.png",
            "2008",
            "Spain",
            "https://www.giantsgaming.pro/",
            "Jose Ramon Díaz",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Paper Rex",
            "Paper Rex is a Singaporean esports organization. They currently field teams in Counter-Strike: Global Offensive, VALORANT, and PUBG Mobile.",
            "logo_prx.png",
            "2017",
            "Singapore",
            "https://www.paparex.gg/",
            "Nikhil Hathiramani",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "DRX",
            "DRX is a Korean esports organization. They currently field teams in League of Legends, PUBG, and VALORANT.",
            "logo_drx.png",
            "2019",
            "South Korea",
            "https://drx.gg/",
            "Yang Sun-il",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "T1",
            "T1 is a global esports joint venture created by Comcast Spectacor and SK Telecom that owns and operates teams in League of Legends, Fortnite, Dota 2, PUBG, Super Smash Bros., Hearthstone, Rainbow Six Siege, Apex Legends, Overwatch and Splitgate.",
            "logo_t1.png",
            "2002",
            "South Korea",
            "https://www.t1.gg/",
            "Joe Marsh",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "ZETA DIVISION",
            "ZETA DIVISION is a Japanese esports organization. They currently field teams in League of Legends, VALORANT, and PUBG.",
            "logo_zeta.png",
            "2017",
            "Japan",
            "https://www.zeta-division.jp/",
            "Daisuke Nishihara",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "EDward Gaming",
            "EDward Gaming is a Chinese esports organization. They currently field teams in League of Legends, PUBG, and VALORANT.",
            "logo_edg.png",
            "2013",
            "China",
            "https://www.edward.cn/",
            "Edward Zhu",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Bilibili Gaming",
            "Bilibili Gaming is a Chinese esports organization. They currently field teams in League of Legends, PUBG, and VALORANT.",
            "logo_bilibili.png",
            "2017",
            "China",
            "https://www.blg.com.cn/",
            "Chen Rui",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "FunPlus Phoenix",
            "FunPlus Phoenix is a Chinese esports organization. They currently field teams in League of Legends, PUBG, and VALORANT.",
            "logo_fpx.png",
            "2017",
            "China",
            "https://www.funplus.com/",
            "Andy Zhong",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Natus Vincere",
            "Natus Vincere (Latin: \"born to win\", often abbreviated as Na'Vi or NaVi) is a leading multi-game esports organization from Ukraine. It is the first team in Counter-Strike history to win three major tournaments in one calendar year - Intel Extreme Masters, Electronic Sports World Cup and World Cyber Games 2010.",
            "logo_navi.png",
            "2009",
            "Ukraine",
            "https://navi.gg/",
            "Yevhen Zolotarov",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "AP.Bren",
            "AP.Bren (previously known as Bren Esports) is a Multi-gaming Organization based in the Philippines.",
            "logo_bren.png",
            "2018",
            "Philippines",
            "https://brenesports.com/",
            "Bernard Chong",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Blacklist International",
            "Blacklist International (referred to as BLCK) is a Multi-gaming Organization based in Philippines under Tier One Entertainment.",
            "logo_blacklist.png",
            "2020",
            "Philippines",
            "",
            "Tryke Gutierrez",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "ONIC Esports",
            "ONIC Esports is a Multi-gaming Organization based in Indonesia.",
            "logo_onic.png",
            "2018",
            "Indonesia",
            "",
            "Justin Widjaja",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Geek Fam ID",
            "Geek Fam, the leading Malaysia esports organisation, based in Kuala Lumpur, Malaysia was founded by Keat and Joseph in 2016. Geek Fam has been rising, well on its way to becoming one of the prominent names in the competitive arena of Asia’s esports scene. The team started off their journey by sending their Dota2 team to the ESL One Genting, making it into the finals of the Phase 2 Qualifiers.",
            "logo_geek_fam.png",
            "2016",
            "Malaysia",
            "",
            "Joseph Yeoh",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "RRQ",
            "Rex Regum Qeon (RRQ) is an Indonesian esports organization. They currently field teams in League of Legends, PUBG, and VALORANT.",
            "logo_rrq.png",
            "2013",
            "Indonesia",
            "https://www.teamrrq.com/",
            "Andrian Pauline",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Bigetron",
            "Bigetron Esports is a multi-gaming organization based in Indonesia.",
            "logo_btr.png",
            "2017",
            "Indonesia",
            "https://www.bigetron.gg/",
            "Edwin Chia",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "HomeBois",
            "HomeBois is part of the Team Bosskurr family and was originally a production team. HomeBois also have a team for the PUBG Mobile division.",
            "logo_homebois.png",
            "2020",
            "Malaysia",
            "",
            "Firdaus Hashim",
            1
        ))

        orgs.add(
            Organization(
                UUID.randomUUID().toString(),
                "Team Flash",
                "Team Flash is a Singaporean organization. They currently field teams in League of Legends, Hearthstone, FIFA, Dota 2, Free Fire, Wild Rift and Arena of Valor.",
                "logo_team_flash.png",
                "2018",
                "Singapore",
                "https://teamflash.gg/",
                "Terence Ting",
                1
            )
        )

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "See You Soon",
            "See You Soon is a Cambodian organization",
            "logo_sys.png",
            "2020",
            "Cambodia",
            "",
            "Sokunphareaktra Phan",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Triple Esports",
            "Triple Esports is a Saudi Arabian esports organization.",
            "logo_triple_esports.png",
            "2021",
            "Saudi Arabia",
            "",
            "Abdulrahman Mansour Fakhri",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Deus Vult",
            "DeusVult is a Russian team. The team was previously signed by Team Unique and played under the name Unique Devu during the M2 World Championship. Later on, it was signed by Natus Vincere for the M3 World Championship.",
            "logo_deus_vult.png",
            "2019",
            "Russia",
            "",
            "Mikhail Voronov",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Fire Flux Esports",
            "Fire Flux Esports is a Turkish professional esports organization.",
            "logo_fire_flux.png",
            "2023",
            "Turkey",
            "",
            "Temuçin Ünalp",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "TheOhioBrothers",
            "TheOhioBrothers is an American team. The team was previously signed by Outplay.",
            "logo_theohiobrothers.png",
            "2023",
            "Russia",
            "",
            "",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Burmese Ghouls",
            "Burmese Ghouls (often abbreviated as BG) was a professional Mobile Legends team from Myanmar formerly operating under Burmese Ghouls Esports.",
            "logo_burmese_ghouls.png",
            "2016",
            "Myanmar",
            "",
            "",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Imperio",
            "Imperio is an Argentine esports team.",
            "logo_imperio.png",
            "2023",
            "Argentina",
            "",
            "",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Team SMG",
            "Team SMG is an esports organization in Asia. Along with PUBG Mobile, Team SMG also competes in Mobile Legends in Malaysia, Peacekeeper Elite and PUBG in China and Valorant, Mobile Legends in Singapore.",
            "logo_team_smg.png",
            "2017",
            "Malaysia",
            "https://www.teamsmg.gg/",
            "JJ Lin",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Umbrella Squad",
            "Umbrella Squad is an American esports organization. They are currently held a MLBB team in CIS.",
            "logo_umbrella_squad.png",
            "2023",
            "United States",
            "",
            "",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "4Merical Esports",
            "4Merical Esports is a Nepalese Esports Organization.",
            "logo_4merical.png",
            "2023",
            "Nepal",
            "",
            "",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Niightmare Esports",
            "Niightmare Esports is an Laotian esports team.",
            "logo_niightmare_esports.png",
            "2020",
            "Laos",
            "",
            "",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Team Lilgun",
            "Team Lilgun is a Mongolian esport organization.",
            "logo_team_lilgun.png",
            "2021",
            "Mongolia",
            "",
            "Ankhbayar Narantsatsral\t",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "Team Falcons",
            "Team Falcons (also known as Falcons Esports) is a Saudi Arabian esports organization which is currently hosting competitive teams in Fortnite, Call of Duty, PUBG, PUBGM, and Rocket League.",
            "logo_team_falcons.png",
            "2017",
            "Saudi Arabia",
            "",
            "Muhammed Motlaq Almutairi",
            1
        ))

        orgs.add(Organization(
            UUID.randomUUID().toString(),
            "KeepBest Gaming",
            "KeepBest Gaming (also known as KBG) is a Chinese professional esports organization.",
            "logo_kbg.png",
            "2023",
            "China",
            "",
            "",
            1
        ))

        orgs.forEach {
            it.insertOrUpdate()
        }
    }
}