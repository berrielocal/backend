package ru.vsu.cs.berrielocal.dto.shop


data class ShopAllInfoResponse(
    var shopId: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var matchLevel: Double? = null,
    var categories: Set<String>? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var rating: Double? = null,
    var description: String? = null,
)
