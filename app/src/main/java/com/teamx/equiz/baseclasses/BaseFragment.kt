package com.teamx.equiz.baseclasses

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.teamx.equiz.R
import com.teamx.equiz.SharedViewModel
import com.teamx.equiz.baseclasses.BaseActivity
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.local.datastore.DataStoreProvider
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.VeriffyOtp
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : androidx.fragment.app.Fragment(){

    lateinit var sharedViewModel: SharedViewModel
    lateinit var navController: NavController
    private lateinit var options: NavOptions
    lateinit var dataStoreProvider: DataStoreProvider

    private var mActivity: BaseActivity<*, *>? = null
    lateinit var mViewDataBinding: T
    protected lateinit var mViewModel: V

    abstract val layoutId: Int
    abstract val viewModel: Class<V>
    abstract val bindingVariable: Int

    protected lateinit var loadingDialog: Dialog
    var lang = "en"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        return mViewDataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        mViewDataBinding.setVariable(bindingVariable, mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()


        subscribeToShareLiveData()
        subscribeToNavigationLiveData()
        subscribeToNetworkLiveData()
        subscribeToViewLiveData()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>)
            this.mActivity = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = DialogHelperClass.loadingDialog(requireContext())
        mViewModel = ViewModelProvider(this).get(viewModel)


        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        dataStoreProvider = DataStoreProvider(requireContext())
        navController = NavController(requireContext())

    }

    open fun subscribeToViewLiveData() {

        //observe view data

    }


    open fun subscribeToShareLiveData() {

    }

    open fun getMainActivity(): MainActivity? {
        return activity as MainActivity?
    }

    open fun subscribeToNetworkLiveData() {
        //All Network Tasks
    }

    open fun subscribeToNavigationLiveData() {

    }

    open fun showProgressBar() {
        (activity as MainActivity).showProgressBar()
    }

    open fun hideProgressBar() {
        (activity as MainActivity).hideProgressBar()
    }

    fun naviagteFragment(fragment : Int, isNavigate : Boolean){

        if (isNavigate){
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(fragment, null)
        }

    }

    open fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    open fun popUpStack() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.popBackStack()

    }


/*
    var dialog: Dialog? = null

    override fun onToOtpPage() {
        if (isAdded) {
            Log.d("123123", "onToSignUpPage: ")

            if (dialog == null) {
                dialog = DialogHelperClass.verifyOtpDialog(requireContext(), this@BaseFragment)

                dialog?.show()
                dialog?.setOnDismissListener {
                    dialog = null
                }
            } else {
                dialog?.dismiss()
                dialog = null
            }

        }
    }
*/




/*    override fun onOtpClick() {
        mViewModel.viewModelScope.launch {
//            dataStoreProvider.saveUserToken("")
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.otpEmailFragment, null)

        }
    }*/


}