package proyek.andro.userActivity.TournamentExtension

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.UncontainedCarouselStrategy
import proyek.andro.R
import proyek.andro.adapter.ParticipantsAdapter
import proyek.andro.model.Participant

class OverviewFr : Fragment() {
    private val participants : ArrayList<Participant> = ArrayList()
    lateinit var participantsRV : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        participantsRV = view.findViewById(R.id.carousel_participants)
        participantsRV.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))
        participants.add(Participant("A1", "EXL", "C9", "A", 1, 3))

        participantsRV.adapter = ParticipantsAdapter(participants)

//        participantsRV.adapter?.notifyDataSetChanged()
    }

}