package ru.vsu.cs.berrielocal.model.enums

enum class OrderPartStatus(val value: String) {
    ORDERED("Заказан"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменен")
}