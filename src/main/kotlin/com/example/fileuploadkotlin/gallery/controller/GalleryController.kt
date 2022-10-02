package com.example.fileuploadkotlin.gallery.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GalleryController {

    @GetMapping("/album")
    fun lightbox(model: Model): String {
        return "album"
    }
}