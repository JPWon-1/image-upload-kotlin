package com.example.fileuploadkotlin.image.domain.file

import com.example.fileuploadkotlin.image.domain.metadata.ImageMetadata
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "FILES")
class ImageFile(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FilesSequenceGenerator")
    @SequenceGenerator(sequenceName = "FilesSequence", name = "FilesSequenceGenerator", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @CreatedDate
    @Column(updatable = false)
    var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    var modifiedDate: LocalDateTime? = null,

    var originalFileName: String? = "",
    var storeFileName: String? = "",
    var filepath: String? = "",

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id")
    var imageMetadata: ImageMetadata? = ImageMetadata()
)