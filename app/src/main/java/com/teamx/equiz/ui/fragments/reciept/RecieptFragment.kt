package com.teamx.equiz.ui.fragments.reciept



import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentRecieptBinding
import com.teamx.equiz.ui.fragments.wishlist.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.util.Date


@AndroidEntryPoint
class RecieptFragment : BaseFragment<FragmentRecieptBinding, WishlistViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_reciept
    override val viewModel: Class<WishlistViewModel>
        get() = WishlistViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    var id = ""
    var paymentMethod = ""
    var total = ""
    var date = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            popUpStack()
        }


      var  view = mViewDataBinding.constraintLayout8

        mViewDataBinding.btnSave.setOnClickListener {
            screenShot(view)

            showToast("sdsdsdsd")
        }

        val bundle = arguments
        if (bundle != null) {
            id = bundle.getString("id").toString()
            paymentMethod = bundle.getString("paymentMethod").toString()
            total = bundle.getString("total").toString()
            date = bundle.getString("date").toString()
        }


        mViewDataBinding.textView66.text = date
        mViewDataBinding.orderNo.text = id
        mViewDataBinding.paymentType.text = paymentMethod
        mViewDataBinding.total.text = total


    }
    private fun takeScreenshot() {
        val now = Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg"

            // create bitmap screen capture
            val v1 = activity!!.window.decorView.rootView
            v1.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false
            val imageFile = File(mPath)
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            openScreenshot(imageFile)
        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }
    }
    private fun openScreenshot(imageFile: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.fromFile(imageFile)
        intent.setDataAndType(uri, "image/*")
        startActivity(intent)
    }


    fun screenShot(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }


}