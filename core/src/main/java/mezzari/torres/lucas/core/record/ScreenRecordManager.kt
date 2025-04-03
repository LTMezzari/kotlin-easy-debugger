package mezzari.torres.lucas.core.record

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver

/**
 * @author Lucas T. Mezzari
 * @since 29/03/25
 **/
interface ScreenRecordManager: DefaultLifecycleObserver {

    fun startRecording(activity: FragmentActivity)

    fun stopRecording()
}