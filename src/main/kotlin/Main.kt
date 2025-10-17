package core

import core.tools.changeName as newName
import core.tools.email
import core.tools.makeChild as child

fun main() {


    println("Hello world!\nВход в систему...\n")

    print("Введите имя пользователя: "); val userName = /*readln()*/ "Admin"
    print("Введите пароль от учетной записи: "); val userShadow = /*readln()*/ "Password"

    val isValid = validUserPassword(user=userName, password = userShadow)

    if (isValid) println("Добро пожаловать, $userName!")
    else println("Ошибка входа! Неправильное имя и пароль.")

    println("Vector_1: ${createVector(1,2,3)}")
    println("Vector_2: ${createVector(z=1, x=2, y=3)}")
    println("Vector_3: ${createVector()}")

    fun makeSomething() {
        println("fun: makeSomething")
    }

    class newClass()
    println("local class: ${newClass::class}")

    makeSomething()
    makeSomething()

    println("create:")
    val person1 = Person(1, "H#V-DZe3v-@rT3~", "7e^)P-RTCu^PcWr")
    val person2 = Person(2, "dQp(7RUX-G~y!rH", "h9-!8wL*j(GRC@2")

    println(person1); println(person2)
    println("person1 email: ${person1.email}"); println("person2 email: ${person2.email}")

    println("rename:")
    person1.newName("User")
    person2.newName("Admin")

    println(person1);println(person2)
    println("person1 email: ${person1.email}"); println("person2 email: ${person2.email}")

    val array = arrayOf(1, 9.806f, null, "Hello!")
    printAll("123", 1, 'C', 0b011010101001L, 100L, 3.14f, null, {if (1 > 2) throw RuntimeException("It's unbelievable!") else false})
    printAll(*array)

    val personsChild1 = person1 child person2
    val personsChild2 = person2 child person1
    val personsChild3 = personsChild1 child personsChild2
    println("child_1: $personsChild1")
    println("child_2: $personsChild2")
    println("child_3: $personsChild3")

    val (id, name, password) = personsChild3
    println( "Person(id=$id, name='$name', email='${personsChild3.email}', password='$password')")

}

fun createVector(x: Int = 0, y: Int = 0, z: Int = 0): Triple<Int, Int, Int> {
    return Triple(x, y, z)
}

fun validUserPassword(user: String, password: String) =
    when (user) {
        "User" -> password == "user12345"
        "Admin" -> password == "password"
        else -> false
    }

data class Person(val id: Int, var name: String, var password: String)

fun printAll(vararg values: Any?){
    print("Values: {")
    values.forEach { print("$it ") }
    print("}\n")
}