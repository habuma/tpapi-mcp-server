package com.example.tpapimcpserver.domain

data class Destination(
    val id: String,
    val name:String,
    val parks: List<Park>)
