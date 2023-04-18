package app

import MyClass
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class BlogApplication

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
    println("test")
    val c = MyClass(3, 5)
    println(c.sum)

}