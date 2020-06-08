package top.hanyue.movieflex.content.play

import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videoplayer.player.VideoView
import kotlinx.android.synthetic.main.activity_movie_play.*
import timber.log.Timber
import top.hanyue.movieflex.DataKey
import top.hanyue.movieflex.R
import top.hanyue.movieflex.base.MovieFlexBaseActivity

class MoviePlayActivity : MovieFlexBaseActivity() {

    private var mController: StandardVideoController? = null
    private var adUrl:String = "https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4"
    private var videoUrl:String = "https://youku.cdn2-okzy.com/20200318/8437_6978feb8/index.m3u8"

    override fun layoutResID(): Int {
        return R.layout.activity_movie_play
    }

    override fun initViewAndEvent() {
        videoUrl = intent.getStringExtra(DataKey.DATA_KEY_PLAY_URL)
        playAd()
    }

    private fun playAd(){
        mController = StandardVideoController(this)
        mController?.addDefaultControlComponent("广告", false)
        videoView.setUrl(adUrl)
        videoView.setVideoController(mController)
        videoView.start()
        Timber.d("开始播放广告   %s", adUrl)

        //监听播放结束
        videoView.addOnStateChangeListener(adStateListener)
    }

    private fun playVideo(){
        Timber.d("开始播放正片   %s", videoUrl)

        videoView.removeOnStateChangeListener(adStateListener)
        videoView.release()
        mController?.removeAllControlComponent()
        mController?.addDefaultControlComponent("正片", false)
        videoView.addOnStateChangeListener(videoStateListener)
        //重新设置数据
        videoView.setUrl(videoUrl)
        //开始播放
        videoView.start()
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.release()
    }


    override fun onBackPressed() {
        if (!videoView.onBackPressed()) {
            super.onBackPressed()
        }
    }

    private val adStateListener = object: VideoView.OnStateChangeListener{
        override fun onPlayStateChanged(playState: Int) {
            if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                Timber.d("广告播放完毕")
                playVideo()
            }
        }

        override fun onPlayerStateChanged(playerState: Int) {
            Timber.d("播放器状态改变    %s", playerState)
        }
    }

    private val videoStateListener = object: VideoView.OnStateChangeListener{
        override fun onPlayStateChanged(playState: Int) {
            Timber.d("播放器状态改变    %s", playState)
        }

        override fun onPlayerStateChanged(playerState: Int) {
            Timber.d("播放器状态改变    %s", playerState)
        }
    }
}
