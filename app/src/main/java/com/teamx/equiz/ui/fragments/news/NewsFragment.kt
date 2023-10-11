package com.teamx.equiz.ui.fragments.news


import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getwalletData.Transaction
import com.teamx.equiz.data.models.newsdaya.New
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentNewsBinding
import com.teamx.equiz.databinding.FragmentReferralBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.ui.fragments.referral.ReferralViewModel
import com.teamx.equiz.ui.fragments.wallet.WalletAdapter
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_news
    override val viewModel: Class<NewsViewModel>
        get() = NewsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var newsAdapter: NewsAdapter
    lateinit var newsArrayList: ArrayList<New>

    private lateinit var options: NavOptions


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        mViewDataBinding.btnback.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewModel.getNews()

        if (!mViewModel.getnewsResponse.hasActiveObservers()) {
            mViewModel.getnewsResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()

                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            newsArrayList.addAll(data.news)

                            newsAdapter.notifyDataSetChanged()


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getnewsResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        newNewsRecyclerview()
    }

    private fun newNewsRecyclerview() {
        newsArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recylertodaysnews.layoutManager = linearLayoutManager

        newsAdapter = NewsAdapter(newsArrayList)
        mViewDataBinding.recylertodaysnews.adapter = newsAdapter

    }

}