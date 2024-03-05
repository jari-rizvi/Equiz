package com.teamx.equiz.ui.fragments.wallet


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.TextureView
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.common.io.ByteStreams
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getwalletData.Transaction
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentWalletBinding
import com.teamx.equiz.utils.Datess
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.checkerframework.checker.units.qual.s
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>() {

    override val layoutId: Int
        get() = com.teamx.equiz.R.layout.fragment_wallet
    override val viewModel: Class<WalletViewModel>
        get() = WalletViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var walletAdapter: WalletAdapter
    lateinit var walletArrayList: ArrayList<Transaction>
    private lateinit var options: NavOptions
    var cal = Calendar.getInstance()
    var GameModel: Game? = null
    lateinit var pdflink: InputStream
    var DatessModel: Datess? = null

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
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
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

        mViewDataBinding.textView9.setOnClickListener {
            if (isAdded) {
                findNavController().navigate(
                    R.id.action_walletFragment_to_referralFragment,
                    arguments,
                    options
                )
            }
        }

        mViewDataBinding.btnTopUp.setOnClickListener {
            findNavController().navigate(
                com.teamx.equiz.R.id.action_walletFragment_to_topupFragment,
                arguments,
                options
            )

        }


        DatessModel = Datess("", "")


        mViewDataBinding.btnFilter.setOnClickListener {
            DatePickerDialog().show()


            /*          Log.d("TAG", "click: ")

                      DatessModel?.let { it1 ->
                          DialogHelperClass.DatePickerDialog(requireContext(),
                              object : DialogHelperClass.Companion.DialogDateCallBack {
                                  override fun startDate(sDate: String) {


                                      DatessModel= Datess(sDate,"")
                                  }

                                  override fun endDate(eDate: String) {
                                      val dateSetListener =
                                          DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                                              cal.set(Calendar.YEAR, year)
                                              cal.set(Calendar.MONTH, monthOfYear)
                                              cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                                              DatessModel= Datess("",eDate)

                                          }

                                      DatePickerDialog(
                                          requireContext(), dateSetListener,
                                          // set DatePickerDialog to point to today's date when it loads up
                                          cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
                                      ).show()
                                  }

                                  override fun btncnfrm(sDate: String, eDate: String) {

                                  }


                              }, datess = it1
                          ).show()
                      }
          */


        }


        if (!mViewModel.getTransResponse.hasActiveObservers()) {
            mViewModel.getTransResponse.observe(requireActivity()) {
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


                            CoroutineScope(Dispatchers.Main).launch {

                                val inputStream: InputStream =
                                    data.byteStream()  // da Your InputStream containing the PDF file
                                val fileName = "TransactionHistory.pdf"

                                downloadPDF(inputStream, fileName)
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
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getTransResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        mViewModel.getWallet()



        if (!mViewModel.getwalletResponse.hasActiveObservers()) {
            mViewModel.getwalletResponse.observe(requireActivity()) {
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
                            walletArrayList.clear()
                            mViewDataBinding.shimmerLayout.visibility = View.GONE
                            mViewDataBinding.root.visibility = View.VISIBLE

                            mViewDataBinding.shimmerLayout.stopShimmer()
                            val formattedNumber = String.format("%.2f", data.data)
                            mViewDataBinding.textView10.text = formattedNumber + " Points"

                            Log.d("TAG", "getWalletData:  ${data.data}")

                            data.transactions.forEach {
                                walletArrayList.add(it)
                                mViewDataBinding.textView11.text =
                                    it.points.toString() + " Expires at " + it.expiresAt.substringBefore(
                                        "T"
                                    )

                            }

                            walletAdapter.notifyDataSetChanged()


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
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getwalletResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        walletRecyclerview()


        mViewDataBinding.swiperefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mViewDataBinding.swiperefresh.isRefreshing = false
            RearrangeData()
        })

    }


    private fun RearrangeData() {
        mViewModel.getWallet()
    }


    private fun walletRecyclerview() {
        walletArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.walletrecycler.layoutManager = linearLayoutManager

        walletAdapter = WalletAdapter(walletArrayList)
        mViewDataBinding.walletrecycler.adapter = walletAdapter

    }

    private fun downloadPDF(inputStream: InputStream, fileName: String) {
        try {
            val outputFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )
            val outputStream = FileOutputStream(outputFile)
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            Log.d("downloadPDF", "File downloaded successfully: ${outputFile.absolutePath}")
            showToast("Downloaded successfully. Opening PDF...")

            mViewDataBinding.pdfViewver.visibility = View.VISIBLE

            displayPDF(outputFile)
        } catch (e: Exception) {
            Log.e("downloadPDF", "Error downloading file: ${e.message}")
            showToast("Failed to download PDF")
        }
    }

    private fun displayPDF(fileUri: File) {
        mViewDataBinding.pdfViewver.fromFile(fileUri)
            .defaultPage(0)
            .enableSwipe(true)
            .enableDoubletap(true)
            .swipeHorizontal(false)
            .onLoad(object : OnLoadCompleteListener {
                override fun loadComplete(nbPages: Int) {
                    Log.d("nbPages", "displayPDF: ")

                }
            })
            .onPageChange { page, pageCount -> }
            .scrollHandle(DefaultScrollHandle(requireContext()))
            .enableAnnotationRendering(true)
            .password(null)
            .pageFitPolicy(FitPolicy.WIDTH)
            .load()
    }

    lateinit var startdateTxt: TextView
    lateinit var enddateTxt: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    fun DatePickerDialog(): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.date_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)

        startdateTxt = dialog.findViewById(R.id.startdob)
        enddateTxt = dialog.findViewById(R.id.enddob)
        val btn = dialog.findViewById<TextView>(R.id.btnCnfirm)
        val closeDialog = dialog.findViewById<ImageView>(R.id.closeDialog)

        val currentDate = LocalDate.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        startDateStr = currentDate.format(formatter)
        startdateTxt.text = startDateStr
        endDateStr = startDateStr
        enddateTxt.text = endDateStr


        startdateTxt.setOnClickListener {
            ifStart = true
            picker()
        }
        enddateTxt.setOnClickListener {
            ifStart = false
            picker()
        }

        btn.setOnClickListener {
            mViewModel.getTrans(startDateStr, endDateStr)
            dialog.dismiss()

        }

        closeDialog.setOnClickListener {
            dialog.dismiss()
        }



        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

    var ifStart = false
    var startDateStr = ""
    var endDateStr = ""

    fun picker() {

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                Log.d("pickerd", "picker: ${year}")
                Log.d("pickerd", "picker: ${monthOfYear + 1}")
                Log.d("pickerd", "picker: ${dayOfMonth}")

                val inputFormat = SimpleDateFormat("d M yyyy", Locale.getDefault())
                val date = inputFormat.parse("$dayOfMonth ${monthOfYear + 1} $year")

                val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                val formattedDate = outputFormat.format(date)

                if (ifStart) {

                    startDateStr = formattedDate
                    startdateTxt.text = startDateStr
                } else {
                    endDateStr = formattedDate
                    enddateTxt.text = endDateStr
                }


            }

        DatePickerDialog(
            requireContext(), dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


}