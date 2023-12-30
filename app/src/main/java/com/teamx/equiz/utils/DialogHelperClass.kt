package com.teamx.equiz.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import com.teamx.equiz.R
import com.teamx.equiz.ui.fragments.collectPrice.ClaimInterfaceCallback

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
            price: String
        ) {
            ComposeView(context).also {
                composeView = it
            }
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.top_up_dialog)
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

            cancelBtn.text = "You Earned ${price} Points "
            /*    cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }*/

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



    }
}