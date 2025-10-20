package core.Testing.OverloadingTesting

import io.github.serpro69.kfaker.Faker
import kotlin.random.Random

fun overloadingTesting(){
    println("Hello, world!")

    val point = Point(1, 2)
    println(point)
    println()

    fun rand() = Random.nextInt(1, 100)
    fun randPoint() = Point(rand(), rand())

    val array = arrayOf(
        randPoint(),
        randPoint(),
        randPoint(),
        randPoint(),
        randPoint(),
        randPoint())

    array.forEachIndexed { i, it -> println("$i. $it") }
    println()
    println("${array[0]} + ${array[1]} = ${array[0] + array[1]}")
    println("${array[0]} - ${array[1]} = ${array[0] - array[1]}")
    println()
    println("${array[0]} * ${array[1]} = ${array[0] * array[1]}")
    println("${array[0]} / ${array[1]} = ${array[0] / array[1]}")
    println()
    println("array[0][0] = ${array[0][0]}")
    println("array[0][1] = ${array[0][1]}")
    println("array[1][0] = ${array[1][0]}")
    println("array[1][1] = ${array[1][1]}")
    println("array[1][-5] = ${array[1][-5]}")

    for (i in 10..<20) print("$i ")
    println()

    val (x, y) = point
    println("x=$x y=$y")

    println(array.size)
    val (a, b, c, d, e) = array // последний элемент распоковать не получается
    println("a=$a b=$b c=$c d=$d e=$e")

    val map = mutableMapOf<String, Int>()
    for (i in 0 until 16) map[i.toHexString().uppercase()] = i

    for ((key, value) in map) println("$key to $value")

    for (i in map.entries) println("key: ${i.component1()}, value: ${i.component2()}")
    map.forEach { (key, value) -> println("$key -> $value") }

    println()
    val person2 = Person2()

    println("#  Запрос на получения всех писем "); println("#  Ответ на запрос (emails): ${person2.emails::class.simpleName}" )
    println("#  Запрос на получения всех писем "); println("#  Ответ на запрос (emails): ${person2.emails::class.simpleName}" )
    println("#  Запрос на получения всех писем "); println("#  Ответ на запрос (emails): ${person2.emails::class.simpleName}" )
    println("#  Запрос на получения всех писем "); println("#  Ответ на запрос (emails): ${person2.emails::class.simpleName}" )

    println()
    val person = Person("Jack", 47, 10450)

    println("#  Запрос на получения всех писем "); println("#  Ответ на запрос (emails): ${person.emails::class.simpleName}" )
    println("#  Запрос на получения всех писем "); println("#  Ответ на запрос (emails): ${person.emails::class.simpleName}" )
    println("#  Запрос на получения всех писем "); println("#  Ответ на запрос (emails): ${person.emails::class.simpleName}" )
    println("#  Запрос на получения всех писем "); println("#  Ответ на запрос (emails): ${person.emails::class.simpleName}" )
2
    println()
    val observer1: Observer = object : Observer{
        override fun onChange(property: ObservableProperty) {
            if (property.name == "for observer1") println("observer_1 получил новость: (propName='${property.name}', oldValue=${property.oldValue}, newValue=${property.newValue})")
        }
    }

    val observer2: Observer = object : Observer{
        override fun onChange(property: ObservableProperty) {
            if (property.name == "top secret") println("observer_2 получил новость: (propName='${property.name}', oldValue=${property.oldValue}, newValue=${property.newValue})")
        }
    }

    val observer3: Observer = object : Observer{
        override fun onChange(property: ObservableProperty) {
            if (property.name == "top secret" || property.name == "for observer1") println("observer_3 получил новость: (propName='${property.name}', oldValue=${property.oldValue}, newValue=${property.newValue})")
        }
    }

    val observable = Observable()
    observable.observers.addAll(mutableListOf(observer1, observer2, observer3))
    observable.notifyObservers(ObservableProperty("top secret", Person2::class, Person::class))
    observable.notifyObservers(ObservableProperty("spam", "buy it", "buy now!"))
    observable.notifyObservers(ObservableProperty("for observer1", "unknow", person))
    observable.notifyObservers(ObservableProperty("ERR100R", 0b1010100u, 0b1010100110u))

    println()

    val personObserver: Observer = object : Observer {
        override fun onChange(property: ObservableProperty) {
            println("-> Новое событие: (propName='${property.name}', oldValue=${property.oldValue}, newValue=${property.newValue})")

            when (property.name) {
                "name" -> println("# Сотрудник ${property.oldValue} сменил имя на ${property.newValue}")
                "age" -> if (property.oldValue as Int > property.newValue as Int) println("# Сотрудник помолодел на ${property.oldValue as Int - property.newValue as Int} лет!")
                else println("# Сотрудник посторел на ${property.newValue as Int - property.oldValue as Int} лет!")
                "salary" -> if (property.oldValue as Int > property.newValue as Int) println("# Зарплата сотрудника понижена на ${property.oldValue as Int - property.newValue as Int} р. Нам очень жаль!")
                else println("# Зарплата сотрудника увеличенна на ${property.newValue as Int - property.oldValue as Int} р. Поздравляем!")
                else -> println("# Это событие не интересует компанию")
            }
        }
    }
    person.observers += personObserver

    person.name = "Mary"
    person.salary = 90000
    person.age = 18
    person.salary = 200000
    person.age = 34
    person.salary = 70000
    person.name = "Anna"
    person.salary = 71900
    person.notifyObservers(ObservableProperty("Я уволняюсь", "конфликты, дедлайны, сресс", "свободная, прекрасная жизнь"))
}

