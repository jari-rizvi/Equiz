package com.teamx.equiz.ui.fragments.subscription


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentUnsubscriptionBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Attribute
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnsubsFragment : BaseFragment<FragmentUnsubscriptionBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_unsubscription
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    lateinit var subCatAdapter: SubCatPlanAdapter
    lateinit var subCatArrayList: ArrayList<Attribute>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        mViewDataBinding.btnback.setOnClickListener {
            popUpStack()
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

        sharedViewModel.setActiveUser("")

        mViewModel.me()

        if (!mViewModel.meResponse.hasActiveObservers()) {
            mViewModel.meResponse.observe(requireActivity()) {
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

                            data.user.stripProductId?.categoryId!!.attributes!!.forEach {
                                subCatArrayList.add(it)
                            }
                            subCatAdapter.notifyDataSetChanged()

                            Glide.with(mViewDataBinding.imageView16.context)
                                .load(data.user.stripProductId.categoryId.image)
                                .into(mViewDataBinding.imageView16)
                            mViewDataBinding.textView58.text =
                                data.user.stripProductId.categoryId.title
                            mViewDataBinding.price.text = data.user.stripProductId.price.toString()
                            val date = data.user.updatedAt.toString().replaceAfter('T', "")
                                .replace("T", "")

                            mViewDataBinding.validity.text = "Valid till " + date


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
                    mViewModel.meResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }


        mViewDataBinding.unsub.setOnClickListener {

                  DialogHelperClass.unsubUserDialog(requireContext(),
                      object : DialogHelperClass.Companion.DeleteUserDialogCallBack {
                          override fun onSignInClick1() {

                          }

                          override fun onSignUpClick1() {
                              mViewModel.unsub()
                              if (!mViewModel.unsubResponse.hasActiveObservers()) {
                                  mViewModel.unsubResponse.observe(requireActivity()) {
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

                                                  try {
      //                                mViewDataBinding.root.snackbar(data)
                                                      mViewDataBinding.root.snackbar("Subcription will end at the end of the Month")


                                                  } catch (e: Exception) {
                                                      e.printStackTrace()
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


                          }

                      }).show()


        }

        SubCatPlansRecyclerview()

    }

    private fun SubCatPlansRecyclerview() {
        subCatArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recCatPlans1.layoutManager = linearLayoutManager

        subCatAdapter = SubCatPlanAdapter(subCatArrayList)
        mViewDataBinding.recCatPlans1.adapter = subCatAdapter

    }


}

