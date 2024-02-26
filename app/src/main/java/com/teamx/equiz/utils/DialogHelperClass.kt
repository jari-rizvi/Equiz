package com.teamx.equiz.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import com.squareup.picasso.Picasso
import com.teamx.equiz.R
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.games.games.dialogShareGame
import com.teamx.equiz.ui.fragments.collectPrice.ClaimInterfaceCallback
import com.teamx.equiz.ui.fragments.dashboard.GamesUID2

class DialogHelperClass {
    companion object {

        fun loadingDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_layout_loading)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            return dialog
        }

        fun errorDialog(context: Context, errorMessage: String) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_layout_error)
            val errorTextMessage = dialog.findViewById<TextView>(R.id.tv_error_message)
            errorTextMessage.setText(errorMessage)
            if (errorMessage.contains("job was cancelled", true)) {
                return
            }
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        interface DialogInviteAnotherCallBack {
            fun InviteClicked()
        }

        interface DialogLessAmountCallBack {
            fun Topup()
        }

        interface DialogDateCallBack {
            fun startDate(sDate: String)
            fun endDate(eDate: String)
            fun btncnfrm(sDate: String, eDate: String)
        }

        interface OrderCompleteCallBack {
            fun InviteClicked()
        }

        fun InviteDialog(
            context: Context, dialogLoginCallBack: DialogInviteAnotherCallBack, boo: Boolean
        ) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.invite_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )


            val removeBtn = dialog.findViewById<TextView>(R.id.btnInvite)
            removeBtn.setOnClickListener {
                if (boo) {
                    dialogLoginCallBack.InviteClicked()
                } else {
                }
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        fun topUpDialog(
            context: Context,
            dialogLoginCallBack: DialogInviteAnotherCallBack,
            boo: Boolean,
            price: String
        ) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.top_up_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)


            val removeBtn = dialog.findViewById<TextView>(R.id.btnInvite)
            removeBtn.setOnClickListener {
                if (boo) {
                    dialogLoginCallBack.InviteClicked()
                } else {
                }
                dialog.dismiss()
            }

            val cancelBtn = dialog.findViewById<TextView>(R.id.cancelBtn)

            cancelBtn.text = "You Earned ${price} Points "/*    cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }*/

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        fun lessPointsDialog(
            context: Context, lessAmountCallBack: DialogLessAmountCallBack, boo: Boolean
        ) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.amount_less_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )


            val removeBtn = dialog.findViewById<TextView>(R.id.top_up)
            val cancelBtn = dialog.findViewById<TextView>(R.id.cancelBtn)
            removeBtn.setOnClickListener {
                if (boo) {
                    lessAmountCallBack.Topup()
                } else {
                }
                dialog.dismiss()
            }

            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        fun claimPrizeDialog(
            context: Context,
            dialogLoginCallBack: ClaimInterfaceCallback,
            boo: Boolean,
            price: String
        ) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.social_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )


            val btn_social_fb1 = dialog.findViewById<ImageView>(R.id.btn_social_fb1)
            val btn_social_fb2 = dialog.findViewById<ImageView>(R.id.btn_social_fb2)
            val btn_social_fb3 = dialog.findViewById<ImageView>(R.id.btn_social_fb3)
            val btn_social_fb4 = dialog.findViewById<ImageView>(R.id.btn_social_fb4)
            val btn_social_fb = dialog.findViewById<ImageView>(R.id.btn_social_fb)


            btn_social_fb1.setOnClickListener {
                if (boo) {
                    dialogLoginCallBack.onClickItem(1)
                } else {
                }
                dialog.dismiss()
            }
            btn_social_fb2.setOnClickListener {
                if (boo) {
                    dialogLoginCallBack.onClickItem(2)
                } else {
                }
                dialog.dismiss()


            }
            btn_social_fb3.setOnClickListener {
                if (boo) {
                    dialogLoginCallBack.onClickItem(3)
                } else {
                }
                dialog.dismiss()
            }
            btn_social_fb4.setOnClickListener {
                if (boo) {
                    dialogLoginCallBack.onClickItem(4)
                } else {
                }
                dialog.dismiss()
            }
            btn_social_fb.setOnClickListener {
                if (boo) {
                    dialogLoginCallBack.onClickItem(5)
                } else {
                }
                dialog.dismiss()
            }



            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        fun orderCompleteDialog(
            context: Context,
            dialogLoginCallBack: OrderCompleteCallBack,
            boo: Boolean,
            price: String
        ) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.complete_order_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )


            val removeBtn = dialog.findViewById<TextView>(R.id.btnInvite)
            removeBtn.setOnClickListener {
                if (boo) {
                    dialogLoginCallBack.InviteClicked()
                } else {
                }
                dialog.dismiss()
            }

            val cancelBtn = dialog.findViewById<TextView>(R.id.cancelBtn)

            cancelBtn.text =
                "You Have Successfully your Confirm \nPayment Send!\n Order Id : $price"/*    cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }*/

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        private lateinit var composeView: ComposeView
        fun shareGameResultDialog(
            context: Context,
            dialogLoginCallBack: DialogInviteAnotherCallBack,
            boo: Boolean,
            price: String,
            total: Int,
            right: Int,
            time: Int,
            gameName: String,
            onContinueClicked: (i: Int) -> Unit
        ) {


            val dialog = Dialog(context)

            dialog.setContentView(ComposeView(context).also {
                composeView = it
            })
            composeView.setContent {
                dialogShareGame(
                    total,
                    right,
                    20,
                    returnGameName(gameName.toString()),
                    returnGameIcon(gameName.toString())
                ) { i ->
                    when (i) {
                        1 -> {
                            //                            findNavController().popBackStack()
                        }

                        2 -> {
//                            findNavController().navigate(R.id.dashboardFragment, arguments, options)
                        }

                        3 -> {

                        }

                        else -> {

                        }
                    }
                }
            }


            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        interface LogoutCallBack {
            fun OkClick()
        }

        fun LogoutDialog(context: Context, dialogLogoutCallBack: LogoutCallBack, boo: Boolean) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.logout_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )


            val removeBtn = dialog.findViewById<TextView>(R.id.btnOk)
            val cnclBtn = dialog.findViewById<TextView>(R.id.btnCncl)

            cnclBtn.setOnClickListener {
                dialog.dismiss()
            }
            removeBtn.setOnClickListener {
                if (boo) {
                    dialogLogoutCallBack.OkClick()
                } else {

                }
                dialog.dismiss()
            }


            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }


        /*  interface DialogCallBackOtpVerify {
              fun onOtpClick()
          }

          fun verifyOtpDialog(context: Context, dialogCallBack: DialogCallBackOtpVerify): Dialog {
              val dialog = Dialog(context)
              dialog.setContentView(R.layout.verify_otp_dialog)
              dialog.window!!.setLayout(
                  WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
              )
              dialog.setCancelable(false)
              val send = dialog.findViewById<TextView>(R.id.sendOtp)
              val cancelBtn = dialog.findViewById<TextView>(R.id.btnCancel)

              cancelBtn.setOnClickListener {
                  dialog.dismiss()
              }
              send.setOnClickListener {
                  dialogCallBack.onOtpClick()
                  dialog.dismiss()
              }

              dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

              return dialog
          }

  */


        @Composable
        fun returnGameIcon(enumNumberEnum: String): Painter {


            return when (enumNumberEnum) {

                GamesUID2.AdditionAddiction.name -> {

                    painterResource(R.drawable.addition_icon)


                }

                GamesUID2.BirdWatching.name -> {
                    painterResource(R.drawable.bird_favicon)

                }


                GamesUID2.ColorDeception.name -> {
                    painterResource(R.drawable.colorofdeception_icon)

                }

                /*GamesUID2.Tetris.name -> {
                    painterResource(R.drawable.tetris_icon)

                }*/

                GamesUID2.Concentration.name -> {

                    painterResource(R.drawable.concentration_icon)
                }

                GamesUID2.CardCalculation.name -> {
                    painterResource(R.drawable.cardscalculations_icon)

                }

                GamesUID2.Flick.name -> {
                    painterResource(R.drawable.flick_icon)

                }

                GamesUID2.FollowTheLeader.name -> {
                    painterResource(R.drawable.follow_the_leder)

                }

                GamesUID2.UnfollowTheLeader.name -> {
                    painterResource(R.drawable.follow_the_leder)

                }

                GamesUID2.GuessTheFlag.name -> {
                    painterResource(R.drawable.guestheflag_icon)
                }

                GamesUID2.HighLow.name -> {
                    painterResource(R.drawable.highorlow_icon)

                }

                GamesUID2.MakeTen.name -> {
                    painterResource(R.drawable.maketen_icon)

                }

                GamesUID2.MissingPiece.name -> {
                    painterResource(R.drawable.missingpieces_icon)

                }


                GamesUID2.QuickEye.name -> {
                    painterResource(R.drawable.quickeye_icon)

                }

                GamesUID2.RainFall.name -> {
                    painterResource(R.drawable.rainfall_icon)

                }

                GamesUID2.RapidSorting.name -> {
                    painterResource(R.drawable.rapid_sorting_icon)

                }

                GamesUID2.ReverseRps.name -> {
                    painterResource(R.drawable.reverserps_icon)

                }

                GamesUID2.Simplicity.name -> {
                    painterResource(R.drawable.simplicity_icon)

                }

                GamesUID2.SpinningBlock.name -> {
                    painterResource(R.drawable.spinthewheel_icon)

                }

                GamesUID2.ShapeDeception.name -> {
                    painterResource(R.drawable.shapedeception_icon)
                }

                GamesUID2.TapTheColor.name -> {
                    painterResource(R.drawable.tapthecolor_icon)

                }

                GamesUID2.TouchTheNum.name -> {
                    painterResource(R.drawable.touchthenumber_icon)

                }

                GamesUID2.TouchTheNumPlus.name -> {
                    painterResource(R.drawable.touchthenumbers_icon)

                }

                GamesUID2.WeatherCast.name -> {
                    painterResource(R.drawable.weathercast_icon)

                }


                else -> {
                    painterResource(R.drawable.weathercast_icon)
                }
            }


        }

        fun returnGameIconRes(enumNumberEnum: String): Int {


            return when (enumNumberEnum) {

                GamesUID2.AdditionAddiction.name -> {

                    R.drawable.addition_icon


                }

                GamesUID2.BirdWatching.name -> {
                    R.drawable.bird_favicon

                }


                GamesUID2.ColorDeception.name -> {
                    R.drawable.colorofdeception_icon

                }

                /*GamesUID2.Tetris.name -> {
                   R.drawable.tetris_icon

                }*/

                GamesUID2.Concentration.name -> {

                    R.drawable.concentration_icon
                }

                GamesUID2.CardCalculation.name -> {
                    R.drawable.cardscalculations_icon

                }

                GamesUID2.Flick.name -> {
                    R.drawable.flick_icon

                }

                GamesUID2.FollowTheLeader.name -> {
                    R.drawable.follow_the_leder

                }

                GamesUID2.UnfollowTheLeader.name -> {
                    R.drawable.follow_the_leder

                }

                GamesUID2.GuessTheFlag.name -> {
                    R.drawable.guestheflag_icon
                }

                GamesUID2.HighLow.name -> {
                    R.drawable.highorlow_icon

                }

                GamesUID2.MakeTen.name -> {
                    R.drawable.maketen_icon

                }

                GamesUID2.MissingPiece.name -> {
                    R.drawable.missingpieces_icon

                }


                GamesUID2.QuickEye.name -> {
                    R.drawable.quickeye_icon

                }

                GamesUID2.RainFall.name -> {
                    R.drawable.rainfall_icon

                }

                GamesUID2.RapidSorting.name -> {
                    R.drawable.rapid_sorting_icon

                }

                GamesUID2.ReverseRps.name -> {
                    R.drawable.reverserps_icon

                }

                GamesUID2.Simplicity.name -> {
                    R.drawable.simplicity_icon

                }

                GamesUID2.SpinningBlock.name -> {
                    R.drawable.spinthewheel_icon

                }

                GamesUID2.ShapeDeception.name -> {
                    R.drawable.shapedeception_icon
                }

                GamesUID2.TapTheColor.name -> {
                    R.drawable.tapthecolor_icon

                }

                GamesUID2.TouchTheNum.name -> {
                    R.drawable.touchthenumber_icon

                }

                GamesUID2.TouchTheNumPlus.name -> {
                    R.drawable.touchthenumbers_icon

                }

                GamesUID2.WeatherCast.name -> {
                    R.drawable.weathercast_icon

                }


                else -> {
                    R.drawable.weathercast_icon
                }
            }


        }

        fun returnGameName(enumNumberEnum: String): String {


            return when (enumNumberEnum) {

                GamesUID2.AdditionAddiction.name -> {
                    "Addition Addiction"

                }

                GamesUID2.BirdWatching.name -> {
                    "Bird Watching"

                }

                GamesUID2.Matching.name -> {
                    "Matching"

                }

                GamesUID2.Operations.name -> {

                    "Operations"
                }

                GamesUID2.ColorDeception.name -> {
                    "ColorDeception"

                }

                /* GamesUID2.Tetris.name -> {
                     "Tetris"

                 }*/


                GamesUID2.CardCalculation.name -> {
                    "Card Calculation"

                }

                GamesUID2.Concentration.name -> {
                    "Concentration"

                }

                GamesUID2.Flick.name -> {
                    "Flick"

                }

                GamesUID2.FollowTheLeader.name -> {
                    "Follow The Leader"

                }

                GamesUID2.UnfollowTheLeader.name -> {
                    "Unfollow The Leader"

                }

                GamesUID2.GuessTheFlag.name -> {
                    "Guess The Flag"
                }

                GamesUID2.HighLow.name -> {
                    "High Low"

                }

                GamesUID2.MakeTen.name -> {
                    "Make Ten"

                }

                GamesUID2.MissingPiece.name -> {
                    "Missing Piece"

                }


                GamesUID2.QuickEye.name -> {
                    "Quick Eye"

                }

                GamesUID2.RainFall.name -> {
                    "Rain Fall"

                }

                GamesUID2.RapidSorting.name -> {
                    "Rapid Sorting"

                }

                GamesUID2.ReverseRps.name -> {
                    "Reverse RPS"

                }

                GamesUID2.Simplicity.name -> {
                    "Simplicity"

                }

                GamesUID2.SpinningBlock.name -> {
                    "Spinning Block"

                }

                GamesUID2.ShapeDeception.name -> {
                    "Shape Deception"
                }

                GamesUID2.TapTheColor.name -> {
                    "Tap The Color"

                }

                GamesUID2.TouchTheNum.name -> {
                    "Touch The Number"

                }

                GamesUID2.TouchTheNumPlus.name -> {
                    "Touch The Number Plus"

                }

                GamesUID2.WeatherCast.name -> {
                    "Weather Cast"

                }


                else -> {
                    "Weather Cast"
                }
            }


        }


        interface DialogCallBackSignIn {
            fun onSignInClick1()
            fun onSignUpClick1()
        }

        interface DeleteUserDialogCallBack {
            fun onSignInClick1()
            fun onSignUpClick1()
        }

        interface ChickenDialogCallBack {
            fun onCloseClick()
        }

        var dialog: Dialog? = null
        fun signUpLoginDialog(context: Context, dialogCallBack: DialogCallBackSignIn): Dialog {
            if (dialog == null) {

                dialog = Dialog(context)
            } else {
                if (dialog!!.isShowing) {
                    dialog?.dismiss()
                    dialog!!.show()
                }

            }
            dialog?.setContentView(R.layout.signup_signin_dialog_equiz)
            dialog?.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
//            dialog.setCancelable(false)
            val signIn = dialog?.findViewById<TextView>(R.id.signIn)
            val signUp = dialog?.findViewById<TextView>(R.id.signUp)

            signUp?.setOnClickListener {
                dialogCallBack.onSignUpClick1()
                dialog?.dismiss()
            }
            signIn?.setOnClickListener {
                dialogCallBack.onSignInClick1()
                dialog?.dismiss()
            }

            dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return dialog!!
        }


        fun deleteUserDialog(context: Context, dialogCallBack: DeleteUserDialogCallBack): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.delete_user_layout)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val cancelBtn = dialog.findViewById<TextView>(R.id.cancelBtn)
            val signUp = dialog.findViewById<TextView>(R.id.signUp)

            signUp.setOnClickListener {
                dialogCallBack.onSignUpClick1()
                dialog.dismiss()
            }
            cancelBtn.setOnClickListener {

                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return dialog
        }

        fun unsubUserDialog(context: Context, dialogCallBack: DeleteUserDialogCallBack): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.unsub_user_layout)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val cancelBtn = dialog.findViewById<TextView>(R.id.cancelBtn)
            val signUp = dialog.findViewById<TextView>(R.id.signUp)

            signUp.setOnClickListener {
                dialogCallBack.onSignUpClick1()
                dialog.dismiss()
            }
            cancelBtn.setOnClickListener {

                dialog.dismiss()
            }

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return dialog
        }

        fun UserStatsDialog(
            context: Context, dialogCallBack: ChickenDialogCallBack, gamesModel: Game
        ): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.user_stats_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val cancelBtn = dialog.findViewById<TextView>(R.id.btnClose)
            val name = dialog.findViewById<TextView>(R.id.textView75)
            val w_rank = dialog.findViewById<TextView>(R.id.textView76)
            val img = dialog.findViewById<ImageView>(R.id.img)
            val speedProgress = dialog.findViewById<ProgressBar>(R.id.simpleProgressBar)
            val judgeProgress = dialog.findViewById<ProgressBar>(R.id.simpleProgressBar1)
            val calProgress = dialog.findViewById<ProgressBar>(R.id.simpleProgressBar2)
            val accuracyProgress = dialog.findViewById<ProgressBar>(R.id.simpleProgressBar3)
            val obsProgress = dialog.findViewById<ProgressBar>(R.id.simpleProgressBar4)
            val memoryProgress = dialog.findViewById<ProgressBar>(R.id.simpleProgressBar5)

            name.text = gamesModel.userId.name
            w_rank.text = gamesModel.rank.toString()

            try {
                Picasso.get().load(gamesModel.userId.image).placeholder(R.drawable.baseline_person)
                    .error(R.drawable.baseline_person).resize(500, 500).into(img)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val speed = gamesModel.speed.toDouble() / gamesModel.level.Range.toDouble() * 100
            speedProgress.secondaryProgress = speed.toInt()


            val judgment = gamesModel.judgment.toDouble() / gamesModel.level.Range.toDouble() * 100
            judgeProgress.secondaryProgress = judgment.toInt()

            val calul = gamesModel.calculation.toDouble() / gamesModel.level.Range.toDouble() * 100
            calProgress.secondaryProgress = calul.toInt()

            val acc = gamesModel.accuracy.toDouble() / gamesModel.level.Range.toDouble() * 100
            accuracyProgress.secondaryProgress = acc.toInt()

            val observation =
                gamesModel.observation.toDouble() / gamesModel.level.Range.toDouble() * 100
            obsProgress.secondaryProgress = observation.toInt()

            val memory = gamesModel.memory.toDouble() / gamesModel.level.Range.toDouble() * 100
            memoryProgress.secondaryProgress = memory.toInt()


            cancelBtn.setOnClickListener {
                dialogCallBack.onCloseClick()
                dialog.dismiss()
            }


            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return dialog
        }


        @SuppressLint("SetTextI18n")
        fun chickenDialog(
            context: Context, dialogCallBack: ChickenDialogCallBack, gamesModel: Game
        ): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.level_chicken_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val cancelBtn = dialog.findViewById<TextView>(R.id.btnClose)
            val speed = dialog.findViewById<TextView>(R.id.textView67)
            val judgment = dialog.findViewById<TextView>(R.id.textView68)
            val cal = dialog.findViewById<TextView>(R.id.textView70)
            val accuracy = dialog.findViewById<TextView>(R.id.textView71)
            val observation = dialog.findViewById<TextView>(R.id.textView72)
            val memory = dialog.findViewById<TextView>(R.id.textView73)


            speed.text = gamesModel.speed.toString() + "/" + gamesModel.level.Range
            judgment.text = gamesModel.judgment.toString() + "/" + gamesModel.level.Range
            cal.text = gamesModel.calculation.toString() + "/" + gamesModel.level.Range
            accuracy.text = gamesModel.accuracy.toString() + "/" + gamesModel.level.Range
            observation.text = gamesModel.observation.toString() + "/" + gamesModel.level.Range
            memory.text = gamesModel.observation.toString() + "/" + gamesModel.level.Range


            cancelBtn.setOnClickListener {
                dialogCallBack.onCloseClick()
                dialog.dismiss()
            }


            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return dialog
        }

        @SuppressLint("SetTextI18n")
        fun DatePickerDialog(
            context: Context, dateCallBack: DialogDateCallBack, datess: Datess
        ): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.date_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)

            val startdate = dialog.findViewById<TextView>(R.id.startdob)
            val enddate = dialog.findViewById<TextView>(R.id.enddob)
            val btn = dialog.findViewById<TextView>(R.id.btnCnfirm)


            startdate.text = datess.sDate
            enddate.text = datess.eDate

            startdate.setOnClickListener {
                dateCallBack.startDate(startdate.text.toString())

            }
            enddate.setOnClickListener {
                dateCallBack.endDate(enddate.text.toString())
            }

            btn.setOnClickListener {
                dateCallBack.btncnfrm(startdate.text.toString(), enddate.text.toString())
            }


            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            return dialog
        }


        /*  fun DatePickerDialog(
              context: Context, dateCallBack: DialogDateCallBack, datess: Datess
          ) {
              val dialog = Dialog(context)
              dialog.setContentView(R.layout.date_dialog)
              dialog.window!!.setLayout(
                  WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
              )




              val startdate = dialog.findViewById<TextView>(R.id.startdob)
              val enddate = dialog.findViewById<TextView>(R.id.enddob)
              val btn = dialog.findViewById<TextView>(R.id.btnCnfirm)


              startdate.text = datess.sDate
              enddate.text = datess.eDate

              startdate.setOnClickListener {
                  dateCallBack.startDate(startdate.text.toString())

              }
              enddate.setOnClickListener {
                  dateCallBack.endDate(enddate.text.toString())
              }

              btn.setOnClickListener {
                  dateCallBack.btncnfrm(startdate.text.toString(),enddate.text.toString())
              }

              dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
              dialog.show()
          }*/

    }


}

data class Datess(var sDate: String, var eDate: String)