package com.example.fileuploadkotlin.image.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

/*
* 저장소에 이미지 파일을 업로드
* */
@Component
class ImageFileStore {

    @Value("\${file.dir}")
    val fileDir: String? = null

    private fun getFullPath(filename: String): String {
        return fileDir + filename
    }

    fun storeFile(multipartFile: MultipartFile): File {
        val name: String = multipartFile.originalFilename.toString()
        val storeFileName: String = createStoreFilename(name)
        val file = File(getFullPath(storeFileName))
        multipartFile.transferTo(file)
        return file
    }

    fun storeFiles(multipartFiles: List<MultipartFile>) {

    }

    private fun createStoreFilename(filename: String): String {
        val ext: String = extractExt(filename)
        val uuid: String = UUID.randomUUID().toString()
        return "$uuid.$ext"
    }

    private fun extractExt(filename: String): String {
        val pos: Int = filename.lastIndexOf(".")
        return filename.substring(pos + 1)
    }
}