package com.application.pepul.ui.fragment

import android.Manifest
import android.os.Build
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.application.pepul.R
import com.application.pepul.ui.activity.BaseActivity
import com.application.pepul.util.Constants
import com.application.pepul.util.ToastUtils
import com.application.pepul.util.UtilInterface
import com.application.pepul.util.UtilsDefault

open class BaseFragment(layoutId:Int) : Fragment(layoutId), UtilInterface {


    override fun toast(message: String) {
        ToastUtils.show(requireContext(), message)
    }

    override fun requestPermission(
        vararg permissions: String,
        result: (permissionGranted: Boolean) -> Unit
    ) {
        (activity as UtilInterface).requestPermission(*permissions, result = result)
    }

    override fun showProgress() {
        (activity as BaseActivity?)?.showProgress()
    }

    override fun isProgressShowing(): Boolean = (activity as BaseActivity?)?.isProgressShowing() ?: false

    override fun dismissProgress() {
        (activity as BaseActivity?)?.dismissProgress()
    }


    fun imagePermission(action: () -> Unit){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA){
                if(it){
                    action()
                }else{
                    toast("Need Camera and Storage Permission!")
                }
            }
        }else{
            action()
        }
    }

}