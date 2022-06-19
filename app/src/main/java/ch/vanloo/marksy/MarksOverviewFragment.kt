package ch.vanloo.marksy

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.vanloo.marksy.databinding.MarksOverviewFragmentBinding
import ch.vanloo.marksy.entity.Mark

class MarksOverviewFragment : Fragment(R.layout.marks_overview_fragment),
    MarksAdapter.ItemClickListener {
    private lateinit var binding: MarksOverviewFragmentBinding
    private lateinit var marksAdapter: MarksAdapter

    private var state: Parcelable? = null

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
            state = binding.recyclerViewMarks.layoutManager?.onSaveInstanceState()
            marks?.let {
                marksAdapter.submitList(it)
                // @FIXME: `submitList` runs on its own thread and will only finish *after* the
                //   state has been restored, effectively making the restore useless.
                //binding.recyclerViewMarks.layoutManager?.onRestoreInstanceState(state)
            }
        }
    }

    override fun onItemClick(mark: Mark) {
        val intent = Intent(requireContext(), MarkDetailsActivity::class.java)
        intent.putExtra(MarkDetailsActivity.MARK_ID, mark.Uid)
        startActivity(intent)
    }

    override fun onListUpdated() {
        // @FIXME: This is a terrible solution. Please tell me there is a better way to do this!
        binding.recyclerViewMarks.layoutManager?.onRestoreInstanceState(state)
    }
}