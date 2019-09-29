package com.jama.kenyablooddonationsystem.models

import com.jama.kenyablooddonationsystem.utils.DateTimeUtil
import java.io.Serializable

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
    var active: Boolean = true,
    var viewed: Int = 0,
    var accepted: Int = 0


): Serializable {

    fun registerDonation(): Map<String, Any> {
        return mapOf(
            "hname" to hname,
            "placeOfDonation" to place,
            "lastDonated" to DateTimeUtil().getDateNow(),
            "latlng" to mapOf(
                "lat" to lat,
                "lng" to lng
            )
        )
    }

}