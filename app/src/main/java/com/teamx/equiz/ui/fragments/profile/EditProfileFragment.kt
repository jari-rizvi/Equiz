package com.teamx.equiz.ui.fragments.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.LoginStatusCallback
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.snapchat.kit.sdk.SnapLogin
import com.snapchat.kit.sdk.login.models.UserDataResponse
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback
import com.teamx.equiz.BR
import com.teamx.equiz.MainActivitytttt
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.editProfile.IdentityDocument
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEditProfileBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates
import java.security.SecureRandom
import java.util.Base64

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(),
    OnRemoveImg {

    override val layoutId: Int
        get() = com.teamx.equiz.R.layout.fragment_edit_profile
    override val viewModel: Class<EditProfileViewModel>
        get() = EditProfileViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private val CLIENT_ID = "d221aa97-89d9-4ced-befe-f15ae538bd8f"
    private val REDIRECT_URI = "loginkitsample://snap-kit/oauth2"
    private val SNAPCHAT_AUTH_URL = "https://accounts.snapchat.com/accounts/oauth2/auth"
    private val SNAPCHAT_TOKEN_URL = "https://accounts.snapchat.com/accounts/oauth2/token"


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
    val username = mutableStateOf("")
    val bitmoji = mutableStateOf("")

    lateinit var uploadDocuments: UploadDocuments

    lateinit var docsAdapter: DocsAdapter
    lateinit var docsArrayList: ArrayList<IdentityDocument>

    /*   val authApi = AuthApi(
           activity = requireActivity()
       )*/

//     var idenArrayList: ArrayList<com.teamx.equiz.ui.fragments.profile.IdentityDocument> = ArrayList()


//    fun tiktok(){
//
//// STEP 2: Create an AuthRequest and set parameters
//        val request = AuthRequest(
//            clientKey = "awvbco7q9fdpucdi",
//            scope = "user.info.basic",
//            redirectUri = "redirectUri",
//            codeVerifier = "codeVerifier"
//        )
//
//        authApi.authorize(
//            request = request,
//            authMethod = AuthApi.AuthMethod.TikTokApp
//        );
//        val intent = Intent(requireContext(), MainActivitytttt::class.java)
//
//
//        val authResponse = authApi.getAuthResponseFromIntent(intent, "YOUR_REDIRECT_URI")
//
//        authResponse?.let { response ->
//            val authCode = response.authCode // The auth code will be used to obtain access token
//            val grantedPermissions = response.grantedPermissions // Granted scopes
//
//            val authError = response.authError
//            val authErrorDescription = response.authErrorDescription
//
//        }
//
//    }


    fun fetchInstagramProfile() {
        val request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken()
        ) { _, result ->
            if (result != null) {


//                val emailObject = result.getJSONObject()?.optJSONObject("email")
//                val email = emailObject?.getString("email")


                val response = result.getJSONObject()
                val email = response?.getString("email")
                val userID = response?.getString("id")
                val lastName = response?.getString("last_name")
                val firstName = response?.getString("first_name")
                val fullName = response?.getString("name")

                var profilePictureUrl: String? = null

//                  val pictureData = result.optJSONObject("picture")?.optJSONObject("data")

                val pictureData = response?.getJSONObject("picture")?.getJSONObject("data")
//                val profilePictureUrl = pictureData.getString("url")

                val url = pictureData?.optString("url")
                if (!url.isNullOrEmpty()) {
                    profilePictureUrl = url
                }

                Log.d("TAG", "fetchInstagramProfile: $email")
                Log.d("TAG", "fetchInstagramProfile: $userID")
                Log.d("TAG", "fetchInstagramProfile: $lastName")
                Log.d("TAG", "fetchInstagramProfile: $firstName")
                Log.d("TAG", "fetchInstagramProfile: $fullName")
                Log.d("TAG", "fetchInstagramProfile: $profilePictureUrl")

            }
        }

        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id, name, first_name, last_name, picture.type(large), email"
        )
        request.parameters = parameters

        request.executeAsync()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = com.teamx.equiz.R.anim.enter_from_left
                exit = com.teamx.equiz.R.anim.exit_to_left
                popEnter = com.teamx.equiz.R.anim.nav_default_pop_enter_anim
                popExit = com.teamx.equiz.R.anim.nav_default_pop_exit_anim
            }
        }
        FacebookSdk.sdkInitialize(requireContext())
        FacebookSdk.setApplicationId("428659309635685")



        mViewDataBinding.btnSc.setOnClickListener {
            ScLogin()
        }
        mViewDataBinding.btnLinkedin.setOnClickListener {
//            tiktok()
        }

        mViewDataBinding.btnInsta.setOnClickListener {
            fetchInstagramProfile()
        }

        mViewDataBinding.btnFb.setOnClickListener {
            fb()
        }

        imageFiles = ArrayList()

        sharedViewModel.setActiveUser("")

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }
        mViewDataBinding.btnChangePass.setOnClickListener {
            findNavController().navigate(
                R.id.changePassFragment, arguments, options
            )
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
//                                mViewDataBinding.recDocs.visibility = View.VISIBLE
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
                                .error(com.teamx.equiz.R.drawable.baseline_person)
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
                                    data.user.dateOfBirth?.replaceAfter("T", "")?.replace("T", "")
                                        .toString()
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
                        if (data.user?.image?.isNotEmpty() == true) {
                            Glide.with(mViewDataBinding.profilePicture.context)
                                .load(data.user.image).into(mViewDataBinding.profilePicture)

                            if (data.user.image.isNotEmpty()) {
                                Glide.with(mViewDataBinding.profilePicture.context)
                                    .load(data.user.image)
                                    .placeholder(com.teamx.equiz.R.drawable.baseline_person_white)
                                    .into(mViewDataBinding.profilePicture)
                            }

                            mViewDataBinding.userName.setText(data.user.name ?: "")
                            mViewDataBinding.phone.setText(data.user.phone ?: "")
                            mViewDataBinding.email.setText(data.user.email ?: "")
                            mViewDataBinding.dob.setText(data.user.dateOfBirth ?: "")

                            try {
                                val uData = PrefHelper.getInstance(requireActivity()).getUserData()
                                uData!!.user?.image = data.user.image
                                PrefHelper.getInstance(requireActivity()).setUserData(uData)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }



                            Log.d("TAG", "textttt: ${mViewDataBinding.dob.text}")


                            if (isAdded) {
                                mViewDataBinding.root.snackbar("Profile updated")
                            }


                        }

                        findNavController().navigate(
                            com.teamx.equiz.R.id.action_editProfileFragment_to_dashboardFragment,
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

//                        docsArrayList.clear()
//
                        data.data.forEach {
                            docsArrayList.add(IdentityDocument(null, it, null, it, null))
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
                    params.addProperty("bank_name", "imageUrl")
                    params.addProperty("account", "imageUrl")

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
                                com.teamx.equiz.R.id.otpPhoneFragment, bundle, options
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
                                com.teamx.equiz.R.id.otpEmailFragment, bundle, options
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

                    val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
                    val cursor =
                        requireActivity().contentResolver.query(uri, projection, null, null, null)
                    if (cursor != null && cursor.moveToFirst()) {
                        for (i in 0 until cursor.columnCount) {
                            val columnName = cursor.getColumnName(i)
                            val value = cursor.getString(i)
                            Log.d("Image Column", "$columnName: $value")
                        }
                        cursor.close()
                    }

                    Log.d("uploadImageArrayList", "exist: $projection")
                    Log.d("uploadImageArrayList", "exist: $cursor")

                    val str = "${requireContext().filesDir}/$index.jpg"

                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver, uri
                    )


                    val outputStream = FileOutputStream(str)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                    outputStream.close()

                    if (File(str).exists()) {
                        Log.d("uploadImageArrayList", "exist: $str")
                    }

                    imageFiles.add(File(str))

                }

                if (imageFiles.isNotEmpty()) {

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

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recDocs.layoutManager = linearLayoutManager

        docsAdapter = DocsAdapter(docsArrayList, this)
        mViewDataBinding.recDocs.adapter = docsAdapter

    }

    private val SNAPCHAT_LOGIN_REQUEST_CODE = 123

    private var webView: WebView? = null
    fun ScLogin() {
        mViewDataBinding.webView.visibility = View.VISIBLE
        mViewDataBinding.root.visibility = View.GONE

        /* SnapLogin.getAuthTokenManager(requireContext()).startTokenGrant()

        val mLoginStateChangedListener: OnLoginStateChangedListener =
            object : OnLoginStateChangedListener {
                override fun onLoginSucceeded() {
                    getUserDetails(requireContext())
                    // Here you could update UI to show login success
                }

                override fun onLoginFailed() {
                    // Here you could update UI to show login failure
                }

                override fun onLogout() {
                    // Here you could update UI to reflect logged out state
                }
            }*/


        val webView = mViewDataBinding.webView


        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

// Load Tawk.to chat widget URL
//        webView.loadUrl("https://tawk.to/chat/659eb44f8d261e1b5f519728/1hjpv0mb5")


//        webView = requireView().findViewById(com.teamx.equiz.R.id.webView)
//        webView.getSettings().setJavaScriptEnabled(true)
        /*  webView.webViewClient = object : WebViewClient() {
              override fun shouldOverrideUrlLoading(
                  view: WebView,
                  request: WebResourceRequest
              ): Boolean {
                  val url = request.url.toString()
                  if (url.startsWith(REDIRECT_URI)) {
                      val uri = Uri.parse(url)
                      val code = uri.getQueryParameter("code")
                      code?.let { exchangeAuthorizationCodeForAccessToken(it) }
                      return true
                  }
                  return super.shouldOverrideUrlLoading(view, request)
              }

              override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                  super.onPageStarted(view, url, favicon)
                  if (favicon != null) {
                  }
              }

              override fun onPageFinished(view: WebView, url: String) {
                  super.onPageFinished(view, url)
              }
          }

          val authUrl =
              "$SNAPCHAT_AUTH_URL?response_type=code&client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI"
          webView.loadUrl(authUrl)*/


        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (favicon != null) {
                    // Handle favicon
                }
            }

            @SuppressLint("LogNotTimber")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url?.startsWith(REDIRECT_URI) == true) {
                    val uri = Uri.parse(url)
                    val code = uri.getQueryParameter("code")
                    if (code != null) {
                        exchangeAuthorizationCodeForAccessToken(code)
                    }
                    return true
                }
                return super.shouldOverrideUrlLoading(view, url)
            }
        }

        val authUrl =
            "$SNAPCHAT_AUTH_URL?response_type=code&client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI"
        webView.loadUrl(authUrl)

    }

    var callbackManager: CallbackManager? = null

    fun loginToInstagram() {
        val callbackManager = CallbackManager.Factory.create()
        val loginManager = LoginManager.getInstance()

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // Handle successful login
                val accessToken = loginResult.accessToken
                val userId = accessToken.userId


                // Use the user's access token to make requests to the Instagram Graph API
                // For example, you can fetch the user's Instagram profile data
            }

            override fun onCancel() {
                // Handle canceled login
            }

            override fun onError(error: FacebookException) {
                // Handle login error
            }
        })

        // Start Instagram login flow
        loginManager.logInWithReadPermissions(requireActivity(), listOf("instagram_basic"))
    }


    private fun fb() {
        callbackManager = CallbackManager.Factory.create();

        val EMAIL = "email"

        var loginButton = mViewDataBinding.btnFb
        Arrays.asList(EMAIL)

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.d("TAG", "cakcebek: ")
                }

                override fun onError(error: FacebookException) {
                    Log.d("TAG", "onError: ${error.message}")
                }

                override fun onSuccess(result: LoginResult) {
                    Timber.tag("TAG").d("1stfbToken: ${result.accessToken}")


                    val idTokenFb = result.accessToken.token
                    Timber.tag("TAG").d("1stfbToken: $idTokenFb")


                }

            })


        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))



        LoginManager.getInstance()
            .retrieveLoginStatus(requireContext(), object : LoginStatusCallback {
                override fun onCompleted(accessToken: AccessToken) {
                    // User was previously logged in, can log them in directly here.
                    // If this callback is called, a popup notification appears that says
                    // "Logged in as <User Name>"

                    Timber.tag("TAG").d("fbToken: ${accessToken.token}")


                    val idTokenFb = accessToken.token.toString()


                }

                override fun onFailure() {
                    // No access token could be retrieved for the user
                }

                override fun onError(exception: Exception) {
                    // An error occurred
                }
            })


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }


    fun getUserDetails(context: Context) {
        val query = "{me{bitmoji{avatar},displayName}}"
        val is_logged_in = SnapLogin.isUserLoggedIn(context)
        Log.e("Is user logged in", "$is_logged_in")
        SnapLogin.fetchUserData(context, query, null, object : FetchUserDataCallback {
            override fun onSuccess(userDataResponse: UserDataResponse?) {
                if (userDataResponse != null) {
                    val data = userDataResponse.data.me
                    if (data != null) {
                        username.value = data.displayName
                        bitmoji.value = data.bitmojiData.avatar
                        Log.e(
                            "data", "Name: ${data.displayName}, avatar: ${data.bitmojiData.avatar}"
                        )
                    }
                }
            }

            override fun onFailure(isNetworkError: Boolean, statusCode: Int) {
                Log.e("Snapkit Failure", "network error: $isNetworkError, status code: $statusCode")
            }


        })
    }

    private fun exchangeAuthorizationCodeForAccessToken(code: String) {
        val client = OkHttpClient()
        val formBody: RequestBody =
            FormBody.Builder().add("grant_type", "authorization_code").add("code", code)
                .add("client_id", CLIENT_ID).add("redirect_uri", REDIRECT_URI).build()
        val request: Request = Request.Builder().url(SNAPCHAT_TOKEN_URL).post(formBody).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val json = response.body!!.string()
                try {
                    val jsonObject = JSONObject(json)
                    val accessToken = jsonObject.getString("access_token")
                    Log.d("SnapchatLogin", "Access Token: $accessToken")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

    override fun onRemoveClick(position: Int) {
        val abc = docsArrayList[position]
        docsArrayList.remove(abc)
        docsAdapter.notifyDataSetChanged()
    }


}

@Keep
open class Socials(
    open val link: String,
)

