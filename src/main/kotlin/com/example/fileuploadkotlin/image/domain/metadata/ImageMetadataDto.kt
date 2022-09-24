package com.example.fileuploadkotlin.image.domain.metadata

import java.time.LocalDateTime

data class ImageMetadataDto(

    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var shootDate: LocalDateTime? = null,

)

fun ImageMetadataDto.toEntity() = ImageMetadata(
    latitude = latitude,
    longitude = longitude,
    shootDate = shootDate,
)