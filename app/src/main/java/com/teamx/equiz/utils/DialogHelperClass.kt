package com.teamx.equiz.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import com.teamx.equiz.R
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
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        interface DialogInviteAnotherCallBack {
            fun InviteClicked()
        }

        interface OrderCompleteCallBack {
            fun InviteClicked()
        }

        fun InviteDialog(
            context: Context,
            dialogLoginCallBack: DialogInviteAnotherCallBack,
            boo: Boolean
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

            cancelBtn.text = "You Earned ${price} Points "
            /*    cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }*/

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
                "You Have Successfully your Confirm \nPayment Send!\n Order Id : $price"
            /*    cancelBtn.setOnClickListener {
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
            price: String, total: Int,
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
        private fun returnGameIcon(enumNumberEnum: String): Painter {


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

                GamesUID2.Tetris.name -> {
                    painterResource(R.drawable.tetris_icon)

                }

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
                    painterResource(R.drawable.colorofdeception_icon)
                }

                GamesUID2.TapTheColor.name -> {
                    painterResource(R.drawable.tapthecolor_icon)

                }

                GamesUID2.TouchTheNum.name -> {
                    painterResource(R.drawable.touchthenumber_icon)

                }

                GamesUID2.TouchTheNumPlus.name -> {
                    painterResource(R.drawable.touchthenumbers_icon_plus)

                }

                GamesUID2.WeatherCast.name -> {
                    painterResource(R.drawable.weathercast_icon)

                }


                else -> {
                    painterResource(R.drawable.weathercast_icon)
                }
            }


        }

        private fun returnGameName(enumNumberEnum: String): String {


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

                GamesUID2.Tetris.name -> {
                    "Tetris"

                }


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
                    "Un Follow The Leader"

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
                    "Touch The Num"

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
    }


}