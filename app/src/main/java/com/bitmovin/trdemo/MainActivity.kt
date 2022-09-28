package com.bitmovin.trdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bitmovin.player.api.Player
import com.bitmovin.player.api.source.SourceConfig
import com.bitmovin.trdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var player: Player
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializePlayer()
    }

    override fun onStart() {
        super.onStart()
        binding.playerView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.playerView.onResume()
    }

    override fun onPause() {
        binding.playerView.onPause()
        super.onPause()
    }

    override fun onStop() {
        binding.playerView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        binding.playerView.onDestroy()
        super.onDestroy()
    }

    private fun initializePlayer() {
        player = Player.create(this).also { binding.playerView.player = it }
        player.load(SourceConfig.fromUrl(TR_VID_URL))
    }
}

private const val TR_VID_URL =
    "https://ajo.prod.reuters.tv/rest/v2/playlist/assets/67320,533577,533505,533495,57445,58015,533578,127691,533501,58131,533496,58145,533484,127814,533370,127695,533387,127740,533587,127815,533569,58146,533395,58132,533310,127786,533497,58048,533543,57979,533582,533389,58030,533344,318503/master.m3u8"
