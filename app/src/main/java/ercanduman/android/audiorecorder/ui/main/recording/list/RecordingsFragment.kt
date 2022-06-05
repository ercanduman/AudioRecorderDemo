package ercanduman.android.audiorecorder.ui.main.recording.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.databinding.FragmentRecordingsBinding
import ercanduman.android.audiorecorder.internal.util.safeCollectWithRepeatOnLifecycle

@AndroidEntryPoint
class RecordingsFragment : Fragment() {
    private val viewModel: RecordingsViewModel by viewModels()

    private var _binding: FragmentRecordingsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecordingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "RecordingsFragment"
    }
}