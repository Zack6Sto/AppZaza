package com.example.appzaza.data.model

data class ConfigData(
    val AppName: String,
    val AppVersion: Double,
    val RSS: List<RSS>,
    val age: Int,
    val lastname: String,
    val name: String
)

data class RSS(
    val index: String,
    val link: List<String>
)