package com.teamx.equiz.ui.fragments.wallet


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.TextureView
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.NavDeepLinkRequest.Builder.Companion.fromUri
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
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
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.checkerframework.checker.units.qual.s
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.text.SimpleDateFormat
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
            findNavController().navigate(
                com.teamx.equiz.R.id.action_walletFragment_to_referralFragment,
                arguments,
                options
            )
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
//                            val inputStream: InputStream = data

                            mViewDataBinding.pdfViewver.visibility = View.VISIBLE

                            displayPDF(data.byteStream())


//
//                            val pdfFile = File(data.string())
//
//                            val extractedText = extractTextFromPDF(pdfFile)
//                            Log.d("getTransResponse", "dataRecive1: ${extractedText}")

//                            val decodedBytes = decodeBase64(data.string())
//                            val decodedString = decodedBytes.toString(Charset.defaultCharset())
//
//
//                            mViewDataBinding.webView.settings.javaScriptEnabled = true
//                            mViewDataBinding.webView.settings.allowFileAccessFromFileURLs = true
//                            mViewDataBinding.webView.settings.allowUniversalAccessFromFileURLs = true
//                            mViewDataBinding.webView.loadUrl("data:application/pdf;base64,${data.string()}")
//
//                            Log.d("getTransResponse", "dataRecive1: ${decodedString}")

//                            val pdfImageView = mViewDataBinding.imageView3
//                            val pdfBitmap = decodePDFDataToBitmap(data.toString())
//                            pdfImageView.setImageBitmap(pdfBitmap)

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
                            mViewDataBinding.textView10.text = data.data.toString() + " Points"

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


    fun extractTextFromPDF(pdfFile: File): String {
        val document = PDDocument.load(pdfFile)
        val pdfStripper = PDFTextStripper()
        val text = pdfStripper.getText(document)
        document.close()
        return text
    }

    private fun decodePDFDataToBitmap(pdfData: String): Bitmap {
        // Implement PDF decoding logic here
        // For demonstration purposes, we'll just return a placeholder bitmap
        return BitmapFactory.decodeResource(resources, R.drawable.facebookrain)
    }

    fun decodeBase64(encodedString: String): ByteArray {
        return Base64.decode(encodedString, Base64.DEFAULT)
    }

    private fun displayPDF(fileUri: InputStream) {
        mViewDataBinding.pdfViewver.fromStream(fileUri)
            .defaultPage(0)
            .enableSwipe(true)
            .enableDoubletap(true)
            .swipeHorizontal(false)
            .onLoad { nbPages -> }
            .onPageChange { page, pageCount -> }
            .scrollHandle(DefaultScrollHandle(requireContext()))
            .enableAnnotationRendering(true)
            .password(null)
            .pageFitPolicy(FitPolicy.WIDTH)
            .load()
    }

    lateinit var startdateTxt: TextView
    lateinit var enddateTxt: TextView
    fun DatePickerDialog(): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.date_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)

        startdateTxt = dialog.findViewById<TextView>(R.id.startdob)
        enddateTxt = dialog.findViewById<TextView>(R.id.enddob)
        val btn = dialog.findViewById<TextView>(R.id.btnCnfirm)


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