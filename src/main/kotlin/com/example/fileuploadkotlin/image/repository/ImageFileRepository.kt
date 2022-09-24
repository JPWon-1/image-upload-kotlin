package com.example.fileuploadkotlin.image.repository

import com.example.fileuploadkotlin.image.domain.file.ImageFile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageFileRepository : CrudRepository<ImageFile, Long> {

}