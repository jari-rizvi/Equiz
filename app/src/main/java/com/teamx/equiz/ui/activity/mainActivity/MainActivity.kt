package com.teamx.equiz.ui.activity.mainActivity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseActivity
import com.teamx.equiz.databinding.ActivityMainBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), DialogHelperClass.Companion.LogoutCallBack  {

    override val viewModel: Class<MainViewModel>
        get() = MainViewModel::class.java

    override val layoutId: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var progress_bar: ProgressBar
    private var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        mViewDataBinding.drawerLayoutMain.btneccomernce.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.ecommerceFragment)
        }
        mViewDataBinding.drawerLayoutMain.btnHome.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.dashboardFragment)
        }
        mViewDataBinding.drawerLayoutMain.btnquiz.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.quizFragment)
        }
        mViewDataBinding.drawerLayoutMain.btnWallet.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.walletFragment)
        }

        mViewDataBinding.drawerLayoutMain.btnCoupon.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.coupnsFragment)
        }

        mViewDataBinding.drawerLayoutMain.btnReffeal.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.referralFragment)
        }

        mViewDataBinding.drawerLayoutMain.btnProfile.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.profileFragment)
        }

        mViewDataBinding.drawerLayoutMain.btnNotification.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.notificationsFragment)
        }

        mViewDataBinding.drawerLayoutMain.btnNews.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.newsFragment)
        }

        mViewDataBinding.drawerLayoutMain.btnSupport.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.supportFragment)
        }

        mViewDataBinding.drawerLayoutMain.btnSetting.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            Toast.makeText(this, "Comming Soom", Toast.LENGTH_SHORT).show();
        }

        mViewDataBinding.drawerLayoutMain.btnTermsAndCondition.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.wishlistFragment)
            Toast.makeText(this, "Comming Soom", Toast.LENGTH_SHORT).show();
        }

        mViewDataBinding.drawerLayoutMain.btnCollectPrize.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            navController!!.navigate(R.id.profileFragment)
        }

        mViewDataBinding.drawerLayoutMain.btnleaderboard.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            Toast.makeText(this, "Comming Soom", Toast.LENGTH_SHORT).show();
        }

        mViewDataBinding.drawerLayoutMain.btnLogout.setOnClickListener {
            mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            DialogHelperClass.LogoutDialog(

               this , this, true
            )
        }

    }

    fun openDrawer() {
        if (mViewDataBinding.drawerLayout.isOpen) {
            mViewDataBinding.drawerLayout.openDrawer(GravityCompat.END)
        } else {
            mViewDataBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    open fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    open fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    override fun OkClick() {
    }


}