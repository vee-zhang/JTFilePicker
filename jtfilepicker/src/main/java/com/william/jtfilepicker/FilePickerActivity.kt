package com.william.jtfilepicker

import android.app.Activity
import android.app.FragmentTransaction
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.william.jtfilepicker.interfaces.OnFolderItemClickListener
import kotlinx.android.synthetic.main.activity_file_picker.*
import java.io.File

class FilePickerActivity : AppCompatActivity(), OnFolderItemClickListener, View.OnClickListener {

    private lateinit var rootDir: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_picker)
        rootDir = intent.getStringExtra("root")
        val rootFileBean = FileBean(File(rootDir))
        replaceFragment(rootFileBean, false)
        setResult(Activity.RESULT_CANCELED)
        this.ib_back.setOnClickListener(this)
        this.tv_cancel.setOnClickListener(this)
        this.btn_choose.setOnClickListener(this)
    }

    private fun replaceFragment(fileBean: FileBean, isAddBackStack: Boolean) {
        val fragment = FilePickerFragment.newInstance(fileBean.path)
        val ft = supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.replace(R.id.container, fragment)
        if (isAddBackStack) {
            ft.addToBackStack(null)
        }
        ft.commit()
        val title = if (TextUtils.equals(fileBean.name,rootDir)) "根目录" else fileBean.name
        this.tv_title.setText(title)
    }

    override fun onFolderItemClick(fileBean: FileBean) {
        replaceFragment(fileBean, true)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_choose -> {
                val resultData = intent.putExtra("paths", JTFilePicker.fileList)
                setResult(Activity.RESULT_OK, resultData)
                finish()
            }
            R.id.ib_back -> {
                val fragmentCount = supportFragmentManager.getBackStackEntryCount()
                if (fragmentCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
            }
            else -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

    fun reload() {
        val count = JTFilePicker.fileList.size
        if (count>0) {
            this.btn_choose.isEnabled = true
            this.btn_choose.setText("上传(${count})")
        } else {
            this.btn_choose.isEnabled = false
            this.btn_choose.setText("上传")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        JTFilePicker.fileList.clear()
    }
}
