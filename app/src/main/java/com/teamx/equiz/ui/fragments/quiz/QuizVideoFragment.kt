package com.teamx.equiz.ui.fragments.quiz

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.PlayQuizVideoLayoutBinding
import com.teamx.equiz.ui.fragments.singlequize.SingleQuizesViewModel
import com.teamx.equiz.ui.fragments.wishlist.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizVideoFragment : BaseFragment<PlayQuizVideoLayoutBinding, SingleQuizesViewModel>() {

    override val layoutId: Int
        get() = com.teamx.equiz.R.layout.play_quiz_video_layout
    override val viewModel: Class<SingleQuizesViewModel>
        get() = SingleQuizesViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    var videoUrl =
        "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            val route = bundle?.getString("routeQuiz")
            if (route.equals("dash", true)) {
                findNavController().popBackStack(com.teamx.equiz.R.id.dashboardFragment, true)
                findNavController().navigate(
                    com.teamx.equiz.R.id.dashboardFragment,
                    arguments,
                    options
                )
            } else {
                findNavController().navigate(
                    com.teamx.equiz.R.id.quizesFragment,
                    arguments,
                    options
                )
            }

        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = com.teamx.equiz.R.anim.enter_from_left
                exit = com.teamx.equiz.R.anim.exit_to_left
                popEnter = com.teamx.equiz.R.anim.nav_default_pop_enter_anim
                popExit = com.teamx.equiz.R.anim.nav_default_pop_exit_anim
            }
        }
        sharedViewModel.setActiveUser("")
//        mViewDataBinding.btnback.setOnClickListener {
//            var bundle = arguments
//            if (bundle == null) {
//                bundle = Bundle()
//            }
//            val route = bundle?.getString("routeQuiz")
//            if (route.equals("dash", true)) {
//                findNavController().popBackStack(com.teamx.equiz.R.id.dashboardFragment, true)
//                findNavController().navigate(
//                    com.teamx.equiz.R.id.dashboardFragment,
//                    arguments,
//                    options
//                )
//            } else {
//                findNavController().navigate(
//                    com.teamx.equiz.R.id.quizesFragment,
//                    arguments,
//                    options
//                )
//            }
//        }

        val bundle = arguments
        val id = bundle?.getString("modelQuizId")

        val videoView: VideoView = mViewDataBinding.videoView
        val uri = Uri.parse(videoUrl)

        videoView.setVideoURI(uri)

//        val mediaController = MediaController(requireContext())
//
//        mediaController.setAnchorView(videoView)
//
//
//        mediaController.setMediaPlayer(videoView)
//
//
//        videoView.setMediaController(mediaController)


        videoView.start()

        mViewDataBinding.skip.setOnClickListener {  }





     /*   if (bundle == null) {
            bundle = Bundle()
        }

        bundle.putString("quiz_id","${modelQuiz._id}")
        bundle.putString("routeQuiz", "quiz")

        findNavController().navigate(R.id.playQuizFragment, bundle,options)*/



    }

}