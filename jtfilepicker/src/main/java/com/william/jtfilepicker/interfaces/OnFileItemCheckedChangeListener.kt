package com.william.jtfilepicker.interfaces

import com.william.jtfilepicker.FileBean
import java.io.File

/**
 * Created by william on 2018/3/2.
 */
interface OnFileItemCheckedChangeListener {
    fun onFileItemCheckedChange(checked: Boolean, fileBean: FileBean)
}