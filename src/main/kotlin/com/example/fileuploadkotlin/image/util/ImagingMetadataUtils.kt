package com.example.fileuploadkotlin.image.util

import org.apache.commons.imaging.Imaging
import org.apache.commons.imaging.common.RationalNumber
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata
import org.apache.commons.imaging.formats.tiff.TiffField
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class ImagingMetadataUtils {

    fun getJpegImageMetadata(file: File): JpegImageMetadata {
        return Imaging.getMetadata(file) as JpegImageMetadata
    }

    fun getLatitude(file: File): Double {
        val jpegImageMetadata = getJpegImageMetadata(file)
        val latitudeDms = jpegImageMetadata.findEXIFValue(GpsTagConstants.GPS_TAG_GPS_LATITUDE)
        val latitudeDmsArray: Array<RationalNumber> = latitudeDms.value as Array<RationalNumber>
        val latitude = latitudeDmsArray.let {
            it.get(0).toDouble() + it.get(1).toInt().div(60).toDouble() + it.get(2).toDouble().div(3600)
        }
        return latitude
    }

    fun getLongitude(file: File): Double {
        val jpegImageMetadata = getJpegImageMetadata(file)
        val longitudeDms = jpegImageMetadata.findEXIFValue(GpsTagConstants.GPS_TAG_GPS_LONGITUDE)
        val longitudeDmsArray: Array<RationalNumber> = longitudeDms.value as Array<RationalNumber>
        val longitude = longitudeDmsArray.let {
            it.get(0).toDouble() + it.get(1).toInt().div(60).toDouble() + it.get(2).toDouble().div(3600)
        }
        return longitude
    }

    fun getLocalDateTime(file: File): LocalDateTime {
        val jpegImageMetadata = getJpegImageMetadata(file)
        val stringDateParseFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss")
        val dateTiff: TiffField = jpegImageMetadata.findEXIFValue(TiffTagConstants.TIFF_TAG_DATE_TIME)
        val dateString = dateTiff.value.toString()
        return LocalDateTime.parse(dateString, stringDateParseFormat)
    }
}