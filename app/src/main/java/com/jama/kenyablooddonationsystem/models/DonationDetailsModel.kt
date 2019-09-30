package com.jama.kenyablooddonationsystem.models

data class DonationDetailsModel(
    var bloodType: String = "",
    var hname: String = "",
    var fullName: String = "",
    var lastDonated: String = "",
    var nationalId: String = "",
    var placeOfDonation: String = "",
    var uid: String = "",
    var noOfDonations: Int = 0,
    var latlng: Map<String, String> = mapOf(
        "lat" to "0.0",
        "lng" to "0.0"
    )
)