package com.jama.kenyablooddonationsystem.repository.geofire

import com.firebase.geofire.GeoQuery
import com.firebase.geofire.GeoFire
import com.google.firebase.database.FirebaseDatabase


class GeofireRepository {
    private lateinit var geoQuery: GeoQuery
    private lateinit var geoFire: GeoFire
    private val databaseRef = FirebaseDatabase.getInstance().getReference("geofire/events")



}