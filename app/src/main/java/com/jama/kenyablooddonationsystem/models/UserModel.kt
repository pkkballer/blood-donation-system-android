package com.jama.kenyablooddonationsystem.models

data class UserModel(
    var fullName: String = "",
    var email: String = "",
    var password: String = "",
    var nationalId: String = "",
    var phone: String = "",
    var gender: String = "",
    var bloodType: String = "",
    var uid: String = "userID"
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "fullName" to fullName,
            "email" to email,
            "password" to password,
            "nationalId" to nationalId,
            "phone" to phone,
            "gender" to gender,
            "bloodType" to bloodType,
            "role" to 2,
            "uid" to uid
        )
    }

    fun toDonorDetails(): Map<String, Any> {
        return mapOf(
            "fullName" to fullName,
            "bloodType" to bloodType,
            "nationalId" to nationalId,
            "noOfDonations" to 0,
            "lastDonated" to "Has not donated yet",
            "hname" to "No hospital visited",
            "placeOfDonation" to "Has not donated yet",
            "uid" to uid,
            "latlng" to mapOf(
                "lat" to "-1.263325357489324",
                "lng" to "36.80419921875"
            )
        )
    }
}