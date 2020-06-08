package top.hanyue.movieflex.utils

import android.content.Context
import android.media.AudioManager
import android.view.View

object SoundUtil {

    fun playClickSound(view: View) {
        if (view.isSoundEffectsEnabled) {
            val manager = view.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            manager.playSoundEffect(AudioManager.FX_KEY_CLICK)
        }
    }
}