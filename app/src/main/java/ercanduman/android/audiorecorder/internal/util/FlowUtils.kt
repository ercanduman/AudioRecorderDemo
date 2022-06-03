package ercanduman.android.audiorecorder.internal.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minimumActiveState` lifecycle state.
 *
 * For Instance, let's say if minimumActiveState is RESUMED state.
 * When the lifecycle is in the RESUMED state, it launches a new coroutine and repeats block.
 * As there is also respective lifecycle state for Resumed which called Paused state.
 * safeCollectWithRepeatOnLifecycle cancels the running coroutine when the state out which is PAUSED.
 *
 * For more details: https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
 */
fun Fragment.safeCollectWithRepeatOnLifecycle(
    minimumActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minimumActiveState) {
            block()
        }
    }
}

/*
 * Launches a new coroutine and repeats `block` every time the DialogFragment's LifecycleOwner
 * is in and out of `minimumActiveState` lifecycle state.
 *
 * For Instance, let's say if minimumActiveState is RESUMED state.
 * When the lifecycle is in the RESUMED state, it launches a new coroutine and repeats block.
 * As there is also respective lifecycle state for Resumed which called Paused state.
 * safeCollectWithRepeatOnLifecycle cancels the running coroutine when the state out which is PAUSED.
 *
 * For more details: https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
 */
fun DialogFragment.safeCollectWithRepeatOnLifecycle(
    minimumActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(minimumActiveState) {
            block()
        }
    }
}