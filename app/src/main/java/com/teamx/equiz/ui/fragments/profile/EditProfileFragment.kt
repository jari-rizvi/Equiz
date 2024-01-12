package com.teamx.equiz.ui.fragments.profile

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEditProfileBinding
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_edit_profile
    override val viewModel: Class<EditProfileViewModel>
        get() = EditProfileViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private var fName: String? = null
    private var dob: String? = null
    var cal = Calendar.getInstance()
    var textview_date: TextView? = null

    var imageUrl = ""
    var userName = ""
    var userEmail = ""
    var userDOB = ""
    var userPhone = ""

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
        mViewDataBinding.btnChangePass.setOnClickListener {
            findNavController().navigate(R.id.changePassFragment, arguments, options)
        }

        mViewDataBinding.btnAddPicture.setOnClickListener {
            fetchImageFromGallery()
        }

        GlobalScope.launch(Dispatchers.Main) {
            mViewDataBinding.shimmerLayout.visibility = View.GONE
            mViewDataBinding.root.visibility = View.VISIBLE
            delay(3000)

        }




        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

        mViewDataBinding.btnSave.setOnClickListener {
            userName = mViewDataBinding.userName.text.toString()
            if (userName.isNotEmpty()) {
                val params = JsonObject()
                try {
                    params.addProperty("email", userEmail)
                    params.addProperty("DOB", userDOB)
                    params.addProperty("username", userName)
                    params.addProperty("image", imageUrl)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                mViewModel.updateProfile(params)
            } else {
                mViewDataBinding.root.snackbar("Enter Username")
            }

        }


        if (!mViewModel.updateProfileResponse.hasActiveObservers()) {
            mViewModel.updateProfileResponse.observe(requireActivity()) {
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
                            if (data.user.image.isNotEmpty()){
                            Glide.with(mViewDataBinding.profilePicture.context).load(data.user.image).placeholder(R.drawable.baseline_person_white).into(mViewDataBinding.profilePicture)
                            }

                            mViewDataBinding.userName.setText(data.user.name)
                            mViewDataBinding.phone.setText(data.user.phone)
                            mViewDataBinding.email.setText(data.user.email)

                            /*  val userData = PrefHelper.getInstance(requireActivity()).getUserData()
                              userData!!.name = data.name
                              userData!!.profileImage = data.profileImage
                              PrefHelper.getInstance(requireActivity()).setUserData(userData)*/
                            if (isAdded) {
                                mViewDataBinding.root.snackbar("Profile updated")
                            }

                            /*       val bundle = arguments
                                   if (bundle != null) {
                                       bundle.putString("userimg", data.profileImage)
                                       bundle.putString("username", data.name)
                                   }*/


                            findNavController().navigate(R.id.action_editProfileFragment_to_dashboardFragment,arguments,options)


                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
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
                    }
                }
            }
        }


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
                            try {

                                Glide.with(mViewDataBinding.profilePicture.context).load(data.user.image).into(mViewDataBinding.profilePicture)
                                imageUrl = data.user.image
//                            userDOB = data.user.dateOfBirth.toString()
                                userEmail = data.user.email
                                userPhone = data.user.phone
                                userName = data.user.name
                                mViewDataBinding.userName.setText(data.user.name)
                                mViewDataBinding.phone.setText(data.user.phone)
                                mViewDataBinding.email.setText(data.user.email)
//                            mViewDataBinding.dob.setText(data.user.dateOfBirth.toString())

//                                Picasso.get().load(data.user.image).resize(500, 500)
//                                    .into(mViewDataBinding.profilePicture)



                            }
                            catch (e: Exception) {

                            }

                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
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
                    }
                }
            }
        }


        if (!mViewModel.uploadReviewImgResponse.hasActiveObservers()) {
            mViewModel.uploadReviewImgResponse.observe(requireActivity()) {
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
//                                Picasso.get().load(imageUrl).resize(500, 500)
//                                    .into(mViewDataBinding.profilePicture)
                                Glide.with(mViewDataBinding.profilePicture.context).load(data.image).into(mViewDataBinding.profilePicture)

                            }
                            catch (e: Exception) {

                            }
                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
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
                        Log.d("uploadReviewIm", "onViewCreated: ${it.message}")
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }


        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        mViewDataBinding.dob.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dob = sdf.format(cal.getTime())

        mViewDataBinding.dob.setText(dob)
    }

    private fun initialization() {
        fName = mViewDataBinding.userName.text.toString().trim()
//        dob = mViewDataBinding.dob.text.toString().trim()
    }

    private fun fetchImageFromGallery() {
        startForResult.launch("image/*")
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val str = "${requireContext().filesDir}/file.jpg"

                Log.d("startForResult", "Profile image: $it")


//                uploadWithRetrofit(it)

                val imageUri = uri

                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    imageUri
                )

// Compress the bitmap to a JPEG format with 80% quality and save it to a file
                val outputStream = FileOutputStream(str)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                outputStream.close()

//                Picasso.get().load(it).into(mViewDataBinding.hatlyIcon)

                uploadWithRetrofit(File(str))
            }
        }

    private fun uploadWithRetrofit(file: File) {

        val imagesList = mutableListOf<MultipartBody.Part>()

        imagesList.add(prepareFilePart("image", file))

        mViewModel.uploadReviewImg(imagesList)

    }

    private fun prepareFilePart(partName: String, fileUri: File): MultipartBody.Part {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), fileUri)
        return MultipartBody.Part.createFormData(partName, fileUri.name, requestFile)
    }


}
