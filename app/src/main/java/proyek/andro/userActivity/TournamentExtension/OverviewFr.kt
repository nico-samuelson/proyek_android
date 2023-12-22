package proyek.andro.userActivity.TournamentExtension

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.UncontainedCarouselStrategy
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.R
import proyek.andro.adapter.ParticipantsAdapter
import proyek.andro.model.Participant
import proyek.andro.model.Team
import proyek.andro.userActivity.TournamentPage

class OverviewFr : Fragment() {
    private var participants: ArrayList<Participant> = ArrayList()
    private var teams: ArrayList<Team> = ArrayList()
    private lateinit var parent: TournamentPage
    lateinit var participantsRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parent = super.requireActivity() as TournamentPage

        participantsRV = view.findViewById(R.id.carousel_participants)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        participantsRV.layoutManager = staggeredGridLayoutManager

        val participantAdapter = ParticipantsAdapter(parent.getParticipants(), parent.getTeams())
        participantsRV.adapter = participantAdapter

//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
//        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))

//        participantsRV.adapter = ParticipantsAdapter(participants, teams)

//        participantsRV.adapter?.notifyDataSetChanged()
    }
}