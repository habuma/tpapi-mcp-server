package com.example.tpapimcpserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KtMcpServerApplication

fun main(args: Array<String>) {
    runApplication<KtMcpServerApplication>(*args)
}
