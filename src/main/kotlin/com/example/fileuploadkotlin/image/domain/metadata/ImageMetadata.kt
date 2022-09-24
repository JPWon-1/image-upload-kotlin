package com.example.fileuploadkotlin.image.domain.metadata

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "IMAGEMETADATAS")
class ImageMetadata(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MetadataSequenceGenerator")
    @SequenceGenerator(sequenceName = "MetadataSequence", name = "MetadataSequenceGenerator", allocationSize = 1)
    @Column(name = "id", nullable = false)
    open var id: Long? = null,

    @CreatedDate
    @Column(updatable = false)
    var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    var modifiedDate: LocalDateTime? = null,

    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var shootDate: LocalDateTime? = null,

    )