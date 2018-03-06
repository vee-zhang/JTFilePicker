package com.example.william.jtfilepickerdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.william.jtfilepicker.FileBean
import com.william.jtfilepicker.JTFilePicker
import com.william.jtfilepicker.interfaces.OnFileIconLoadListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity(), OnFileIconLoadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.tv.setOnClickListener {
            JTFilePicker.from(this, this)
                    .open(0)
        }
    }

    override fun fileIconLoad(iv: ImageView, fileBean: FileBean) {
        //判断文件图标
        val iconResId = if (fileBean.isDirectory) {
            R.drawable.foldertype
        } else {
            when (fileBean.extension.toLowerCase()) {
                "jpg", "jpeg", "gif", "bmp", "png", "webp" -> fileBean.path
                "xls", "xlsx", "excel" -> R.drawable.xlstype
                "txt" -> R.drawable.txttype
                "pdf" -> R.drawable.pdftype
                "ppt", "pptx", "pps", "ppsx", "ppsm", "PPT", "PPTX", "PPS", "PPSX", "PPSM" -> R.drawable.ppttype
                "doc", "docx" -> R.drawable.doctype
                "zip", "rar" -> R.drawable.rartype
                "wmv", "avi", "dat", "asf", "rm", "rmvb", "mpg", "mpeg", "mkv", "dvix", "dv", "flv", "mov", "mp4", "qt", "smil", "swf", "wmv", "3gp" -> R.drawable.videotype
                "mp3", "wav", "wma", "mid" -> R.drawable.musictype
                "apk" -> R.drawable.apktype
                "ipa" -> R.drawable.ipatype
                else -> R.drawable.othertype
            }
        }
        Glide.with(iv).load(iconResId).into(iv)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            Toast.makeText(this,"已成功：${data!!.getStringArrayListExtra("paths")}",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"已取消",Toast.LENGTH_SHORT).show()
        }
    }
}
