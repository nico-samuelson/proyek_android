package proyek.andro.userActivity.TournamentExtension

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import proyek.andro.R
import proyek.andro.adapter.ScheduleUpcomingAdapter
import proyek.andro.model.Tournament

class ScheduleFr : Fragment() {
    private var tournaments : ArrayList<Tournament> = ArrayList()
    lateinit var UpcomingScheduleRV : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UpcomingScheduleRV = view.findViewById(R.id.carousel_schedules)
        UpcomingScheduleRV.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        tournaments.add(Tournament("1", "Valorant", "Tournament 1", "12 June 23", "12/12/2020", 1000000, "EXL", "Online", listOf("Indonesia"), listOf("Indonesia"), "Tournament 1", "logo", "banner", 1))
        tournaments.add(Tournament("1", "Valorant", "Tournament 1", "12 June 23", "12/12/2020", 1000000, "EXL", "Online", listOf("Indonesia"), listOf("Indonesia"), "Tournament 1", "logo", "banner", 1))
        tournaments.add(Tournament("1", "Valorant", "Tournament 1", "12 June 23", "12/12/2020", 1000000, "EXL", "Online", listOf("Indonesia"), listOf("Indonesia"), "Tournament 1", "logo", "banner", 1))
        tournaments.add(Tournament("1", "Valorant", "Tournament 1", "12 June 23", "12/12/2020", 1000000, "EXL", "Online", listOf("Indonesia"), listOf("Indonesia"), "Tournament 1", "logo", "banner", 1))
        tournaments.add(Tournament("1", "Valorant", "Tournament 1", "12 June 23", "12/12/2020", 1000000, "EXL", "Online", listOf("Indonesia"), listOf("Indonesia"), "Tournament 1", "logo", "banner", 1))

        UpcomingScheduleRV.adapter = ScheduleUpcomingAdapter(tournaments)
    }
}