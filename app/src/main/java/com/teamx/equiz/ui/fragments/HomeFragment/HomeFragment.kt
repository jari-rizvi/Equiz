package com.teamx.equiz.ui.fragments.HomeFragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.ProductModel
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentHomeBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_sheet_music_player.*
import kotlinx.android.synthetic.main.bottom_sheet_music_player.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {


    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: Class<HomeViewModel>
        get() = HomeViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var productListAdapter: ProductIstAdapter
    lateinit var bottomSheetProductInfo: BottomSheetBehavior<RelativeLayout>
    var selectedProductModel: ProductModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.hitGetMusicAPI()
        initAdapter()

        sharedViewModel.addToCartProduct.observe(this, Observer {
            if (it != null && it.size > 0) {
                mViewDataBinding.tvFabText.setText(it.size.toString())
                mViewDataBinding.rlFab.visibility = View.VISIBLE
            } else {
                mViewDataBinding.tvFabText.setText("")
                mViewDataBinding.rlFab.visibility = View.GONE
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mViewDataBinding.productList.adapter = productListAdapter
        bottomSheetProductInfo = BottomSheetBehavior.from<RelativeLayout>(mViewDataBinding.musicPlayerBottomSheet.musicPlayerController)
    }

    private fun initAdapter() {
        productListAdapter = ProductIstAdapter(object : ProductIstAdapter.OnItemClickListener {
            override fun onItemClick(itemPosition: Int, productModel: ProductModel) {

                selectedProductModel = productModel
                bottomSheetProductInfo.setState(BottomSheetBehavior.STATE_EXPANDED);
                setDataOnProductDetailBottomSheet(productModel)

            }
        })
    }

    private fun setDataOnProductDetailBottomSheet(productModel: ProductModel) {
        if (!TextUtils.isEmpty(productModel.image)) {
            Picasso.get().load(productModel.image).into(musicPlayerBottomSheet.thumbNail, object : Callback {
                override fun onSuccess() {
                    musicPlayerBottomSheet.spinKit.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    musicPlayerBottomSheet.spinKit.visibility = View.GONE
                }

            })
        }
        if (productModel.isAddToCard) {
            musicPlayerBottomSheet.btnAdd.visibility = View.GONE
            musicPlayerBottomSheet.btnDelete.visibility = View.VISIBLE
        } else {
            musicPlayerBottomSheet.btnAdd.visibility = View.VISIBLE
            musicPlayerBottomSheet.btnDelete.visibility = View.GONE
        }

        musicPlayerBottomSheet.btnAdd.setOnClickListener {
            musicPlayerBottomSheet.btnAdd.visibility = View.GONE
            musicPlayerBottomSheet.btnDelete.visibility = View.VISIBLE
            //false
            selectedProductModel?.isAddToCard = true
            sharedViewModel.addProduct(selectedProductModel!!)
        }

        musicPlayerBottomSheet.btnDelete.setOnClickListener {
            musicPlayerBottomSheet.btnDelete.visibility = View.GONE
            musicPlayerBottomSheet.btnAdd.visibility = View.VISIBLE
            //trur
            selectedProductModel?.isAddToCard = false
            sharedViewModel.removeProduct(selectedProductModel!!)
        }

        if (!TextUtils.isEmpty(productModel.price)) {
            musicPlayerBottomSheet.tvPrice.setText("PKR " + productModel.price)
        }

        if (!TextUtils.isEmpty(productModel.category)) {
            musicPlayerBottomSheet.tvCategory.setText(productModel.category)
        }

        if (!TextUtils.isEmpty(productModel.title)) {
            musicPlayerBottomSheet.tvProductName.setText(productModel.title)
        }

        if (!TextUtils.isEmpty(productModel.description)) {
            musicPlayerBottomSheet.tvProductDescp.setText(productModel.description)
        }
    }

    override fun subscribeToNetworkLiveData() {
        super.subscribeToNetworkLiveData()

        mViewModel.getMusics.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }
                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    it.data?.let {
                        it.let {
                            if (it.isNotEmpty() && this::productListAdapter.isInitialized) {
                                productListAdapter.submitList(it)
                            }
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    DialogHelperClass.errorDialog(requireContext(), it.message!!)
                }
            }
        })
    }
}