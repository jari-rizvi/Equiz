package com.teamx.equiz.ui.fragments.reciept



import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
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
import androidx.core.content.ContextCompat
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

    private val REQUEST_CODE_STORAGE_PERMISSION = 1001

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

        sharedViewModel.setActiveUser("")



        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }




        mViewDataBinding.btnSave.setOnClickListener {
            requestStoragePermission()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && resultCode == Activity.RESULT_OK) {
            // Handle permission granted
            val treeUri = data?.data
            ScreenshotButton()
            // Use treeUri to access the selected directory
        }
    }

    private fun requestStoragePermission() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, REQUEST_CODE_STORAGE_PERMISSION)
    }
    fun ScreenshotButton() {
   /*     val v1 = requireActivity().window.decorView.rootView
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
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
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


        this.startActivity(intent)*/


        val v1 = requireActivity().window.decorView.rootView

// Enable drawing cache
        v1.isDrawingCacheEnabled = true

// Create bitmap from drawing cache
        val bitmap: Bitmap = Bitmap.createBitmap(v1.drawingCache)

// Disable drawing cache
        v1.isDrawingCacheEnabled = false

// Generate file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "screenshot_$timeStamp.jpg"

// Get external storage directory
        val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Download")

// Create storage directory if it doesn't exist
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

// Create file
        val filePath = File(storageDir, fileName)

        var fileOutputStream: FileOutputStream? = null
        try {
            // Create file output stream
            fileOutputStream = FileOutputStream(filePath)

            // Compress bitmap and save to file
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)

            // Flush the stream to ensure all data is written to the file
            fileOutputStream.flush()

            // Send broadcast to notify the system about the new file
            MediaScannerConnection.scanFile(
                requireContext(),
                arrayOf(filePath.toString()),
                arrayOf("image/jpeg"),
                null
            )

            // Show a toast message indicating successful saving
            Toast.makeText(requireContext(), "Screenshot saved to gallery", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            // Show a toast message indicating failure
            Toast.makeText(requireContext(), "Failed to save screenshot", Toast.LENGTH_SHORT).show()
        } finally {
            // Close the file output stream
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }




}