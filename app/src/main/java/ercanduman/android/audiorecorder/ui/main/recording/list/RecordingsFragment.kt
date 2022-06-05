package ercanduman.android.audiorecorder.ui.main.recording.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.databinding.FragmentRecordingsBinding
import ercanduman.android.audiorecorder.internal.util.safeCollectWithRepeatOnLifecycle
import ercanduman.android.audiorecorder.ui.main.recording.list.adapter.RecordingsAdapter

@AndroidEntryPoint
class RecordingsFragment : Fragment(), RecordingsAdapter.OnRecordClickListener {
    private val viewModel: RecordingsViewModel by viewModels()

    private var _binding: FragmentRecordingsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var recordingsAdapter: RecordingsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        recordingsAdapter = RecordingsAdapter(this)
        binding.recyclerviewRecordings.apply {
            adapter = recordingsAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        safeCollectWithRepeatOnLifecycle {
            viewModel.records.collect {
                displayRecords(it)
            }
        }
    }

    private fun displayRecords(records: List<Record>) {
        Log.d(TAG, "displayRecords: record list: ${records.size}")
        recordingsAdapter?.submitList(records)
    }

    override fun onRecordClicked(record: Record) {
        Toast.makeText(requireContext(), "${record.name} clicked.", Toast.LENGTH_SHORT).show()
    }

    // Clear instance of fields in order to prevent memory leaks.
    override fun onDestroyView() {
        super.onDestroyView()
        recordingsAdapter = null

        _binding = null
    }

    companion object {
        private const val TAG = "RecordingsFragment"
    }
}