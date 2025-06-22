package com.example.userapi.adapter.`in`.web.dto

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort

data class PageResponse<T>(
    var size: Int = 0,
    var currentPage: Int = 0,
    var totalPages: Int = 0,
    var totalElements: Long = 0,
    var content: List<T> = emptyList(),
    var sort: List<PageSort> = emptyList()
) {

    fun of(page: Page<out Any>): PageResponse<T> {
        this.size = page.size // 한 페이지에 표시할 항목의 개수
        this.totalElements = page.totalElements // 전체 데이터의 개수
        this.totalPages = page.totalPages // 전체 페이지의 수 (전체 항목수 /페이지 크기)
        this.currentPage = page.pageable.pageNumber // 현재 페이지 번호 (0부터 시작)
        this.sort = PageSort.of(page.sort)
        this.content = page.content as List<T> //{ it.toDomain() as T } // 데이터
        return this
    }

    data class PageSort(
        val direction: String,
        val property: String
    ) {
        companion object {
            fun of(sort: Sort): List<PageSort> {
                return sort.toList().map { PageSort(it.direction.toString(), it.property) } // 수정된 부분
            }
        }
    }
}

