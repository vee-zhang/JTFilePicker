package com.william.jtfilepicker.interfaces

import android.widget.ImageView
import com.william.jtfilepicker.FileBean
import java.io.File

/**
 * Created by william on 2018/3/2.
 */
interface OnFileIconLoadListener {
    fun fileIconLoad(iv: ImageView, fileBean: FileBean)
}