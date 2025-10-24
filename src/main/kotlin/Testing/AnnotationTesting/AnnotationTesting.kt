package core.Testing.AnnotationTesting

/**
 * Изучение работы аннотаций и рефлексии.
 */
fun annotationTesting() {

    something { println("Hello, world!") }
    op { println("Hello, world!") }

    val battery: Battery = Battery()

    @Suppress("UNUSED_PARAMETER")
    fun unusedParameterExample(param: String) {
        println("This function has an unused parameter.")
    }

    unusedParameterExample("Hello")

    println("battery::class               : ${battery::class                 }")
    println("battery::class.simpleName    : ${battery::class.simpleName      }")
    println("battery::class.qualifiedName : ${battery::class.qualifiedName   }")
    println("battery::class.members       : ${battery::class.members         }")
    println("battery::class.constructors  : ${battery::class.constructors    }")
    println("battery::class.nestedClasses : ${battery::class.nestedClasses   }")

    println("\nЭлементы:")
    battery::class.members.forEachIndexed { i, it -> println("${i+1}. name=${it.name} parameters=${it.parameters} ${it::class.qualifiedName}") }

    println()
    val funk = ::foo // return KFunction1<Int, Unit>
    funk.call(49)

}

@Deprecated("Этот метод устарел", ReplaceWith("op(func)"), DeprecationLevel.WARNING)
internal fun something(func: () -> Unit) = func()

internal fun op(func: () -> Unit) = func()

@AnnotationTesting("Test1")
internal class Battery {
    @get:JvmName("obtainCertificate")
    @set:JvmName("putCertificate")
    var certificate: String = "BEGIN PRIVATE KEY"

    class MicroBattery {
        fun charge(){
            println("Микрозарядка")
        }
    }
}

@AnnotationTesting(name = "Test2")
class Jamimami

internal fun foo(x: Int) = println(x)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
internal annotation class AnnotationTesting(val name: String)