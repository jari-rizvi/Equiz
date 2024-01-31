package com.teamx.equiz.ui.fragments.reciept



import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentRecieptBinding
import com.teamx.equiz.ui.fragments.wishlist.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import java.util.Date
import androidx.activity.addCallback
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

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
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }



        mViewDataBinding.btnSave.setOnClickListener {
            ScreenshotButton()
        }

        val bundle = arguments
        if (bundle != null) {
            id = bundle.getString("id").toString()
            paymentMethod = bundle.getString("paymentMethod").toString()
            total = bundle.getString("total").toString()
            date = bundle.getString("date").toString()
        }
        Log.d("TAG", "onViewCreated12121221: $paymentMethod")
        Log.d("TAG", "onViewCreated12121221: $total")


        mViewDataBinding.textView66.text = date
        mViewDataBinding.orderNo.text = id
        mViewDataBinding.paymentType.text = paymentMethod
        mViewDataBinding.total.text = total


    }
    fun ScreenshotButton() {
        val v1 = requireActivity().window.decorView.rootView
        v1.setDrawingCacheEnabled(true)
        v1.setDrawingCacheEnabled(false)
//        val filePath = Environment.getExternalStorageDirectory()
//            .toString() + "/Download/" + Calendar.getInstance().getTime().toString() + ".jpg"
//        val fileScreenshot = File(filePath)
        val authority = "com.teamx.equiz.fileprovider"
//        val fileScreenshot = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot.jpg")
//        var fileOutputStream: FileOutputStream? = null
//        try {
//
//            fileOutputStream = FileOutputStream(fileScreenshot)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
//            fileOutputStream.flush()
//            fileOutputStream.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }



        v1.isDrawingCacheEnabled = true
        val bitmap: Bitmap = Bitmap.createBitmap(v1.drawingCache)
        v1.isDrawingCacheEnabled = false

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "screenshot_$timeStamp.jpg"
        val storageDir = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Download")
        val filePath = File(storageDir, fileName)

        var fileOutputStream: FileOutputStream? = null
        try {
            if (!storageDir.exists()) {
                storageDir.mkdirs()
            }

            fileOutputStream = FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        val intent = Intent(Intent.ACTION_VIEW)
        val uri = FileProvider.getUriForFile(requireContext(), authority, filePath)
        intent.setDataAndType(uri, "image/jpeg")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)


        this.startActivity(intent)
    }




}