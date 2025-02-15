package com.example.tpapimcpserver.domain

data class LiveData(
    val queue: Map<String, Object>?,
    val showtimes: List<ShowTime>?)
