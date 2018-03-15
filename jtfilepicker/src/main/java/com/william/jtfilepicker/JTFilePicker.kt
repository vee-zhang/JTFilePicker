package com.william.jtfilepicker

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Environment
import android.support.annotation.IntDef
import com.william.jtfilepicker.interfaces.OnFileIconLoadListener

/**
 * Created by william on 2018/3/2.
 */
class JTFilePicker private constructor(private val mContext: Any) {

    companion object {

        const val ACTIVITY = 0
        const val FRAGMENT = 1

        val fileList = ArrayList<String>()

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

    var rootDir = Environment.getExternalStorageDirectory().path

    fun setRoot(rootDir: String): JTFilePicker {
        this.rootDir = rootDir
        return this
    }

    fun open(requestCode: Int) {
        when (mContext) {
            is Activity -> {
                val intent = Intent(mContext, FilePickerActivity::class.java)
                intent.putExtra("root",this.rootDir)
                mContext.startActivityForResult(intent,requestCode)
            }
            is Fragment -> {
                val intent = Intent(mContext.activity, FilePickerActivity::class.java)
                intent.putExtra("root",this.rootDir)
                mContext.startActivityForResult(intent,requestCode)
            }
            is android.support.v4.app.Fragment -> {
                val intent = Intent(mContext.activity, FilePickerActivity::class.java)
                intent.putExtra("root",this.rootDir)
                mContext.startActivityForResult(intent,requestCode)
            }
        }
    }
}