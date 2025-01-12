package org.github.goatfryed.spring_boot_helm_e2e

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootHelmE2eApplication

fun main(args: Array<String>) {
	runApplication<SpringBootHelmE2eApplication>(*args)
}
