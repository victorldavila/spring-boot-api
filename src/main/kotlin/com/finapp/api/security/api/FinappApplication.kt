package com.finapp.api.security.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.finapp.api"])
class FinappApplication

fun main(args: Array<String>) {
	runApplication<FinappApplication>(*args)
}
