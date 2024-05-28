package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.model.enums.Category

@RestController
@RequestMapping(API_VERSION)
@Tag(name = "CategoriesController", description = "Словарь категорий")
class CategoriesController {

    @GetMapping("/categories")
    fun getCategoriesMapWithRussianSymbols(): ResponseEntity<Map<String, String>> {
        val map = createMapWithCategories()

        return ResponseEntity.ok(map)
    }

    private fun createMapWithCategories() =
        Category.entries.associate { it.name to it.type }
}