package com.example.tpapimcpserver

data class EntityParent(
    val id: String,
    val name: String,
    val entityType: String,
    val children: List<Entity>)
