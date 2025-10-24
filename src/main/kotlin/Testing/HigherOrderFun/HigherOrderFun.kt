package core.Testing.HigherOrderFun

import kotlin.jvm.functions.FunctionN

/**
 * Изучение функций высшего порядка.
 */

fun higherOrderFun() {

    val fun1: (Int, Int) -> Int? // тип: функция возвращающая значение, допускающее null
    val fun2: ((Int, Int) -> Int)? // тип: функция возвращающее значение, или null

    val sum_1 = Something2()(1,2)
    val sum_2 = Something2().invoke(-35,+765)

    val fun10: (Int, (Int) -> Unit) -> Unit = ::times3000
    fun10(3) { println(it) }

    println("Kotlin version: ${KotlinVersion.CURRENT}")

    println("=== 1. Функции как переменные ===")
    var operation: (Int, Int) -> Int = ::add
    println("Сложение: ${operation(5, 3)}")

    operation = ::multiply
    println("Умножение: ${operation(5, 3)}")

    operation = { a, b -> a - b }
    println("Вычитание: ${operation(5, 3)}")

    println("\n=== 2. Nullable функции ===")
    var funOrNull: ((Int, Int) -> Int)? = null
    println("funOrNull is null: ${funOrNull == null}")

    funOrNull = ::add
    println("funOrNull after assignment: ${funOrNull?.invoke(10, 20)}")

    println("\n=== 3. Классы как функции ===")
    val multiplier = Multiplier(2)
    val result = multiplier(5)
    println("Multiplier class as function: $result")

    val something = Something()
    val sum1 = something(1, 2)
    val sum2 = something.invoke(3, 4)
    println("Something results: $sum1, $sum2")

    println("\n=== 4. Функции высшего порядка как параметры ===")
    calculateAndPrint(10, 5, ::add)
    calculateAndPrint(10, 5, ::multiply)
    calculateAndPrint(10, 5) { a, b -> a * a + b * b }

    println("\n=== 5. Функции возвращающие функции ===")
    val addFive = createAdder(5)
    val addTen = createAdder(10)
    println("Add five to 3: ${addFive(3)}")
    println("Add ten to 3: ${addTen(3)}")

    println("\n=== 6. Inline функции ===")
    executeWithLogging("Операция A") {
        println("Выполняю операцию A")
        Thread.sleep(100)
    }

    println("\n=== 7. Return в лямбдах ===")
    demoReturnInLambda()

    println("\n=== 8. Коллекции и функции высшего порядка ===")
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val evenNumbers = numbers.filter { it % 2 == 0 }
    println("Четные числа: $evenNumbers")

    val squared = numbers.map { it * it }
    println("Квадраты: $squared")

    val sum = numbers.reduce { acc, num -> acc + num }
    println("Сумма: $sum")

    numbers.forEach { print("$it ") }
    println()

    println("\n=== 9. Цепочки вызовов ===")
    val resultChain = numbers
        .filter { it > 5 }
        .map { it * 2 }
        .take(3)
        .fold(0) { acc, num -> acc + num }
    println("Цепочка преобразований: $resultChain")

    println("\n=== 10. Сохранение состояния ===")
    val counter = createCounter()
    println("Счетчик: ${counter()}")
    println("Счетчик: ${counter()}")
    println("Счетчик: ${counter()}")

    println("\n=== 11. Композиция функций ===")
    val addTwo = { x: Int -> x + 2 }
    val triple = { x: Int -> x * 3 }
    val addThenTriple = compose(triple, addTwo)
    println("Композиция (2 + 2) * 3 = ${addThenTriple(2)}")

    println("\n=== 12. Каррирование ===")
    val curriedAdd = curryAdd(5)(3)
    println("Каррированное сложение: $curriedAdd")
}

internal class Something2 : Function2<Int, Int, Int> {
    override fun invoke(p1: Int, p2: Int): Int {
        val sum = p1 + p1
        println("$p1 + $p2 = $sum")
        return sum
    }
}

inline fun times3000(
    num: Int,
    noinline action: (Int) -> Unit  // Явно запрещаем встраивание лямбды
) {
    println("$num^2 = ${num*num}")
    action(num * num)
}

fun add(a: Int, b: Int): Int = a + b
fun multiply(a: Int, b: Int): Int = a * b

class Multiplier(private val factor: Int) : (Int) -> Int {
    override fun invoke(number: Int): Int = number * factor
}

class Something : (Int, Int) -> Int {
    override fun invoke(p1: Int, p2: Int): Int {
        val sum = p1 + p2
        println("$p1 + $p2 = $sum")
        return sum
    }
}

fun calculateAndPrint(a: Int, b: Int, operation: (Int, Int) -> Int) {
    val result = operation(a, b)
    println("$a op $b = $result")
}

fun createAdder(value: Int): (Int) -> Int {
    return { x -> x + value }
}

inline fun executeWithLogging(name: String, block: () -> Unit) {
    println("Начало: $name")
    val startTime = System.currentTimeMillis()
    block()
    val endTime = System.currentTimeMillis()
    println("Конец: $name (затрачено ${endTime - startTime} мс)")
}

fun demoReturnInLambda() {
    val numbers = listOf(1, 2, 3, 4, 5)

    numbers.forEach {
        if (it == 3) return@forEach
        print("$it ")
    }
    println()

    numbers.forEach(fun(number) {
        if (number == 3) return
        print("$number ")
    })
    println()
}

fun createCounter(): () -> Int {
    var count = 0
    return {
        count++
    }
}

fun curryAdd(a: Int): (Int) -> Int {
    return { b -> a + b }
}

fun <T, R, U> compose(f: (R) -> U, g: (T) -> R): (T) -> U {
    return { x -> f(g(x)) }
}
