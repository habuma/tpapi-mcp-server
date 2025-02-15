package com.example.tpapimcpserver.domain

data class EntityParent(
    val id: String,
    val name: String,
    val entityType: String,
    val children: List<Entity>)
