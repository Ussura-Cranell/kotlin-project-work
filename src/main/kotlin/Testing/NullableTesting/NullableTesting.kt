package core.Testing.NullableTesting

import core.Testing.CollectionTesting.User

/**
 * Изучение работы nullable типов.
 */

fun nullableTesting(){
    var user: User? = User.randomUser()

    println(User.getTableHead())

    println(user.getUserID())
    println(user.getUserNAME())
    println(user.getUserAGE())
    println(user.getUserCOMPANY())
    user?.let { println(it.sendMain("google@gmail.com")) }

    user = null

    println(user.getUserID())
    println(user.getUserNAME())
    println(user.getUserAGE())
    println(user.getUserCOMPANY())
    user?.let { println(it.sendMain("google@gmail.com")) }

}

fun User?.isNull(): Boolean = this == null
fun User?.getUserID()       =   if (!isNull()) (this as User ).id           else null
fun User?.getUserNAME()     =   if (!isNull()) (this as User ).firstName    else null
fun User?.getUserAGE()      =   if (!isNull()) (this as User ).age          else null
fun User?.getUserCOMPANY()  =   if (!isNull()) (this as User ).company      else null
fun User.sendMain(email: String) = "${this.firstName} отправляет письмо на ${email}..."

