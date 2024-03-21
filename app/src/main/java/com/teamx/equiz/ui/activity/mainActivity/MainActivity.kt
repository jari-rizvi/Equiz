package com.teamx.equiz.ui.activity.mainActivity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.MainApplication
import com.teamx.equiz.R
import com.teamx.equiz.SharedViewModel
import com.teamx.equiz.baseclasses.BaseActivity
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.ActivityMainBinding
import com.teamx.equiz.games.games.tetris.logic.SoundUtil
import com.teamx.equiz.games.games.tetris.logic.StatusBarUtil
import com.teamx.equiz.utils.CounterNotificationService
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    DialogHelperClass.Companion.LogoutCallBack {

    override val viewModel: Class<MainViewModel>
        get() = MainViewModel::class.java

    override val layoutId: Int
        get() = R.layout.activity_main

    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var progress_bar: ProgressBar
    private var navController: NavController? = null

    lateinit var sharedViewModel: SharedViewModel

    private var totalActiveTime: Long = 0
    private val handler = Handler()
    private lateinit var runnable: Runnable


    override fun attachBaseContext(newBase: Context?) =
        super.attachBaseContext(MainApplication.localeManager!!.setLocale(newBase!!))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarUtil.transparentStatusBar(this)
        SoundUtil.init(this)

        val khArray = getStringArrayByName("IN")

        khArray?.forEach {
            Log.d("khArray", "onCreate: $it")
        }

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        if (!sharedViewModel.activeUser.hasActiveObservers()) {
            sharedViewModel.activeUser.observe(this) {
                Log.d("activeUserds", "activeUser: $it")
                stopTimer()
                startTimer()
            }
        }

        runnable = Runnable {
            totalActiveTime += 1
            Log.d("activeUserds", "activeUser: $totalActiveTime")
            handler.postDelayed(runnable, 1000)
        }

        service = CounterNotificationService(applicationContext)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        /* mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

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
             navController!!.navigate(R.id.quizesFragment)
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

         mViewDataBinding.drawerLayoutMain.btnCoupon.setOnClickListener {
             mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
             navController!!.navigate(R.id.coupnsFragment
             )
         }

         mViewDataBinding.drawerLayoutMain.btnleaderboard.setOnClickListener {
             mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
             navController!!.navigate(R.id.loaderBoardFragment)
         }
         mViewDataBinding.drawerLayoutMain.btnSubscribe.setOnClickListener {
             mViewDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
             navController!!.navigate(R.id.subscriptionFragment)
         }*/


        setBottomNavigationWithNavController(savedInstanceState)

        if (!sharedViewModel.activeUserResponse.hasActiveObservers()) {
            sharedViewModel.activeUserResponse.observe(this) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.d("destinationsdsd", "LOADING: ")
                    }

                    Resource.Status.NOTVERIFY -> {
                        Log.d("destinationsdsd", "NOTVERIFY: ")
                    }

                    Resource.Status.SUCCESS -> {
                        Log.d("destinationsdsd", "SUCCESS: ${it.data?.activeLevel}")
                        it.data?.let { data ->

                        }
                    }

                    Resource.Status.AUTH -> {
                        Log.d("destinationsdsd", "AUTH: ")
                    }

                    Resource.Status.ERROR -> {
                        Log.d("destinationsdsd", "ERROR: ${it.message}")
                    }
                }
            }
        }


        navController!!.addOnDestinationChangedListener { _, destination, _ ->

            stopTimer()

            if (totalActiveTime.toInt() != 0) {
                Log.d("destinationsdsd", "totalActiveTime: ${totalActiveTime} ")
                val jsonObject = JsonObject()
                jsonObject.addProperty("time", totalActiveTime)
                totalActiveTime = 0
                sharedViewModel.activeUser(jsonObject)
            }

            when (destination.id) {

                R.id.dashboardFragment -> {
                    mViewDataBinding.bottomNavigationq.setSelectedIndex(0)
                    mViewDataBinding.bottomNavigationq.visibility = View.VISIBLE
                    mViewDataBinding.bgShow.visibility = View.VISIBLE

                }

                R.id.ecommerceFragment -> {
                    mViewDataBinding.bottomNavigationq.setSelectedIndex(2)
                    mViewDataBinding.bottomNavigationq.visibility = View.VISIBLE
                    mViewDataBinding.bgShow.visibility = View.VISIBLE
                }

                R.id.userStatsFragment -> {
                    mViewDataBinding.bottomNavigationq.setSelectedIndex(1)
                    mViewDataBinding.bottomNavigationq.visibility = View.VISIBLE
                    mViewDataBinding.bgShow.visibility = View.VISIBLE
                }

                /*R.id.profileFragment -> {

                     mViewDataBinding.bottomNavigationq?.visibility = View.VISIBLE
                }*/

                /*  R.id.quizesFragment -> {
                       mViewDataBinding.bottomNavigationq?.visibility = View.VISIBLE
                  }*/

//                R.id.editProfileFragment -> {
//                     mViewDataBinding.bottomNavigationq?.visibility = View.VISIBLE
//                }

                /*    R.id.qr -> {
                         mViewDataBinding.bottomNavigationq?.visibility = View.VISIBLE
                    }

                    R.id.shopHomePageFragment -> {
                         mViewDataBinding.bottomNavigationq?.visibility = View.VISIBLE
                    }

                    R.id.productPreviewFragment -> {
                         mViewDataBinding.bottomNavigationq?.visibility = View.VISIBLE
                    }*/

                else -> {
                    mViewDataBinding.bottomNavigationq.visibility = View.GONE
                    mViewDataBinding.bgShow.visibility = View.GONE
                }
            }
            /*  mViewDataBinding.bottomNavigationq?.menu?.getItem(2)?.isVisible =
                  !(NetworkCallPoints.TOKENER == null || NetworkCallPoints.TOKENER.equals("null") || NetworkCallPoints.TOKENER.equals(
                      ""
                  ))
              mViewDataBinding.bottomNavigationq?.menu?.getItem(1)?.isVisible =
                  !(NetworkCallPoints.TOKENER == null || NetworkCallPoints.TOKENER.equals("null") || NetworkCallPoints.TOKENER.equals(
                      ""
                  ))*/
//            setupBottomNavMenu(navController!!)
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

    companion object {
        private const val STATE_SAVE_STATE = "save_state"
        private const val STATE_KEEP_FRAGS = "keep_frags"
        private const val STATE_HELPER = "helper"
        var bottomNav: BottomNavigationView? = null
        var service: CounterNotificationService? = null
        var isEnable = false
        var isiaDialog = false

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
                text = /*R.string.title_home*/R.string.profile2,
                count = R.string.empty_value
            ),
            com.teamx.equiz.games.nav_custom.custombottomnav.Model(
                R.drawable.game_dash,
                R.id.userStatsFragment,
                id = 1,
                R.string.profile2/*R.string.title_favorite*/,
                R.string.empty_value
            ),
            com.teamx.equiz.games.nav_custom.custombottomnav.Model(
                R.drawable.cartt,
                R.id.ecommerceFragment,
                2,/*R.string.title_chat*/
                R.string.profile2,
                R.string.empty_value
            ),

            )

        mViewDataBinding.bottomNavigationq.apply {
            // If you don't pass activeIndex then by default it will take 0 position
            setMenuItems(menuItems, -1)
            setupWithNavController(navController)

            // manually set the active item, so from which you can control which position item should be active when it is initialized.
            // onMenuItemClick(4)

            // If you want to change notification count
            setCount(1/*ID_NOTIFICATION*/, R.string.profile/*R.string.count_update*/)
        }

    }

    private fun getStringArrayByName(arrayName: String): Array<String>? {
        val resId = resources.getIdentifier(arrayName, "array", packageName)
        return if (resId != 0) {
            resources.getStringArray(resId)
        } else {
            null
        }
    }

    private fun startTimer() {
        handler.post(runnable)
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onResume() {
        super.onResume()
        startTimer()
//        if (!handler.hasCallbacks { runnable }) {
//        }

    }

    override fun onPause() {
        super.onPause()

        stopTimer()

    }

}