package com.example.tpapimcpserver.domain

data class EntitySchedule(
    val id: String,
    val name: String,
    val schedule: List<ScheduleEntry>)
