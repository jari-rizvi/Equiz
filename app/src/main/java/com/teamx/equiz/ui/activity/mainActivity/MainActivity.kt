package com.teamx.equiz.ui.activity.mainActivity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseActivity
import com.teamx.equiz.databinding.ActivityMainBinding
import com.teamx.equiz.games.games.tetris.logic.SoundUtil
import com.teamx.equiz.games.games.tetris.logic.StatusBarUtil
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

//for tetris
        StatusBarUtil.transparentStatusBar(this)
        SoundUtil.init(this)

        //


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


        setBottomNavigationWithNavController(savedInstanceState)

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


    private fun setBottomNavigationWithNavController(savedInstanceState: Bundle?) {

        // If you don't pass activeIndex then by default it will take 0 position
        val activeIndex = savedInstanceState?.getInt("activeIndex") ?: 0

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.dashboardFragment,
//                R.id.gamesFragment,
//                R.id.editProfileFragment,
//            )
//        )
//        setupActionBarWithNavController(this, navController, appBarConfiguration)
        val menuItems = arrayOf(
            com.teamx.equiz.games.nav_custom.custombottomnav.Model(
                icon = R.drawable.home_dash,
                destinationId = R.id.dashboardFragment,
                id = 0,
                text = /*R.string.title_home*/R.string.dashboard,
                count = R.string.empty_value
            ),
            com.teamx.equiz.games.nav_custom.custombottomnav.Model(
                R.drawable.game_dash,
                R.id.gamesFragment,
                id = 1,
                R.string.Games/*R.string.title_favorite*/,
                R.string.empty_value
            ),
            com.teamx.equiz.games.nav_custom.custombottomnav.Model(
                R.drawable.setting_dash,
                R.id.editProfileFragment,
                2,/*R.string.title_chat*/
                R.string.profile,
                R.string.empty_value
            ),

            )

        mViewDataBinding.bottomNavigationq.apply {
            // If you don't pass activeIndex then by default it will take 0 position
            setMenuItems(menuItems, activeIndex)
            setupWithNavController(navController)

            // manually set the active item, so from which you can control which position item should be active when it is initialized.
            // onMenuItemClick(4)

            // If you want to change notification count
            setCount(1/*ID_NOTIFICATION*/,  R.string.profile/*R.string.count_update*/)
        }

    }


}