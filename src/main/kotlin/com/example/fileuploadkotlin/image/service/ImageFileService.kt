package com.example.fileuploadkotlin.image.service

import com.example.fileuploadkotlin.image.domain.file.ImageFile
import com.example.fileuploadkotlin.image.domain.file.ImageFileDto
import com.example.fileuploadkotlin.image.domain.file.toEntity
import com.example.fileuploadkotlin.image.repository.ImageFileRepository
import com.example.fileuploadkotlin.image.util.ImageFileStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageFileService {

    @Autowired
    lateinit var imageFileRepository: ImageFileRepository

    @Autowired
    lateinit var imageFileStore: ImageFileStore

    @Value("\${file.dir}")
    val fileDir: String? = null

    fun saveImage(multipartFile: MultipartFile){
        val storeFile: ImageFileDto = imageFileStore.storeFile(multipartFile)
        imageFileRepository.save(storeFile.toEntity())
    }

    fun saveImages(multipartFiles: List<MultipartFile>) {
        val storeFiles: List<ImageFileDto> = imageFileStore.storeFiles(multipartFiles)
        val imageFiles: MutableList<ImageFile> = mutableListOf()
        for (storeFile in storeFiles) {
            imageFiles.add(storeFile.toEntity())
        }
        imageFileRepository.saveAll(imageFiles)
    }
}