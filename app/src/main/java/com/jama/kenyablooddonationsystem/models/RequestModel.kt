package com.jama.kenyablooddonationsystem.models

data class RequestModel(
    var bloodType: String = "",
    var gender: String = "",
    var hname: String = "",
    var imageUrl: String = "",
    var key: String = "",
    var lat: String = "",
    var lng: String = "",
    var place: String = "",
    var requestReason: String = "",
    var recepientName: String = "",
    var timestamp: Int = 0,
    var uid: String = "",
    var active: Boolean = true

) {

    fun toMap(): Map<String, Any> {
        return mapOf(
            "bloodType" to bloodType,
            "hname" to hname,
            "imageUrl" to imageUrl,
            "key" to key,
            "lat" to lat,
            "gender" to gender,
            "lng" to lng,
            "place" to place,
            "uid" to uid,
            "requestReason" to requestReason,
            "recepientName" to recepientName,
            "timestamp" to timestamp,
            "active" to active
        )
    }

}