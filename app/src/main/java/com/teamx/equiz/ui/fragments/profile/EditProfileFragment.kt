package com.teamx.equiz.ui.fragments.profile

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Keep
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.editProfile.IdentityDocument
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEditProfileBinding
import com.teamx.equiz.ui.fragments.coupons.CouponsAdapter
import com.teamx.equiz.ui.fragments.subscription.SubCatPlanAdapter
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Attribute
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Plan
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates

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

    private lateinit var imageUrl: String
    private lateinit var userName: String
    private lateinit var userEmail: String
    private var progress by Delegates.notNull<Int>()
    private var imgDocUrl = ""
    private lateinit var userPhone: String
    lateinit var userId: String
    var linkInsta = ""
    var linkFb = ""

    lateinit var uploadDocuments: UploadDocuments

    lateinit var docsAdapter: DocsAdapter
    lateinit var docsArrayList: ArrayList<IdentityDocument>

//     var idenArrayList: ArrayList<com.teamx.equiz.ui.fragments.profile.IdentityDocument> = ArrayList()

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

        imageFiles = ArrayList()

        sharedViewModel.setActiveUser("")

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }
        mViewDataBinding.btnChangePass.setOnClickListener {
            findNavController().navigate(R.id.changePassFragment, arguments, options)
        }


        linkFb = mViewDataBinding.instagram.text.toString()
        linkInsta = mViewDataBinding.instagram.text.toString()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        mViewDataBinding.dob.setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        mViewDataBinding.btnAddPicture.setOnClickListener {
            fetchImageFromGallery()
        }

        mViewDataBinding.carImgLayout.setOnClickListener {
            fetchImageFromGallery1()
        }

        GlobalScope.launch(Dispatchers.Main) {
            mViewDataBinding.shimmerLayout.visibility = View.GONE
            mViewDataBinding.root.visibility = View.VISIBLE
            delay(3000)

        }

        mViewModel.me()
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
                            if (data.user.identityDocuments.isNotEmpty()) {
                                mViewDataBinding.recDocs.visibility = View.VISIBLE
                                docsArrayList.clear()

                                docsArrayList.addAll(data.user.identityDocuments)
                                docsAdapter.notifyDataSetChanged()
                            }

                            Log.d("TAG", "onViewCreatedemail: ${data.user.isEmailValid}")
                            Log.d("TAG", "onViewCreatedphone: ${data.user.isPhoneValid}")

                            if (data.user.isEmailValid) {
                                mViewDataBinding.btnVerifyEmail.visibility = View.GONE
                                mViewDataBinding.emailImgVerify.visibility = View.VISIBLE
                            }
                            if (!data.user.isEmailValid) {
                                mViewDataBinding.btnVerifyEmail.visibility = View.VISIBLE
                                mViewDataBinding.emailImgNotVerify.visibility = View.VISIBLE
                            }

                            if (data.user.isPhoneValid) {
                                mViewDataBinding.btnVerifyPhone.visibility = View.GONE
                                mViewDataBinding.phoneImgVerify.visibility = View.VISIBLE
                            }
                            if (!data.user.isPhoneValid) {
                                mViewDataBinding.btnVerifyPhone.visibility = View.VISIBLE
                                mViewDataBinding.phoneImgNotVerify.visibility = View.VISIBLE
                            }


                            var bundle = arguments
                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            bundle?.putString("phone", data.user.phone)
                            bundle?.putString("email", data.user.email)

                            userId = data.user._id


                            Glide.with(mViewDataBinding.profilePicture.context)
                                .load(data.user.image)
                                .error(R.drawable.baseline_person)
                                .into(mViewDataBinding.profilePicture)


//                                    Picasso.get().load(product.quizId.icon.toString()).into(mViewDataBinding.profilePicture)


                            /*     if (data.user.identification.isNotEmpty()) {
                                     mViewDataBinding.doccx.visibility = View.GONE
                                     mViewDataBinding.imgDoc.visibility = View.VISIBLE
                                 } else {
                                     mViewDataBinding.doccx.visibility = View.VISIBLE
                                     mViewDataBinding.imgDoc.visibility = View.GONE
                                 }

                                 Glide.with(mViewDataBinding.imgDoc.context)
                                     .load(data.user.identification)
                                     .error(R.drawable.baseline_person)
                                     .into(mViewDataBinding.imgDoc)*/


                            imageUrl = data.user.image
                            userEmail = data.user.email
                            userPhone = data.user.phone
                            userName = data.user.name
                            progress = data.user.profileProgress
                            mViewDataBinding.simpleProgressBar.secondaryProgress = progress


                            mViewDataBinding.userName.setText(data.user.name)
                            mViewDataBinding.dob.text = data.user.dateOfBirth
                            mViewDataBinding.phone.setText(data.user.phone)
                            mViewDataBinding.email.setText(data.user.email)

                            if (data.user.dateOfBirth.isNullOrEmpty()) {
                                mViewDataBinding.dob.text = "_"
                            } else {
                                mViewDataBinding.dob.text =
                                    data.user.dateOfBirth?.replaceAfter("T", "")
                                        ?.replace("T", "").toString()
                            }

