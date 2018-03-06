package com.william.jtfilepicker

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by william on 2018/3/2.
 */
class FileBean {
    var file: File
    var path: String
    var name: String
    var extension: String
    var lastModified: String
    var isChecked = false
    var isDirectory = false

    companion object {
        val formate = SimpleDateFormat("yyyy/MM/dd\t\tHH:mm")
    }

    constructor(file: File) {
        this.file = file
        this.path = file.path
        isDirectory = file.isDirectory
        name = file.name
        extension = file.extension
        val date = Date(file.lastModified())
        lastModified = formate.format(date)
    }
}