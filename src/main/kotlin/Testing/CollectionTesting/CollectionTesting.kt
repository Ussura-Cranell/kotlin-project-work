package core.Testing.CollectionTesting

import io.github.serpro69.kfaker.Faker
import kotlin.random.Random

val faker = Faker()

fun collectionTesting(){

    val usersCollection = hashSetOf<User>()

    println(User.getTableHead())
    repeat(1000){
        usersCollection.add(User.randomUser())
    }

    usersCollection.sortedBy { it.company }.forEach { println(it) }

    val group = usersCollection.groupBy { it.company }
    val maxNumColleagues = group.values.filter { it.size > 1 }.maxByOrNull { it.size }

    println("\nКоллеги по работе:")
    println(User.getTableHead())
    maxNumColleagues?.sortedBy { it.id }?.forEach { println(it) } ?: println("Совпадений не найдено!")
}

data class User (
    var id:Int,
    var firstName: String,
    var lastName: String,
    var age: Int,
    var company: String){

    companion object Builder{
        private var userID = 0
        fun randomUser(): User{

            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            val age = Random.nextInt(10, 100)
            val company = faker.company.name()

            return User(firstName, lastName, age, company)
        }

        fun getTableHead(): String{
            return """
            |   ID   |   First Name   |   Last Name   |   Age   |                 Company                 |
            | ------ |--------------- | ------------- | ------- | --------------------------------------- |
        """.trimIndent()
        }
    }
    constructor(
        firstName: String = "unknow",
        lastName: String = "unknow",
        age: Int = -1, company: String) : this(userID++, firstName, lastName, age, company)

    override fun toString(): String {
        return "| %6d | %14s | %13s | %7d | %39s |".format(id, firstName, lastName, age, company)
    }
}