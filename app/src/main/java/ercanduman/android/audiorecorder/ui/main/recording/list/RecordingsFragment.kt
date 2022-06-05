package ercanduman.android.audiorecorder.ui.main.recording.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.android.audiorecorder.R
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.databinding.FragmentRecordingsBinding
import ercanduman.android.audiorecorder.internal.util.safeCollectWithRepeatOnLifecycle
import ercanduman.android.audiorecorder.ui.main.recording.delegate.SnackbarMessage
import ercanduman.android.audiorecorder.ui.main.recording.list.adapter.RecordingsAdapter
import ercanduman.android.audiorecorder.ui.main.recording.list.adapter.SwipeToDeleteTouchHelper

@AndroidEntryPoint
class RecordingsFragment :
    Fragment(),
    RecordingsAdapter.OnRecordClickedListener,
    SwipeToDeleteTouchHelper.OnRecordSwipedListener {

    private val viewModel: RecordingsViewModel by viewModels()

    private var _binding: FragmentRecordingsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var recordingsAdapter: RecordingsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        val swipeToDeleteTouchHelper = ItemTouchHelper(SwipeToDeleteTouchHelper(this))
        binding.recyclerviewRecordings.apply {
            adapter = recordingsAdapter
            setHasFixedSize(true)
            swipeToDeleteTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun observeViewModel() {
        safeCollectWithRepeatOnLifecycle {
            viewModel.records.collect {
                displayRecords(it)
            }

            viewModel.uiState.collect { state ->
                state.snackbarMessages.firstOrNull()?.let {
                    displaySnackbarMessage(it)
                    viewModel.onSnackbarMessageProcessed(it.id)
                }
            }
        }
    }

    private fun displaySnackbarMessage(snackbarMessage: SnackbarMessage) {
        val snackbar = Snackbar.make(binding.root, snackbarMessage.message, Snackbar.LENGTH_LONG)

        snackbarMessage.undoCallback?.let { callback ->
            snackbar.setAction(getString(R.string.undo)) {
                callback.undoClicked()
            }
        }

        snackbar.show()
    }

    private fun displayRecords(records: List<Record>) {
        recordingsAdapter?.submitList(records)
    }

    override fun onRecordClicked(record: Record) {
        viewModel.onPlayPauseRecordClicked(record.path)
    }

    override fun onRecordSwiped(position: Int) {
        recordingsAdapter?.getRecordAt(position)?.let {
            viewModel.onSwipeToDelete(it)
        }
    }

    // Clear instance of fields in order to prevent memory leaks.
    override fun onDestroyView() {
        super.onDestroyView()
        recordingsAdapter = null

        _binding = null
    }
}
