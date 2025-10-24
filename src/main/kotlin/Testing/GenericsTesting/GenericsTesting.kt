package core.Testing.GenericsTesting

/**
 * Изучение работы обобщений.
 */
fun genericsTesting() {
    val boxInt = Box(42)
    val boxString = Box("Hello")
    println("Box Int: ${boxInt.value}")
    println("Box String: ${boxString.value}")

    println()
    val numberContainer = Container(123)
    val doubleContainer = Container(45.67)
    println("Number container: ${numberContainer.getItem()}")
    println("Double container: ${doubleContainer.getItem()}")

    println()
    val nonNullBox = NonNullBox("Text")
    val nullableBox: NullableBox<String?> = NullableBox(null)
    println("Nullable box: ${nullableBox.value}")

    println()
    val stringList = listOf("a", "b", "c")
    val intList = listOf(1, 2, 3)

    println("String list is List<String>: ${stringList.isInstanceOf<List<String>>()}")
    println("Int list is List<Int>: ${intList.isInstanceOf<List<Int>>()}")

    val strings = filterByType<String>(listOf("hello", 123, "world", 456, true))
    println("Filtered strings: $strings")

    println()
    val catProducer: Producer<Cat> = CatProducer()
    val animalProducer: Producer<Animal> = catProducer
    val animal: Animal = animalProducer.produce()
    println("Produced animal: ${animal.name}")

    val cats = listOf(Cat("Whiskers"), Cat("Fluffy"))
    val animals: List<Animal> = cats
    printAnimals(animals)

    println()
    val animalFeeder: Consumer<Animal> = object : Consumer<Animal> {
        override fun consume(item: Animal) {
            println("Feeding ${item.name}")
        }
    }
    val catFeeder: Consumer<Cat> = animalFeeder
    cats.forEach { catFeeder.consume(it) }

    println()
    val catList = mutableListOf(Cat("Tom"), Cat("Jerry"))
    copyAnimalsReadOnly(catList)

    val animalList = mutableListOf<Animal>(Cat("Simba"), Dog("Rex"))
    addAnimals(animalList)

    println()
    printListElements(listOf(1, 2, 3))
    printListElements(listOf("a", "b", "c"))

    val anyList: List<*> = listOf(1, "hello", true)
    println("First element: ${anyList[0]}")

    println()
    val list = listOf(1, 2, 3, 4, 5)
    val swapped = list.swap(1, 3)
    println("Swapped list: $swapped")

    println()
    val pair = Pair("key", 42)
    val triple = Triple("a", 1, true)
    println("Pair: ${pair.first} -> ${pair.second}")
    println("Triple: ${triple.first}, ${triple.second}, ${triple.third}")

    println()
    val stringBuilderProcessor = StringBuilderProcessor()
    stringBuilderProcessor.process(StringBuilder("text"))

    println()
    val lazyValue: String by lazy {
        println("Computing...")
        "Hello, World!"
    }
    println("Lazy value: $lazyValue")
    println("Lazy value: $lazyValue")

    println()
    val items: List<Any> = listOf("text", 123, 45.67, "another")
    val numbers = items.filterIsInstance<Number>()
    println("Filtered numbers: $numbers")
}

internal class Box<T>(val value: T)

internal open class Animal(val name: String)
internal class Cat(name: String) : Animal(name)
internal class Dog(name: String) : Animal(name)

internal class Container<T : Number>(private val item: T) {
    fun getItem(): T = item
}

internal class NonNullBox<T : Any>(val value: T)
internal class NullableBox<T>(val value: T)

internal inline fun <reified T> List<*>.isInstanceOf(): Boolean {
    return all { it is T }
}

internal inline fun <reified T> filterByType(list: List<Any>): List<T> {
    return list.filterIsInstance<T>()
}

internal interface Producer<out T> {
    fun produce(): T
}

internal class CatProducer : Producer<Cat> {
    override fun produce(): Cat = Cat("Mittens")
}

internal fun printAnimals(animals: List<Animal>) {
    animals.forEach { println("Animal: ${it.name}") }
}

internal interface Consumer<in T> {
    fun consume(item: T)
}

internal fun copyAnimalsReadOnly(animals: List<Animal>) {
    animals.forEach { println("Copying ${it.name}") }
}

internal fun addAnimals(animals: MutableList<in Cat>) {
    animals.add(Cat("New Cat"))
}

internal fun printListElements(list: List<*>) {
    println("List size: ${list.size}")
    list.forEachIndexed { index, any -> println("$index: $any") }
}

internal fun <T> List<T>.swap(index1: Int, index2: Int): List<T> {
    val result = this.toMutableList()
    val tmp = result[index1]
    result[index1] = result[index2]
    result[index2] = tmp
    return result
}

internal class Pair<A, B>(val first: A, val second: B)
internal class Triple<A, B, C>(val first: A, val second: B, val third: C)

internal interface Serializable
internal interface CharSequence

internal class StringBuilderProcessor {
    fun process(item: StringBuilder) {
        println("Processing: $item")
    }
}

internal class InvariantBox<T>(var item: T)

internal class CovariantBox<out T>(private val item: T) {
    fun getItem(): T = item
}

internal class ContravariantBox<in T> {
    fun consume(item: T) {
        println("Consuming $item")
    }
}

internal class SortedList<T : Comparable<T>>(private val items: List<T>) {
    fun getSorted(): List<T> = items.sorted()

    fun isSorted(): Boolean {
        return items == items.sorted()
    }
}

internal fun <T, R> List<T>.mapCustom(transform: (T) -> R): List<R> {
    val result = mutableListOf<R>()
    for (item in this) {
        result.add(transform(item))
    }
    return result
}

internal fun <T> List<T>.filterCustom(predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in this) {
        if (predicate(item)) {
            result.add(item)
        }
    }
    return result
}