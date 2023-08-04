package com.example.appzaza.data.model

object ConnProperties {

    private var _ip: String = ""

    val ip: String
        get() = _ip

    fun initialize(ip: String) {
        _ip = ip
    }

    val URL_Register: String
        get() = "httpTEST://$ip"


}
