package com.example.tpapimcpserver.domain

data class ScheduleEntry(
    val date: String,
    val type: String,
    val description: String?,
    val openingTime: String,
    val closingTime: String) {
}
