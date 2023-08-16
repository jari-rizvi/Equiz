package com.teamx.equiz.baseclasses

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.SharedViewModel
import com.teamx.equiz.utils.DialogHelperClass


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    lateinit var sharedViewModel: SharedViewModel

    private var mActivity: BaseActivity<*, *>? = null
    lateinit var mViewDataBinding: T
    protected lateinit var mViewModel: V

    abstract val layoutId: Int
    abstract val viewModel: Class<V>
    abstract val bindingVariable: Int

    protected lateinit var loadingDialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mViewDataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(bindingVariable, mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()


        subscribeToShareLiveData()
        subscribeToNavigationLiveData()
        subscribeToNetworkLiveData()
        subscribeToViewLiveData()


    }

    open fun subscribeToViewLiveData() {

        //observe view data

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
    }


    open fun subscribeToShareLiveData() {

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
}