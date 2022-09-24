package com.example.fileuploadkotlin.image.service

import com.example.fileuploadkotlin.image.domain.file.ImageFileDto
import com.example.fileuploadkotlin.image.domain.file.toEntity
import com.example.fileuploadkotlin.image.domain.metadata.ImageMetadataDto
import com.example.fileuploadkotlin.image.domain.metadata.toEntity
import com.example.fileuploadkotlin.image.repository.ImageFileRepository
import com.example.fileuploadkotlin.image.util.ImageFileStore
import com.example.fileuploadkotlin.image.util.ImagingMetadataUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class ImageFileService {

    @Autowired
    lateinit var imageFileRepository: ImageFileRepository

    @Autowired
    lateinit var imageFileStore: ImageFileStore

    @Autowired
    lateinit var imagingMetadataUtils: ImagingMetadataUtils

    @Value("\${file.dir}")
    val fileDir: String? = null

    fun saveImage(multipartFile: MultipartFile){
        val storeFile: File = imageFileStore.storeFile(multipartFile)
        val latitude = imagingMetadataUtils.getLatitude(storeFile)
        val longitude = imagingMetadataUtils.getLongitude(storeFile)
        val localDateTime = imagingMetadataUtils.getLocalDateTime(storeFile)

        val imageMetadataDto = ImageMetadataDto(latitude = latitude, longitude = longitude, shootDate = localDateTime)
        val imageFileDto = ImageFileDto(
            originalFileName = multipartFile.originalFilename.toString(),
            storeFileName = storeFile.name,
            filepath = "$fileDir",
            imageMetadata = imageMetadataDto.toEntity()
        )

        imageFileRepository.save(imageFileDto.toEntity())
    }
}