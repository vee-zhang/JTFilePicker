package com.william.jtfilepicker

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.support.annotation.IntDef
import com.william.jtfilepicker.interfaces.OnFileIconLoadListener

/**
 * Created by william on 2018/3/2.
 */
class JTFilePicker private constructor(private val mContext: Any) {

    companion object {

        const val ACTIVITY = 0
        const val FRAGMENT = 1

        @Retention(AnnotationRetention.SOURCE)
        //这里指定int的取值只能是以下范围
        @IntDef(ACTIVITY, FRAGMENT)
        internal annotation class ContextType

        lateinit var mlistener: OnFileIconLoadListener
        fun from(activity: Activity, listener: OnFileIconLoadListener): JTFilePicker {
            mlistener = listener
            return JTFilePicker(activity)
        }

        fun from(fragment: Fragment, listener: OnFileIconLoadListener): JTFilePicker {
            mlistener = listener
            return JTFilePicker(fragment)
        }
        fun from(fragment: android.support.v4.app.Fragment, listener: OnFileIconLoadListener): JTFilePicker {
            mlistener = listener
            return JTFilePicker(fragment)
        }
    }

    fun open(requestCode: Int) {
        when (mContext) {
            is Activity -> mContext.startActivityForResult(Intent(mContext, FilePickerActivity::class.java), requestCode)
            is Fragment -> mContext.startActivityForResult(Intent(mContext.activity, FilePickerActivity::class.java), requestCode)
            is android.support.v4.app.Fragment -> mContext.startActivityForResult(Intent(mContext.activity,FilePickerActivity::class.java),requestCode)
        }
    }
}