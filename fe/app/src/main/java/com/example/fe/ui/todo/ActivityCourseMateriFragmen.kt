package com.example.fe.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fe.courseTopic
import com.example.fe.databinding.FragmentActivityCourseMateriBinding
import com.example.fe.materialTopic
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.regex.Pattern

class ActivityCourseMateriFragmen : Fragment() {

    private var _binding: FragmentActivityCourseMateriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityCourseMateriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiSetup()

        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoUrl = "https://www.youtube.com/watch?v=aqz-KE-bpKQ"//materialTopic!!.video_url
                val videoId = extractYoutubeId(videoUrl)

                if (!videoId.isNullOrEmpty()) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }
        })
    }

    fun uiSetup(){
        binding.txtDetailTitle.setText(courseTopic?.title ?: "No Title")
        binding.txtDetailDesc.setText(materialTopic?.attachment_file ?: "No Attachment")
    }

    private fun extractYoutubeId(ytUrl: String?): String? {
        if (ytUrl.isNullOrEmpty()) return null

        return when {
            ytUrl.contains("watch?v=") ->
                ytUrl.substringAfter("watch?v=").substringBefore("&")

            ytUrl.contains("youtu.be/") ->
                ytUrl.substringAfter("youtu.be/").substringBefore("?")

            ytUrl.contains("embed/") ->
                ytUrl.substringAfter("embed/").substringBefore("?")

            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}