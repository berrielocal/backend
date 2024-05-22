package ru.vsu.cs.berrielocal.dto.shop

data class ShopMainInfo(
    var shopId: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var matchLevel: Double? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
)
