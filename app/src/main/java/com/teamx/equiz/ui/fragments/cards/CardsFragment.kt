package com.teamx.equiz.ui.fragments.cards

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentCardsBinding
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.OnTopSellerListener
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment : BaseFragment<FragmentCardsBinding, CardsViewModel>(), OnTopSellerListener {

    override val layoutId: Int
        get() = R.layout.fragment_cards
    override val viewModel: Class<CardsViewModel>
        get() = CardsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    private lateinit var cardsAdapter: CardsAdapter
    private lateinit var cardsArrayList: ArrayList<com.teamx.equiz.ui.fragments.cards.modelcards.PaymentMethod>

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

        cardsArrayList = ArrayList()
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.cardsRecyclerview.layoutManager = linearLayoutManager

        cardsAdapter = CardsAdapter(cardsArrayList, this)
        mViewDataBinding.cardsRecyclerview.adapter = cardsAdapter

        mViewModel.cardsList()

        if (!mViewModel.cardsListResponse.hasActiveObservers()) {
            mViewModel.cardsListResponse.observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let {
                            cardsArrayList.clear()
                            cardsArrayList.addAll(it.paymentMethod)
                            cardsAdapter.notifyDataSetChanged()
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
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }

                    else -> {}
                }
            }
        }

        if (!mViewModel.setDefaultCardResponse.hasActiveObservers()) {
            mViewModel.setDefaultCardResponse.observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let {
                            findNavController().popBackStack()
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
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }

                    else -> {}
                }
            }
        }

    }

    override fun onTopSellerClick(position: Int, name: String) {
        cardsArrayList.forEach {
            it.default = false
        }
        cardsArrayList[position].default = true
        cardsAdapter.notifyDataSetChanged()

        val params  = JsonObject()
        params.addProperty("paymentMethodId",cardsArrayList[position].id)
        mViewModel.setDefaultCard(params)

    }

    override fun onTopSellerSelectClick(position: Int, name: String) {

    }

}