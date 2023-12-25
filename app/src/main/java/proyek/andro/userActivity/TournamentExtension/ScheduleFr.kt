package proyek.andro.userActivity.TournamentExtension

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.ScheduleUpcomingAdapter
import proyek.andro.model.Match
import proyek.andro.model.Participant
import proyek.andro.model.Team
import proyek.andro.model.Tournament
import proyek.andro.userActivity.TournamentPage

class ScheduleFr : Fragment() {
    private var tournaments : ArrayList<Tournament> = ArrayList()
    private var participants : ArrayList<Participant> = ArrayList()
    private var teams : ArrayList<Team> = ArrayList()
    lateinit var UpcomingScheduleRV : RecyclerView
    lateinit var parent : TournamentPage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parent = super.requireActivity() as TournamentPage

        UpcomingScheduleRV = view.findViewById(R.id.carousel_schedules)
        UpcomingScheduleRV.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

//        val sortedMatches = ArrayList(parent.getMatches().sortedBy { it.time })

        UpcomingScheduleRV.adapter = ScheduleUpcomingAdapter(
            parent.getMatches(),
            parent.getTeams())
    }
}