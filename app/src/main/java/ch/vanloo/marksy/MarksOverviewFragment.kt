package ch.vanloo.marksy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.vanloo.marksy.databinding.MarksOverviewFragmentBinding
import ch.vanloo.marksy.db.MarksViewModel
import ch.vanloo.marksy.db.MarksViewModelFactory
import ch.vanloo.marksy.entity.Mark

class MarksOverviewFragment : Fragment(R.layout.marks_overview_fragment),
    MarksAdapter.ItemClickListener {
    private lateinit var binding: MarksOverviewFragmentBinding
    private lateinit var marksAdapter: MarksAdapter

    private val marksViewModel: MarksViewModel by viewModels {
        MarksViewModelFactory((activity?.application as MarksApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = MarksOverviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewMarks.apply {
            marksAdapter = MarksAdapter(context, this@MarksOverviewFragment)
            adapter = marksAdapter
            layoutManager = LinearLayoutManager(this@MarksOverviewFragment.requireContext())
        }

        marksViewModel.allMarks.observe(viewLifecycleOwner) { marks ->
            marks?.let { marksAdapter.submitList(it) }
        }
    }

    override fun onItemClick(mark: Mark) {
        val intent = Intent(requireContext(), MarkDetailsActivity::class.java)
        intent.putExtra(MarkDetailsActivity.MARK_ID, mark.Uid)
        startActivity(intent)
    }
}