package core.tools
import kotlin.math.absoluteValue
import core.Person as People

fun People.changeName(name: String) {
    this.name = name
}

val People.email: String
    get() = "${this.name}@mimail.com"

infix fun People.makeChild(people2: People): People{
    val id = this.id * 10 + people2.id
    val name = this.name + people2.name

    return People(id, name, name.hashCode().absoluteValue.toString())
}