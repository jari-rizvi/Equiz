package com.teamx.equiz.ui.fragments.news


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.BR
import com.teamx.equiz.MainApplication
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.newsData.NewsDataX
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentNewsBinding
import com.teamx.equiz.databinding.FragmentNewsDetailBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding, NewsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_news_detail
    override val viewModel: Class<NewsViewModel>
        get() = NewsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private var id: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        val bundle = arguments
        if (bundle != null) {
            id = bundle.getString("id").toString()

        }

        id?.let { mViewModel.getnewsdetails(it) }
        if (!mViewModel.getnewsdetailsResponse.hasActiveObservers()) {
            mViewModel.getnewsdetailsResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            val o = data.data.createdAt.toString().replaceAfter('T', "").replace("T", "")
                            val time = data.data.updatedAt.toString().replaceAfter('T', "").replace("T", "")

                            mViewDataBinding.details.text = data.data.description
                            mViewDataBinding.date.text = o
                            mViewDataBinding.text1.text = data.data.title
                            mViewDataBinding.time.text = time
                            Glide.with(requireContext()).load(data.data.image).into(mViewDataBinding.img);

                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.getnewsdetailsResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }


    }


}