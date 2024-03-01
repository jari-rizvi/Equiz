package com.teamx.equiz.ui.fragments.claimPrize


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.anupkumarpanwar.scratchview.ScratchView
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.stripe.android.Stripe
import com.teamx.equiz.BR
import com.teamx.equiz.MainApplication
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentClaimprizeBinding
import com.teamx.equiz.ui.fragments.claimPrize.adapter.ClaimPrizeChancesAdapter
import com.teamx.equiz.ui.fragments.claimPrize.adapter.ClaimPrizeInterface
import com.teamx.equiz.ui.fragments.claimPrize.model.ClaimPrizeModel
import com.teamx.equiz.ui.fragments.claimPrize.model.Raffle
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class ClaimPrizeFragment : BaseFragment<FragmentClaimprizeBinding, ClaimPrizeViewModel>(),
    ClaimPrizeInterface {

    override val layoutId: Int
        get() = R.layout.fragment_claimprize
    override val viewModel: Class<ClaimPrizeViewModel>
        get() = ClaimPrizeViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

//    lateinit var scratchImageView: ScratchView


    var winnerId = ""
    var prizeId = ""
    var resValue = 300
    var raffleId = ""
    var resType = ""
    var selectedRaffleId = ""

    lateinit var claimPrizeModel: ClaimPrizeModel


    private lateinit var options: NavOptions
    private lateinit var stripe: Stripe

    private lateinit var claimPrizeChancesAdapter: ClaimPrizeChancesAdapter
    private lateinit var claimPrizeArrayList: ArrayList<Raffle>

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

        claimPrizeArrayList = ArrayList()

        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.chancesRec.layoutManager = layoutManager2

        claimPrizeChancesAdapter = ClaimPrizeChancesAdapter(claimPrizeArrayList, this)
        mViewDataBinding.chancesRec.adapter = claimPrizeChancesAdapter



        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }


        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

        mViewDataBinding.txtDropDown.setOnClickListener {
            if (mViewDataBinding.chancesRec.isVisible) {
                mViewDataBinding.chancesRec.visibility = View.GONE
            } else {
                mViewDataBinding.chancesRec.visibility = View.VISIBLE
            }
        }


        var bundle = arguments

        if (bundle == null) {
            bundle = Bundle()
        }

        val winnerid = bundle.getString("winnerid").toString()


        if (!mViewModel.claimPrizeResponse.hasActiveObservers()) {
            mViewModel.claimPrize(winnerid)
            mViewModel.claimPrizeResponse.observe(requireActivity()) {
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

                            claimPrizeArrayList.clear()
                            claimPrizeModel = data

                            winnerId = data.prizeObj.winnerId ?: ""
                            prizeId = data.prizeObj.prizeId ?: ""
                            resValue = data.prizeObj.value ?: 300
                            raffleId = data.prizeObj.raffleId ?: ""



                            when (data.prizeObj.type) {
                                "chances" -> {
                                    mViewDataBinding.radioChances.visibility = View.VISIBLE
                                    mViewDataBinding.radioWallet.visibility = View.GONE
                                    mViewDataBinding.radioTangible.visibility = View.GONE
                                    mViewDataBinding.view2.visibility = View.GONE
                                    mViewDataBinding.view1.visibility = View.GONE

                                    CoroutineScope(Dispatchers.Main).launch {
                                        for (i in 0..10) {
                                            delay(300)
                                            claimPrizeArrayList.add(claimPrizeModel.prizeObj.raffles!![0])
                                            claimPrizeChancesAdapter.notifyItemInserted(i)
                                        }
//                                        data.prizeObj.raffles?.forEachIndexed { index, raffle ->
//                                            claimPrizeArrayList.add(raffle)
//                                            claimPrizeChancesAdapter.notifyItemInserted(index)
//                                        }
                                    }
                                }

                                "wallet" -> {
                                    mViewDataBinding.radioChances.visibility = View.GONE
                                    mViewDataBinding.radioWallet.visibility = View.VISIBLE
                                    mViewDataBinding.radioTangible.visibility = View.GONE
                                    mViewDataBinding.view2.visibility = View.GONE
                                    mViewDataBinding.view1.visibility = View.GONE
                                }

                                "users_choice" -> {
                                    mViewDataBinding.userChoice.visibility = View.VISIBLE
                                    mViewDataBinding.layoutMainScratched.visibility = View.GONE


                                    data.prizeObj.raffles?.let { it1 ->
                                        claimPrizeArrayList.addAll(
                                            it1
                                        )
                                    }

//                                    data.prizeObj.raffles!!.forEach {
//                                        it.isChecked = false
//                                        claimPrizeArrayList.add(it)
//                                        claimPrizeArrayList.add(it)
//                                    }
                                    claimPrizeChancesAdapter.notifyDataSetChanged()
                                }

                                "items" -> {
                                    mViewDataBinding.radioChances.visibility = View.GONE
                                    mViewDataBinding.radioWallet.visibility = View.GONE
                                    mViewDataBinding.radioTangible.visibility = View.VISIBLE
                                    mViewDataBinding.view2.visibility = View.GONE
                                    mViewDataBinding.view1.visibility = View.GONE
                                    mViewDataBinding.layoutSocials.visibility = View.VISIBLE
                                }

                                "scratchcard" -> {
                                    mViewDataBinding.layoutMainScratched.visibility = View.VISIBLE
//                                    mViewDataBinding.userChoice.visibility = View.GONE
                                    scratchViewInit(winnerid)
                                }

                                else -> {}
                            }
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
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                    }
                }
            }
        }

        if (!mViewModel.submitClaimResponse.hasActiveObservers()) {
            mViewModel.submitClaimResponse.observe(requireActivity()) {
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

                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
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
            }
        }

        Log.d("TAG", "onViewCreated: $winnerid")


        mViewDataBinding.radioChances.setOnClickListener {
            mViewDataBinding.radioTangible.isChecked = false
            mViewDataBinding.radioWallet.isChecked = false
            mViewDataBinding.radioChances.isChecked = true
            mViewDataBinding.txtDropDown.visibility = View.VISIBLE
            resType = "chances"
        }

        mViewDataBinding.radioTangible.setOnClickListener {
            mViewDataBinding.radioTangible.isChecked = true
            mViewDataBinding.radioWallet.isChecked = false
            mViewDataBinding.radioChances.isChecked = false
            mViewDataBinding.txtDropDown.visibility = View.GONE
            resType = "items"
        }
        mViewDataBinding.radioWallet.setOnClickListener {
            mViewDataBinding.radioTangible.isChecked = false
            mViewDataBinding.radioWallet.isChecked = true
            mViewDataBinding.radioChances.isChecked = false
            mViewDataBinding.txtDropDown.visibility = View.GONE
            resType = "wallet"
        }


        mViewDataBinding.btnSocialFb.setOnClickListener {
            navController = Navigation.findNavController(
                requireActivity(), R.id.nav_host_fragment
            )
            navController.navigate(
                R.id.chattFragment, null, options
            )
        }


        mViewDataBinding.btnLogin.setOnClickListener {
            submitClaim()
        }

    }

    private fun scratchViewInit(id: String) {
        mViewModel.scratchimg(id)
        if (!mViewModel.scratchimgResponse.hasActiveObservers()) {
            mViewModel.scratchimgResponse.observe(requireActivity()) {
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
                            Glide.with(MainApplication.context).load(data.data.image)
                                .into(mViewDataBinding.layoutScratched.hiddenImg);


                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
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
            }
        }


        mViewDataBinding.layoutScratched.scratchView.setRevealListener(object :
            ScratchView.IRevealListener {
            override fun onRevealed(scratchView: ScratchView) {
                Toast.makeText(requireContext(), "Revealed", Toast.LENGTH_SHORT).show()
                mViewDataBinding.layoutScratched.scrTxt.visibility = View.GONE
                mViewDataBinding.layoutScratched.imgTxt2.visibility = View.GONE
                mViewDataBinding.layoutScratched.scratchView.visibility = View.GONE
                mViewDataBinding.layoutScratched.img2.visibility = View.VISIBLE
            }

            override fun onRevealPercentChangedListener(scratchView: ScratchView, percent: Float) {

                if (percent >= .8f) {
                    scratchView.reveal()
                }

            }
        })
    }

    private fun submitClaim() {
        val params = JsonObject()
        try {
            params.addProperty("winnerId", winnerId)
            params.addProperty("prizeId", prizeId)
            params.addProperty("type", resType)
            params.addProperty("value", 23)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewModel.submitClaim(params)

    }

    override fun claimPrize(position: Int) {
        claimPrizeArrayList.forEach {
            it.isSelected = false
        }
        Log.d("winneriddsdsd", "winnerid: ${claimPrizeArrayList[position]}")
//        claimPrizeArrayList[position].isSelected = true
        Log.d("winneriddsdsd", "winnerid: ${claimPrizeArrayList[position]}")


        claimPrizeChancesAdapter.notifyDataSetChanged()
//        claimPrizeChancesAdapter.notifyItemChanged(position)
//        claimPrizeChancesAdapter.notifyItemRangeChanged(
//            position,
//            claimPrizeArrayList.size
//        )


    }

}