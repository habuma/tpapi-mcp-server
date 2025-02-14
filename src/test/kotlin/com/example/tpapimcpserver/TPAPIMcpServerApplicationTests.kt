package com.example.tpapimcpserver

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TPAPIMcpServerApplicationTests {

    @Autowired
    lateinit var myTools: MyTools

    @Test
    fun contextLoads() {
        val entityLive = myTools.getEntityLive("8c36ff0b-3a32-4d7b-9388-0516c19277db")
        println(entityLive)
    }

}
