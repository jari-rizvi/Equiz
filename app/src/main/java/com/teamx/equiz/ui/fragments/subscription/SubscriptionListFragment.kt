package com.teamx.equiz.ui.fragments.subscription


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentSubscriptionListBinding
import com.teamx.equiz.ui.fragments.subscription.catPlansData.Data
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionListFragment : BaseFragment<FragmentSubscriptionListBinding, SubscriptionViewModel>(),onSubsClick{

    override val layoutId: Int
        get() = R.layout.fragment_subscription_list
    override val viewModel: Class<SubscriptionViewModel>
        get() = SubscriptionViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var subsAdapter: SubscriptionAdapter

    lateinit var subsArrayList: ArrayList<Data>

    private lateinit var options: NavOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        sharedViewModel.setActiveUser("")


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


        mViewModel.getCatPlans(true)

        if (!mViewModel.getCatPlansResponse.hasActiveObservers()) {
            mViewModel.getCatPlansResponse.observe(requireActivity()) {
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
                            Log.d("TAG", "ayaaa: ${it.data}")

                            subsArrayList.clear()
                            data.data.forEach {
                                subsArrayList.add(it)
                            }

                            subsAdapter.notifyDataSetChanged()



                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        if (isAdded) {
                            try {
                                onToSignUpPage()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
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
                    mViewModel.getCatPlansResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        subsRecyclerview()
    }

    private fun subsRecyclerview() {
        subsArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recrecc.layoutManager = linearLayoutManager

        subsAdapter = SubscriptionAdapter(subsArrayList,this)
        mViewDataBinding.recrecc.adapter = subsAdapter

    }

    override fun onSubItemClick(position: Int) {
        var id =subsArrayList[position]._id
        var title =subsArrayList[position].title


        val subscription = subsArrayList[position] // Get the subscription object at the clicked position
        val json = Gson().toJson(subscription)

        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putString("subscription", id)
        bundle.putString("title", title)



        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment
        )
        navController.navigate(R.id.subscriptionFragment, bundle, options)

    }


}