//                            mViewDataBinding.dob.setText(data.user.dateOfBirth.toString())

//                                Picasso.get().load(data.user.image).resize(500, 500)
//                                    .into(mViewDataBinding.profilePicture)


                        } catch (e: Exception) {

                        }

                    }
                }


                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
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



        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


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
                        if (data.user.image.isNotEmpty()) {
                            Glide.with(mViewDataBinding.profilePicture.context)
                                .load(data.user.image).into(mViewDataBinding.profilePicture)

                            if (data.user.image.isNotEmpty()) {
                                Glide.with(mViewDataBinding.profilePicture.context)
                                    .load(data.user.image)
                                    .placeholder(R.drawable.baseline_person_white)
                                    .into(mViewDataBinding.profilePicture)
                            }

                            mViewDataBinding.userName.setText(data.user.name)
                            mViewDataBinding.phone.setText(data.user.phone)
                            mViewDataBinding.email.setText(data.user.email)
                            mViewDataBinding.dob.setText(data.user.dateOfBirth)



                            Log.d("TAG", "textttt: ${mViewDataBinding.dob.text}")


                            if (isAdded) {
                                mViewDataBinding.root.snackbar("Profile updated")
                            }


                        }

                        findNavController().navigate(
                            R.id.action_editProfileFragment_to_dashboardFragment,
                            arguments,
                            options
                        )
                    }
                }


                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
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
                                Glide.with(mViewDataBinding.profilePicture.context).load(data.image)
                                    .into(mViewDataBinding.profilePicture)

                                imageUrl = data.image

                            } catch (e: Exception) {

                            }
                        }
                    }


                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
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



        mViewModel.uploadDocImgResponse.observe(requireActivity()) {
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

                        docsArrayList.clear()
//
                        data.data.forEach {
                            docsArrayList.add(IdentityDocument(null,null,it,null))
                        }


                        docsAdapter.notifyDataSetChanged()

//                        imgDocUrl = data.data[0]

                        Log.d("TAG", "imgDocUrl: $imgDocUrl")
//                        Log.d("TAG", "imgDocUrl: $idenArrayList")
/*
                        try {
                            Glide.with(mViewDataBinding.imgDoc.context).load(imgDocUrl)
                                .into(mViewDataBinding.imgDoc)


                        } catch (e: Exception) {

                        }
                        mViewDataBinding.doccx.visibility = View.GONE
                        mViewDataBinding.imgDoc.visibility = View.VISIBLE*/
                    }
                }


                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
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


        mViewDataBinding.btnSave.setOnClickListener {
            linkFb = mViewDataBinding.facebook.text.toString()
            linkInsta = mViewDataBinding.instagram.text.toString()



            userName = mViewDataBinding.userName.text.toString()
            if (userName.isNotEmpty()) {
                val params = JsonObject()
                try {
                    params.addProperty("email", userEmail)
                    params.addProperty("DOB", dob)
                    params.addProperty("username", userName)
                    params.addProperty("image", imageUrl)

                    if (docsArrayList.isNotEmpty()) {
                        params.add(
                            "identityDocuments", Gson().toJsonTree(
                                docsArrayList
                            )
                        )
                    }


                /*    val socials = listOf(
                        Socials(
                            link = linkFb
                        ),
                        Socials(
                            link = linkInsta
                        )
                    )

                    params.add(
                        "identityDocuments", Gson().toJsonTree(socials)
                    )*/


                } catch (e: JSONException) {
                    e.printStackTrace()
                }



                mViewModel.updateProfile(params)
            } else {
                mViewDataBinding.root.snackbar("Enter Username")
            }

        }

        mViewDataBinding.btnVerifyPhone.setOnClickListener {

            userPhone = mViewDataBinding.phone.text.toString()

            val params = JsonObject()
            try {
                params.addProperty("phone", userPhone)
                params.addProperty("userId", userId)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mViewModel.resendOtp(params)
            if (!mViewModel.resendOtpResponse.hasActiveObservers()) {
                mViewModel.resendOtpResponse.observe(requireActivity()) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.NOTVERIFY -> {
                            loadingDialog.dismiss()
                        }

                        Resource.Status.SUCCESS -> {
                            val bundle = Bundle()
                            bundle.putString("phone", userPhone)
                            loadingDialog.dismiss()
                            findNavController().navigate(
                                R.id.otpPhoneFragment, bundle, options
                            )

                        }

                        Resource.Status.AUTH -> {
                            loadingDialog.dismiss()
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
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.resendOtpResponse.removeObservers(viewLifecycleOwner)
                    }
                }
            }


        }

        mViewDataBinding.btnVerifyEmail.setOnClickListener {
            userEmail = mViewDataBinding.email.text.toString()

            val params = JsonObject()
            try {
                params.addProperty("email", userEmail)
                params.addProperty("userId", userId)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mViewModel.resendOtp(params)
            if (!mViewModel.resendOtpResponse.hasActiveObservers()) {
                mViewModel.resendOtpResponse.observe(requireActivity()) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.NOTVERIFY -> {
                            loadingDialog.dismiss()
                        }

                        Resource.Status.SUCCESS -> {
                            val bundle = Bundle()
                            bundle.putString("email", userEmail)
                            loadingDialog.dismiss()
                            findNavController().navigate(
                                R.id.otpEmailFragment, bundle, options
                            )

                        }

                        Resource.Status.AUTH -> {
                            loadingDialog.dismiss()
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
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.resendOtpResponse.removeObservers(viewLifecycleOwner)
                    }
                }
            }


        }




        docsRecyclerview()
    }


    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dob = sdf.format(cal.getTime())

        mViewDataBinding.dob.text = dob
        Log.d("", "updateDateInView: $dob")
    }

    private fun initialization() {
        fName = mViewDataBinding.userName.text.toString().trim()
//        dob = mViewDataBinding.dob.text.toString().trim()
    }

    private fun fetchImageFromGallery() {
        startForResult.launch("image/*")
    }

    private fun fetchImageFromGallery1() {
        pickImagesLauncher.launch("image/*")
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val str = "${requireContext().filesDir}/file.jpg"

                Log.d("startForResult", "Profile image: $it")


//                uploadWithRetrofit(it)

                val imageUri = uri

                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver, imageUri
                )

