package com.example.tpapimcpserver

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TPAPIMcpServerApplicationTests {

    @Autowired
    lateinit var myTools: ThemeParkApiTools

    @Test
    fun contextLoads() {
    }

}
