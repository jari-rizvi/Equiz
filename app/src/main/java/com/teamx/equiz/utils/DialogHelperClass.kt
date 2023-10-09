package com.teamx.equiz.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.TextView
import com.teamx.equiz.R

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

        fun InviteDialog(context: Context, dialogLoginCallBack: DialogInviteAnotherCallBack, boo: Boolean) {
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

   /*         val cancelBtn = dialog.findViewById<TextView>(R.id.cancelBtn)
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }*/

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }






    }
}