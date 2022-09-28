package com.example.fileuploadkotlin.gallery.controller

import com.example.fileuploadkotlin.image.domain.file.ImageFile
import com.example.fileuploadkotlin.image.repository.ImageFileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GalleryController {

    @Autowired
    lateinit var imageFileRepository: ImageFileRepository

    @GetMapping("/index")
    fun index(model: Model): String {
        val imageFiles: MutableIterable<ImageFile> = imageFileRepository.findAll()
        model.addAttribute("imageFiles", imageFiles)
        return "index"
    }

    @GetMapping("/lightbox")
    fun lightbox(model: Model): String {
        val imageFiles: MutableIterable<ImageFile> = imageFileRepository.findAll()
        model.addAttribute("imageFiles", imageFiles)
        return "lightbox"
    }
}