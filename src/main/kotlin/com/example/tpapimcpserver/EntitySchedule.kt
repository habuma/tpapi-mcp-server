package com.example.tpapimcpserver

data class EntitySchedule(
    val id: String,
    val name: String,
    val schedule: List<ScheduleEntry>)
