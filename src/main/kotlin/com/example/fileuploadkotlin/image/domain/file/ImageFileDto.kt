package com.example.fileuploadkotlin.image.domain.file

import com.example.fileuploadkotlin.image.domain.metadata.ImageMetadata

data class ImageFileDto(
    val originalFileName: String,
    val storeFileName: String,
    val filepath: String,
    val imageMetadata: ImageMetadata
)

fun ImageFileDto.toEntity() = ImageFile(
    originalFileName = originalFileName,
    storeFileName = storeFileName,
    filepath = filepath,
    imageMetadata = imageMetadata
)