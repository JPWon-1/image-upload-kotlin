package com.example.fileuploadkotlin.image.controller

import com.example.fileuploadkotlin.image.service.ImageFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
class ImageFileController {

    @Value("\${file.dir}")
    val fileDir: String? = null

    @Autowired
    lateinit var imageFileService: ImageFileService

    @PostMapping("/upload")
    fun upload(@RequestParam("file") multipartFile: MultipartFile, model: Model): String {
        imageFileService.saveImage(multipartFile)
        return "redirect:/gallery"
    }

    @PostMapping("/upload2")
    fun upload2(@RequestParam("files") multipartFiles: List<MultipartFile>, model: Model): String {
        imageFileService.saveImages(multipartFiles)
        return "redirect:/gallery"
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    fun loadImage(@PathVariable filename: String): Resource {
        return UrlResource("file:"+fileDir+"/"+filename)
    }
}