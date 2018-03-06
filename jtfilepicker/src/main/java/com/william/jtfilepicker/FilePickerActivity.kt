package com.william.jtfilepicker

import android.app.Activity
import android.app.FragmentTransaction
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.william.jtfilepicker.databinding.ActivityFilePickerBinding
import com.william.jtfilepicker.interfaces.OnFolderItemClickListener
import kotlinx.android.synthetic.main.activity_file_picker.*

class FilePickerActivity : AppCompatActivity(), OnFolderItemClickListener, View.OnClickListener {

    private lateinit var rootDir :String
    val fileList = ObservableArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFilePickerBinding>(this,R.layout.activity_file_picker)
        setSupportActionBar(action)
        binding.fileList = this.fileList
        rootDir = Environment.getExternalStorageDirectory().path
        addFragment(rootDir,false)
        setResult(Activity.RESULT_CANCELED)
        this.ib_back.setOnClickListener(this)
        this.tv_cancel.setOnClickListener(this)
        this.btn_choose.setOnClickListener(this)
    }

    private fun addFragment(filePath:String, isAddBackStack:Boolean){
        val fragment = FilePickerFragment.newInstance(filePath)
        val ft = supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.replace(R.id.container,fragment)
        if (isAddBackStack){ft.addToBackStack(null)}
        ft.commit()
    }

    override fun onFolderItemClick(fileBean: FileBean) {
        addFragment(fileBean.path,true)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_choose->{
                val resultData = intent.putExtra("paths",fileList)
                setResult(Activity.RESULT_OK,resultData)
                finish()
            }
            R.id.ib_back->{
                val fragmentCount = supportFragmentManager.getBackStackEntryCount()
                if (fragmentCount>0) {
                    supportFragmentManager.popBackStack()
                }else{
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
            }
            else->{
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }
}