// Compress the bitmap to a JPEG format with 80% quality and save it to a file
                val outputStream = FileOutputStream(str)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                outputStream.close()

//                Picasso.get().load(it).into(mViewDataBinding.hatlyIcon)

                uploadWithRetrofit(File(str))
            }
        }

  /*  private val startForResult1 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val str = "${requireContext().filesDir}/file.jpg"

                Log.d("startForResult", "Profile image: $it")


//                uploadWithRetrofit(it)

                val imageUri = uri

                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver, imageUri
                )

// Compress the bitmap to a JPEG format with 80% quality and save it to a file
                val outputStream = FileOutputStream(str)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                outputStream.close()

//                Picasso.get().load(it).into(mViewDataBinding.hatlyIcon)

                uploadWithRetrofit1(File(str))
            }
        }*/



    private lateinit var imageFiles: ArrayList<File>

    private val pickImagesLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
            // Handle the result here


            if (uris != null) {
                imageFiles.clear()

                uris.forEachIndexed { index, uri ->

                    val str = "${requireContext().filesDir}/$index.jpg"

                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        uri
                    )


                    val outputStream = FileOutputStream(str)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                    outputStream.close()

                    if (File(str).exists()) {
                        Log.d("uploadImageArrayList", "exist: $str")
                    }

                    imageFiles.add(File(str))

                }

                if(imageFiles.isNotEmpty()){

                    uploadWithRetrofit1(imageFiles)
                }


                docsAdapter.notifyDataSetChanged()
            }

        }




    private fun uploadWithRetrofit(file: File) {

        val imagesList = mutableListOf<MultipartBody.Part>()

        imagesList.add(prepareFilePart("image", file))

        mViewModel.uploadReviewImg(imagesList)

    }

/*    private fun uploadWithRetrofit1(file: File) {

        val imagesList = mutableListOf<MultipartBody.Part>()

        imagesList.add(prepareFilePart("images", file))

        mViewModel.uploadDocImg(imagesList)

    }*/

    private fun prepareFilePart(partName: String, fileUri: File): MultipartBody.Part {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), fileUri)
        return MultipartBody.Part.createFormData(partName, fileUri.name, requestFile)
    }
    private fun uploadWithRetrofit1(imageFiles: List<File>) {


        val imagesList = mutableListOf<MultipartBody.Part>()

        for (imageUri in imageFiles) {
            imagesList.add(prepareFilePart("images", imageUri))
        }

        mViewModel.uploadDocImg(imagesList)


    }


    private fun docsRecyclerview() {
        docsArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.recDocs.layoutManager = linearLayoutManager

        docsAdapter = DocsAdapter(docsArrayList)
        mViewDataBinding.recDocs.adapter = docsAdapter

    }

}

@Keep
open class Socials(
    open val link: String,
)