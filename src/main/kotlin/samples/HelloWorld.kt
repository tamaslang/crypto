package samples

fun main() {
    println(sayHello("World"))
}

fun sayHello(name: String = "") = "Hello $name".trimEnd()
