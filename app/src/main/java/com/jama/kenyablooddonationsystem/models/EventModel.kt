package com.jama.kenyablooddonationsystem.models

import java.io.Serializable

data class EventModel(
    var date: Int = 0,
    var endTime: String = "",
    var hname: String = "",
    var imageUrl: String = "",
    var key: String = "",
    var lat: String = "",
    var lng: String = "",
    var place: String = "",
    var startTime: String = "",
    var eventName: String = "",
    var timestamp: Int = 0,
    var uid: String = "",
    var active: Boolean = true,
    var viewed: Int = 0,
    var eventDescription: String = ""


): Serializable