package com.william.jtfilepicker

import android.support.annotation.IntDef
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.william.jtfilepicker.interfaces.OnFileItemCheckedChangeListener
import com.william.jtfilepicker.interfaces.OnFolderItemClickListener
import kotlinx.android.synthetic.main.item_file.view.*
import java.util.*


/**
 * Created by william on 2018/3/1.
 */
class FileAdapter(
        private val list: ArrayList<FileBean>,
        @NonNull private val onFileItemCheckedChangeListener: OnFileItemCheckedChangeListener,
        @NonNull private val onFolderItemClickListener: OnFolderItemClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val TYPE_FOLDER = 0
        const val TYPE_FILE = 1

        @Retention(AnnotationRetention.SOURCE)
        //这里指定int的取值只能是以下范围
        @IntDef(TYPE_FOLDER, TYPE_FILE)
        internal annotation class FileType
    }

    @FileType
    var fileType = 0

    override fun getItemViewType(position: Int): Int {
        val file = list.get(position)
        return if (file.isDirectory) TYPE_FOLDER else TYPE_FILE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_file, parent, false)
        return object : ViewHolder(itemView) {}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemView = holder.itemView
        val fileBean = list.get(position)
        itemView.tv_file_name.setText(fileBean.name)
        itemView.tv_file_create_time.setText(fileBean.lastModified)
        itemView.cb.setOnCheckedChangeListener(null)
        itemView.cb.isChecked = fileBean.isChecked
        itemView.cb.setOnCheckedChangeListener { compoundButton, b ->
            if (!fileBean.name.contains(".")) {
                compoundButton.isChecked = false
                Toast.makeText(itemView.context, "不支持无后缀名文件，请重新选择！", Toast.LENGTH_SHORT).show()
            } else {
                fileBean.isChecked = if (b) true else false
                onFileItemCheckedChangeListener.onFileItemCheckedChange(b, fileBean)
            }
        }
        JTFilePicker.mlistener.fileIconLoad(itemView.iv_file_icon, fileBean)

        when (holder.itemViewType) {
            TYPE_FILE -> {
                itemView.cb.isChecked = fileBean.isChecked
                itemView.cb.visibility = View.VISIBLE
                itemView.setOnClickListener {
                    itemView.cb.isChecked = !itemView.cb.isChecked
                }
            }
            TYPE_FOLDER -> {
                itemView.cb.visibility = View.INVISIBLE
                itemView.cb.isChecked = false
                itemView.setOnClickListener {
                    onFolderItemClickListener.onFolderItemClick(fileBean)
                }
            }
        }
    }
}