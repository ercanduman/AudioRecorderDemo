package ercanduman.android.audiorecorder.ui.main.recording.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.android.audiorecorder.R
import ercanduman.android.audiorecorder.databinding.FragmentHomeBinding
import ercanduman.android.audiorecorder.internal.util.safeCollectWithRepeatOnLifecycle

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        observeViewModel()
    }

    private fun setListener() {
        binding.buttonShowRecordings.setOnClickListener {
            viewModel.onShowRecordingsClicked()
        }
    }

    private fun observeViewModel() {
        safeCollectWithRepeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.homeUiState.collect { state ->
                if (state.navigateToRecordings) {
                    navigateToRecordings()
                    viewModel.onShowRecordingsProcessed()
                }

                state.snackbarMessages.firstOrNull()?.let {
                    displaySnackbarMessage(it.message)
                    viewModel.onSnackbarMessageShown(it.id)
                }
            }
        }
    }

    private fun navigateToRecordings() {
        findNavController().navigate(R.id.action_HomeFragment_to_RecordingsFragment)
    }

    private fun displaySnackbarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}