package com.example.tpapimcpserver.domain

data class EntityLive(
    val id: String,
    val name: String,
    val liveData: List<LiveData>)