internal data class Point(val x: Int, val y: Int){

    // constructor() : this (0, 0)

    operator fun plus(other: Point) =   Point(x + other.x, y + other.y)
    operator fun minus(other: Point) =  Point(x - other.x, y - other.y)
    operator fun times(other: Point) =  Point(x * other.x, y * other.y)
    operator fun div(other: Point) =    Point(x / other.x, y / other.y)
    // operator fun mod(other: Point) =    Point(x % other.x, y % other.y)

    override fun toString(): String {
        return "Point($x; $y)"
    }
}

internal operator fun Point.get(index: Int): Int{
    return when(index){
        0 -> this.x
        1 -> this.y
        else -> 0
    }
}

internal class Email

internal fun loadEmails(): List<Email> {
    println("// Загрузка писем из базы данных...")
    Thread.sleep(1000)
    println("// Загрузка завершена!")
    return emptyList()
}

internal class Person (name: String = "unnamed", age: Int = 0, salary: Int = 0) : Observable(){
    init { println("// Создание экземпляра Person") }
    val emails by lazy { loadEmails() }
    var name: String = name; set(value) { this.notifyObservers( ObservableProperty("name", this.name, value,)); field = value }
    var age: Int = age; set(value) { this.notifyObservers(ObservableProperty("age", this.age, value)); field = value }
    var salary: Int = salary; set(value) { this.notifyObservers(ObservableProperty("salary", this.salary, value)); field = value }
    override fun toString(): String {
        return "Person(name='$name', age='$age', salary='$salary')"
    }

}

internal class Person2 {
    init { println("// Создание экземпляра Person_2") }
    private var _emails: List<Email>? = { println("// Загрузка почтового менеджера..."); null}()
    val emails: List<Email>
        get(){
            println("// Попытка получения списка писем пользователя...")
            if (_emails == null) this._emails = loadEmails()
            else println("// Найдены сохраненные данные...")
            return _emails!!
        }
}

data class ObservableProperty(var name: String, var oldValue: Any?, var newValue: Any?)

interface Observer {
    fun onChange(property: ObservableProperty): Unit
}

internal open class Observable {
    val observers = mutableListOf<Observer>()
    fun notifyObservers(property: ObservableProperty){
        println("// ${this::class.simpleName} уведомляет участников о событии: (propName='${property.name}', oldValue=${property.oldValue}, newValue=${property.newValue})")
        for (obs in observers) {
            obs.onChange(property)
        }
    }
}