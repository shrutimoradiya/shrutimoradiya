package com.sampsolution.contactlessdining.view.dialog

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sampsolution.contactlessdining.R
import com.sampsolution.contactlessdining.databinding.DialogLogoutBinding
import com.sampsolution.contactlessdining.databinding.DialogNotificationBinding
import com.sampsolution.contactlessdining.databinding.DialogScanMenuItemBinding
import com.sampsolution.contactlessdining.model.menuModel.Menus
import com.sampsolution.contactlessdining.model.menuModel.Options
import com.sampsolution.contactlessdining.utils.Constant
import com.sampsolution.contactlessdining.utils.SharedPref
import com.sampsolution.contactlessdining.view.activity.MainActivity
import com.sampsolution.contactlessdining.view.adapter.SizeAdapter

data class LogOutDialog(
    val context: AppCompatActivity,
) : BottomSheetDialog(context) {
    private var dialogbinding: DialogLogoutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogbinding = DialogLogoutBinding.inflate(LayoutInflater.from(context))

        dialogbinding?.let { binding ->
            setContentView(binding.root)

            hideSystemUI()

            binding.btnYes.setOnClickListener {
                dismiss()
                Constant.saveData(context, Constant.QRCODE, null)
                SharedPref(context).isLogin = false
                context.finish()
                context.startActivity(Intent(context, MainActivity::class.java))
            }

            binding.btnNo.setOnClickListener { dismiss() }

        }

        setCanceledOnTouchOutside(true)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        window?.attributes = layoutParams
//        window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        (dialogbinding!!.root.parent as View).setBackgroundColor(
            context.resources.getColor(android.R.color.transparent)
        )
    }

//


    private fun hideSystemUI() {
        window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        window?.let {
            WindowInsetsControllerCompat(
                it,
                window!!.decorView.findViewById(android.R.id.content)
            ).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())

                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}

