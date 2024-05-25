package ru.vsu.cs.berrielocal.repository.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import ru.vsu.cs.berrielocal.model.enums.Category

@Converter
class StringToSetCategoryAttributeConverter
    : AttributeConverter<Set<Category>?, String?> {
    override fun convertToDatabaseColumn(attribute: Set<Category>?) =
        attribute?.joinToString(separator = SEPARATOR)

    override fun convertToEntityAttribute(dbData: String?) =
        dbData
            ?.split(SEPARATOR)
            ?.mapNotNull { category ->
                Category.entries.firstOrNull { it.name == category }
            }
            ?.toSet() ?: emptySet()

    private companion object {
        private const val SEPARATOR: String = ", "
    }
}