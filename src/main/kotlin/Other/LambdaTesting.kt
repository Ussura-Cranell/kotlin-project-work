package core.Other

fun lambdaTesting(){

    val users = hashSetOf<User>(
        User("Jack", 24),
        User("Mary", 31),
        User("Kristof", 65),
        User("Luna", 17),
        User("Poco", 44))

    val function: (Set<User>) -> Unit = {
        println("${it::class.simpleName}:")
        users.forEach { println(it) }
    }

    val function2: () -> Unit = {
        println("Hello, world!")
    }

    function(users)

    val testClass = TestClass()

    val testObjectValue = testClass::value1 // ссылка на поле обьекта
    val testClassValue = TestClass::value1 // ссылка на поле класса

    println(testObjectValue) // ссылка на поле объекта
    println(testClassValue) // ссылка на поле класса

    println(testObjectValue()) // это геттер объекта
    println(testClassValue(testClass)) // вызов геттера класса с передачей объекта

    val iClickable = IClickable {println("Hello, world!")}
    iClickable.onClick()
    iClickable.aboveClick()

    users.first().apply { name = "unnamed" }
    println(users)

}

class TestClass {
    var value1 = 1
}

fun interface IClickable{
    fun onClick()
    fun aboveClick() = println("test")
}

class TestClass2

data class User(var name: String, var age: Int) {
    fun doTask(task: ()->Unit) {
        task()
    }
}