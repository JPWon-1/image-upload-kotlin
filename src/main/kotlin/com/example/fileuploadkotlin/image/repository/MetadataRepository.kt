package com.example.fileuploadkotlin.image.repository

import com.example.fileuploadkotlin.image.domain.metadata.ImageMetadata
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MetadataRepository : CrudRepository<ImageMetadata, Long> {

}