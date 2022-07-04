package com.application.pepul.ui.activity

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import com.application.pepul.R
import com.application.pepul.databinding.ActivityViewsBinding
import com.application.pepul.util.Constants
import com.application.pepul.util.viewBinding
import com.application.pepul.view.VideoViewControl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.IOException

class ViewDetailsActivity : BaseActivity(), SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoViewControl.MediaPlayerControl {

    val binding:ActivityViewsBinding by viewBinding()

    var url = ""
    var urlType = ""
    var player: MediaPlayer? = null
    var controller: VideoViewControl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_views)
        initView()
        setOnClickListener()
    }

    private fun initView() {
        if (intent != null) {
            url = intent.getStringExtra(Constants.URL)!!
            urlType = intent.getStringExtra(Constants.URL_TYPE)!!

            if(urlType.isNotEmpty() && url.isNotEmpty()){
                if (urlType.equals("0")){
                    binding.imgView.visibility = View.VISIBLE
                    Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.imgView)
                }else{
                    binding.videoSurfaceContainer.visibility = View.VISIBLE
                    videoView(url)
                }
            }
        }

        binding.hlHeader.imgBack.visibility = View.VISIBLE
        binding.hlHeader.imgPlus.visibility = View.GONE
        binding.hlHeader.txtHeader.text = getString(R.string.app_name)
    }

    private fun videoView(url: String) {
        val videoHolder: SurfaceHolder = binding.videoSurface.holder
        videoHolder.addCallback(this)
        player = MediaPlayer()
        player!!.setOnPreparedListener(this)
        controller = VideoViewControl(this)
        try {
            player!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            player!!.setDataSource(this,
                Uri.parse(url))
            player!!.setOnPreparedListener(this)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        controller!!.show()
        return false
    }

    private fun setOnClickListener() {
        binding.hlHeader.imgBack.setOnClickListener {
            finish()
        }
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        player!!.setDisplay(p0)
        player!!.prepareAsync()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
    }

    override fun onPrepared(p0: MediaPlayer?) {
        controller!!.setMediaPlayer(this)
        controller!!.setAnchorView(binding.videoSurfaceContainer)
        player!!.start()
    }

    override fun start() {
        player!!.start();
    }

    override fun pause() {
        player!!.pause()
    }

    override fun getDuration(): Int {
        return player!!.getDuration()
    }

    override fun getCurrentPosition(): Int {
        return player!!.getCurrentPosition()
    }

    override fun seekTo(pos: Int) {
        player!!.seekTo(pos)
    }

    override fun isPlaying(): Boolean {
        return player!!.isPlaying()
    }

    override fun getBufferPercentage(): Int {
        return 0
    }

    override fun canPause(): Boolean {
        return true
    }

    override fun canSeekBackward(): Boolean {
        return true
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun isFullScreen(): Boolean {
        return false
    }

    override fun toggleFullScreen() {
    }

}