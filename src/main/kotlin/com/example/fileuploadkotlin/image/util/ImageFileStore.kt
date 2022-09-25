package com.example.fileuploadkotlin.image.util

import com.example.fileuploadkotlin.image.domain.file.ImageFileDto
import com.example.fileuploadkotlin.image.domain.metadata.ImageMetadataDto
import com.example.fileuploadkotlin.image.domain.metadata.toEntity
import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired
    lateinit var imagingMetadataUtils: ImagingMetadataUtils

    private fun getFullPath(filename: String): String {
        return fileDir + filename
    }

    fun storeFile(multipartFile: MultipartFile): ImageFileDto {
        val originalFilename: String = multipartFile.originalFilename.toString()
        val storeFileName: String = createStoreFilename(originalFilename)
        val storeFile: File = File(getFullPath(storeFileName))
        multipartFile.transferTo(storeFile)

        val latitude = imagingMetadataUtils.getLatitude(storeFile)
        val longitude = imagingMetadataUtils.getLongitude(storeFile)
        val localDateTime = imagingMetadataUtils.getLocalDateTime(storeFile)
        val imageMetadataDto = ImageMetadataDto(latitude = latitude, longitude = longitude, shootDate = localDateTime)

        return ImageFileDto(
            originalFileName = originalFilename,
            storeFileName = storeFileName,
            filepath = "$fileDir",
            imageMetadata = imageMetadataDto.toEntity()
        )
    }

    fun storeFiles(multipartFiles: List<MultipartFile>): List<ImageFileDto> {
        val storeFiles: MutableList<ImageFileDto> = mutableListOf()
        for (multipartFile in multipartFiles) {
            storeFiles.add(storeFile(multipartFile))
        }
        return storeFiles
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