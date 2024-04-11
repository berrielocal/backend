package ru.vsu.cs.berrielocal.model.enums

enum class OrderPartStatus(val value: String) {
    IN_CART("В корзине"),
    ORDERED("Заказан"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменен")
}