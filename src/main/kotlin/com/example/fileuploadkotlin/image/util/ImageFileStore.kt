package com.example.fileuploadkotlin.image.util

import com.example.fileuploadkotlin.image.domain.file.ImageFileDto
import com.example.fileuploadkotlin.image.domain.metadata.ImageMetadataDto
import com.example.fileuploadkotlin.image.domain.metadata.toEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.font.TextAttribute
import java.awt.image.BufferedImage
import java.io.File
import java.text.AttributedString
import java.time.LocalDateTime
import java.util.*
import javax.imageio.ImageIO


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

        val localDateTime = imagingMetadataUtils.getLocalDateTime(storeFile)
        val latitude = imagingMetadataUtils.getLatitude(storeFile)
        val longitude = imagingMetadataUtils.getLongitude(storeFile)
        val imageMetadataDto = ImageMetadataDto(latitude = latitude, longitude = longitude, shootDate = localDateTime)
        writeDateOnImage(storeFile, localDateTime)
        return ImageFileDto(
            originalFileName = originalFilename,
            storeFileName = storeFileName,
            filepath = "$fileDir",
            imageMetadata = imageMetadataDto.toEntity()
        )
    }

    private fun writeDateOnImage(storeFile: File, localDateTime: LocalDateTime) {
        val image: BufferedImage = ImageIO.read(storeFile)
        val width = image.width
        val height = image.height
        val graphics: Graphics = image.graphics
        val font = Font("Arial Black", Font.BOLD, 50)
        val text: String = localDateTime.toString()
        val metrics: FontMetrics = graphics.getFontMetrics(font)
        val positionX: Int = (width - metrics.stringWidth(text)) / 2
        val positionY: Int = (height - metrics.height)
        val attributedText = AttributedString(text)
        attributedText.addAttribute(TextAttribute.FONT, font)
        attributedText.addAttribute(TextAttribute.FOREGROUND, Color.GREEN)
        graphics.drawString(attributedText.iterator, positionX, positionY)
        ImageIO.write(image, "jpg", File(getFullPath(storeFile.name)))
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