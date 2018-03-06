package com.william.jtfilepicker

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.IntDef
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_file, parent, false)
        return object : ViewHolder(viewDataBinding.root){}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
        val itemView = holder.itemView
        val fileBean = list.get(position)
        binding.setVariable(BR.fileBean,fileBean)
        itemView.cb.setOnCheckedChangeListener { compoundButton, b ->
            onFileItemCheckedChangeListener.onFileItemCheckedChange(b, fileBean)
        }
        JTFilePicker.mlistener.fileIconLoad(itemView.iv_file_icon, fileBean)

        when (holder.itemViewType) {
            TYPE_FILE -> {
                if (fileBean.isChecked) {
                    itemView.cb.isChecked = true
                } else {
                    itemView.cb.isChecked = false
                }
                itemView.cb.visibility = View.VISIBLE
                itemView.setOnClickListener {
                    if (itemView.cb.isChecked) {
                        fileBean.isChecked = false
                        itemView.cb.isChecked = false
                    } else {
                        fileBean.isChecked = true
                        itemView.cb.isChecked = true
                    }
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