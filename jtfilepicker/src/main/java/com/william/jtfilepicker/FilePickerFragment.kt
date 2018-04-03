package com.william.jtfilepicker

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.william.jtfilepicker.interfaces.OnFileItemCheckedChangeListener
import com.william.jtfilepicker.interfaces.OnFolderItemClickListener
import kotlinx.android.synthetic.main.activity_file_picker.*
import kotlinx.android.synthetic.main.fragment_file_picker.view.*
import java.io.File


class FilePickerFragment : Fragment(), OnFileItemCheckedChangeListener {

    private lateinit var mActivity: FilePickerActivity
    private lateinit var rv: RecyclerView
    val dataList = arrayListOf<FileBean>()

    private lateinit var parentDir: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val path = arguments!!.getString(path)
            parentDir = File(path)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater.inflate(R.layout.fragment_file_picker, container, false)
        this.rv = rootView.rv
        rootView.rv.layoutManager = LinearLayoutManager(context)
        this.rv.adapter = FileAdapter(dataList, this, activity as FilePickerActivity)
        this.rv.addItemDecoration(NormalDecoration())
        return rootView
    }

    override fun onStart() {
        super.onStart()
        val parentActivity = activity as FilePickerActivity
        val title = if (TextUtils.equals(parentActivity.rootDir,parentDir.path)) "根目录" else parentDir.name
        parentActivity.tv_title.setText(title)
        val files = parentDir.listFiles()
        //todo 这里效率稍低，待解决
        for (file in files) {
            val fileBean = FileBean(file)
            for (path in JTFilePicker.fileList) {
                if (TextUtils.equals(fileBean.path, path)) {
                    fileBean.isChecked = true
                    break
                }
            }
            dataList.add(fileBean)
        }
        this.rv.adapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnFolderItemClickListener) {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        } else {
            mActivity = context as FilePickerActivity
        }
    }

    companion object {

        private val path = "path"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 parentPath parent directory's path.
         * @return A new instance of fragment FilePickerFragment.
         */
        fun newInstance(parentPath: String): FilePickerFragment {
            val fragment = FilePickerFragment()
            val args = Bundle()
            args.putString(path, parentPath)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onFileItemCheckedChange(checked: Boolean, fileBean: FileBean) {
        if (checked) {
            JTFilePicker.fileList.add(fileBean.path)
        } else {
            JTFilePicker.fileList.remove(fileBean.path)
        }
        mActivity.reload()
    }

}// Required empty public constructor
