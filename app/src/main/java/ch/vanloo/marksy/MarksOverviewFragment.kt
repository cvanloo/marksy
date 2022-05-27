package ch.vanloo.marksy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ch.vanloo.marksy.databinding.MarksOverviewFragmentBinding

class MarksOverviewFragment : Fragment(R.layout.marks_overview_fragment) {
    private lateinit var binding: MarksOverviewFragmentBinding
    private lateinit var marksAdapter: MarksAdapter

    private val marks = listOf(
        Mark(5.5f, 1.0f, "NPS Kinematik"),
        Mark(6.0f, 1.0f, "Mathe Vektorengeometrie"),
        Mark(5.6f, 1.0f, "English Listening & Grammar"),
        Mark(6.0f, 1.0f, "English Reading & Grammar"),
        Mark(6.0f, 0.33f, "English Voci"),
        Mark(6.0f, 0.33f, "English Voci"),
        Mark(5.9f, 0.33f, "English Voci"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MarksOverviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addDataSet()
    }

    private fun initRecyclerView() {
        binding.recyclerViewMarks.apply {
            marksAdapter = MarksAdapter(context, marks)
            adapter = marksAdapter
            layoutManager = LinearLayoutManager(this@MarksOverviewFragment.context)
        }
    }

    private fun addDataSet() {
        marksAdapter.submitList(marks)
    }
}