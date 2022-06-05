package ercanduman.android.audiorecorder.ui.main.recording.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    // Requesting permission to RECORD_AUDIO
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        with(binding) {
            buttonShowRecordings.setOnClickListener {
                viewModel.onShowRecordingsClicked()
            }

            buttonStartStopRecording.setOnClickListener {
                val isPermissionGranted =
                    ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED

                if (isPermissionGranted) {
                    viewModel.onStartStopRecordingClicked()
                } else {
                    requestMicrophonePermission()
                }
            }
        }
    }

    private fun observeViewModel() {
        safeCollectWithRepeatOnLifecycle {
            viewModel.uiState.collect { state ->
                if (state.onNavigateClicked) {
                    navigateToRecordings()
                    viewModel.onNavigationProcessed()
                }

                state.snackbarMessages.firstOrNull()?.let {
                    displaySnackbarMessage(it.message)
                    viewModel.onSnackbarMessageProcessed(it.id)
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

    @Suppress("DEPRECATION")
    private fun requestMicrophonePermission() {
        requestPermissions(
            permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionRequestAccepted =
            requestCode == REQUEST_RECORD_AUDIO_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED
        if (permissionRequestAccepted) {
            viewModel.onStartStopRecordingClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}
