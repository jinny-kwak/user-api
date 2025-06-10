package com.example.userapi.domain.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.OffsetDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTimeEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @Comment("생성 일자")
    var createdAt: LocalDateTime? = null

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @Comment("생성자")
    var createdBy: String? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    @Comment("수정 일자")
    var updatedAt: LocalDateTime? = null

    @LastModifiedBy
    @Column(name = "updated_by")
    @Comment("수정자")
    var updatedBy: String? = null
}