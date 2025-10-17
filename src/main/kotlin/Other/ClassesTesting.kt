package core.Other

fun classesTesting(){
    var animal: Animal = Cat()
    if (animal is Cat) {
        animal.makeSound()
        animal = Dog(animal)
    }

    if (animal is Defendable) animal.defend()

    val soundable: Soundable = animal as? Soundable ?: object : Soundable {
        override fun makeSound() {
            println("make sound")
        }
    }

    print("${animal::class.simpleName}: ")
    soundable.makeSound()

    Class1().op1()

    println(SomethingClass.SecondSomethingClass::class.simpleName)
    println(SomethingClass::class.simpleName)

    fun makeSomething (x: MakeSomething) {
        x.something()
    }

    makeSomething(SomethingClass) // Передача внешнего класса
    val something = SomethingClass()
    val somethingPigeon = something.Pigeon("Rion")
    somethingPigeon.toSpy()

    val threadSomethingClass1 = SomethingClass.ThreadSomethingClass("param1", "param2")
    val threadSomethingClass2 = SomethingClass.ThreadSomethingClass("param1", "param2")

    println("equals: ${threadSomethingClass1 == threadSomethingClass2}, link: ${threadSomethingClass1 === threadSomethingClass2}" +
            ", hash1: ${threadSomethingClass1.hashCode()}, hash2: ${threadSomethingClass1.hashCode()}")

}
abstract class Animal

class Cat : Animal(), Soundable {
    override fun makeSound() = println("*sounds of purring*")

}
class Dog(val x: Soundable): Animal(), Defendable, Soundable by x{
    override fun defend() = println("leave bites")

    override fun canDefend(): Boolean = true
}

interface Soundable {
    fun makeSound()
}

interface Defendable {
    fun defend()
    fun canDefend(): Boolean
}

interface IF1{
    fun op1() {
        println("op1 IF1")
    }
}

interface IF2{
    fun op1() {
        println("op1 IF2")
    }
}

class Class1 : IF1, IF2 {
    override fun op1() {
        println("op1 class1")
        super<IF1>.op1()
        super<IF2>.op1()
    }
}

interface MakeSomething {
    fun something(){
        println("something!")
    }
}

class SomethingClass {
    private val secretValue: Int = 4
    companion object SecondSomethingClass : MakeSomething

    inner class Pigeon{
       var name: String
           get() = "non-$field"

        constructor(value: String){
            this.name = value
        }
        constructor(value: Int) : this (value.toString())

        fun toSpy(){
            println("($name) to spy: $secretValue")
        }
    }

    data class ThreadSomethingClass(var param1: String, var param2: String)
}