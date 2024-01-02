package com.teamx.equiz.ui.fragments.news


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.newsData.NewsDataX
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentNewsBinding
import com.teamx.equiz.ui.fragments.news.adapter.OnRecentNewsListener
import com.teamx.equiz.ui.fragments.news.adapter.OnUpNewsListener
import com.teamx.equiz.ui.fragments.news.adapter.RecentNewsAdapter
import com.teamx.equiz.ui.fragments.news.adapter.UpComingNewsAdapter
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(), onNewslistner,
    OnUpNewsListener, OnRecentNewsListener {

    override val layoutId: Int
        get() = R.layout.fragment_news
    override val viewModel: Class<NewsViewModel>
        get() = NewsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var newsAdapterupcoming: UpComingNewsAdapter
    lateinit var newsArrayListupcoming: ArrayList<NewsDataX>

    lateinit var newsAdaptercurrent: NewsAdapter
    lateinit var newsArrayListcurrent: ArrayList<NewsDataX>


    lateinit var newsAdapterrecents: RecentNewsAdapter
    lateinit var newsArrayListrecents: ArrayList<NewsDataX>

    private lateinit var options: NavOptions


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

        mViewDataBinding.btnback.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewModel.getCurrentNews(true)
        mViewModel.getRecentsNews(true)
        mViewModel.getUpcomingNews(true)



        recentsRecyclerview()
        currentRecyclerview()
        recylerupcomingnews()
        apiCall()
    }


    fun apiCall() {
        if (!mViewModel.getCurrentNewsResponse.hasActiveObservers()) {
            mViewModel.getCurrentNewsResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
                        mViewDataBinding.shimmerLayout.startShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE

                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            mViewDataBinding.shimmerLayout.stopShimmer()
                            mViewDataBinding.shimmerLayout.visibility = View.GONE
                            mViewDataBinding.mainLayout.visibility = View.VISIBLE

                            newsArrayListcurrent.clear()
                            newsArrayListcurrent.addAll(data.newsData)

                            newsAdaptercurrent.notifyDataSetChanged()
                            if (newsArrayListcurrent.isEmpty()) {
//                                mViewDataBinding.textView1.visibility = View.GONE
//                                mViewDataBinding.recylertodaysnews.visibility = View.GONE
                            }

                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
                    }
                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getCurrentNewsResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        if (!mViewModel.getUpcomingnewsResponse.hasActiveObservers()) {
            mViewModel.getUpcomingnewsResponse.observe(requireActivity()) {
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

                            newsArrayListupcoming.clear()
                            newsArrayListupcoming.addAll(data.newsData)

                            newsAdapterupcoming.notifyDataSetChanged()

                            if (newsArrayListupcoming.isEmpty()) {
//                                mViewDataBinding.textView12.visibility = View.GONE
//                                mViewDataBinding.recylerupcomingnews.visibility = View.GONE
                            }

                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
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
                    mViewModel.getUpcomingnewsResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        if (!mViewModel.getRecentNewsResponse.hasActiveObservers()) {
            mViewModel.getRecentNewsResponse.observe(requireActivity()) {
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

                            newsArrayListrecents.clear()
                            newsArrayListrecents.addAll(data.newsData)

                            newsAdapterrecents.notifyDataSetChanged()
                            if (newsArrayListrecents.isEmpty()) {
//                                mViewDataBinding.textView122.visibility = View.GONE
//                                mViewDataBinding.recylerrecentnews.visibility = View.GONE
                            }

                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
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
                    mViewModel.getRecentNewsResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }
    }

    private fun recentsRecyclerview() {
        newsArrayListrecents = ArrayList()

        val linearLayoutManager1 = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.recylerrecentnews.layoutManager = linearLayoutManager1

        newsAdapterrecents = RecentNewsAdapter(newsArrayListrecents, this)
        mViewDataBinding.recylerrecentnews.adapter = newsAdapterrecents

    }

    private fun currentRecyclerview() {
        newsArrayListcurrent = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.recylertodaysnews.layoutManager = linearLayoutManager

        newsAdaptercurrent = NewsAdapter(newsArrayListcurrent,this)
        mViewDataBinding.recylertodaysnews.adapter = newsAdaptercurrent

    }

    private fun recylerupcomingnews() {
        newsArrayListupcoming = ArrayList()

        val linearLayoutManager2 = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.recylerupcomingnews.layoutManager = linearLayoutManager2

        newsAdapterupcoming = UpComingNewsAdapter(newsArrayListupcoming, this)
        mViewDataBinding.recylerupcomingnews.adapter = newsAdapterupcoming

    }

    override fun newsclick(position: Int) {
        if (newsArrayListcurrent.isNotEmpty()) {

            var id = newsArrayListcurrent[position]._id
            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putString("id", id)
            findNavController().navigate(
                R.id.action_newsFragment_to_newsDetailFragment,
                bundle,
                options
            )
        }
    }

    override fun newsClick(position: Int) {
        if (newsArrayListrecents.isNotEmpty()) {

            var id = newsArrayListrecents[position]._id
            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putString("id", id)
            findNavController().navigate(
                R.id.action_newsFragment_to_newsDetailFragment,
                bundle,
                options
            )
        }
    }

    override fun newsUpClick(position: Int) {
        if (newsArrayListupcoming.isNotEmpty()) {

            var id = newsArrayListupcoming[position]._id
            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putString("id", id)
            findNavController().navigate(
                R.id.action_newsFragment_to_newsDetailFragment,
                bundle,
                options
            )
        }
    }

}