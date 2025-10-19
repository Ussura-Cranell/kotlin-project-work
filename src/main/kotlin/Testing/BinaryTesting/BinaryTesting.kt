package core.Testing.BinaryTesting

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

fun binaryTesting(){

    val byte: Byte = 199.toByte()
    println("Byte to Hex: %02X".format(byte)) // C7
    println("Byte to Oct: %03o".format(byte)) // 307

    val a: Byte = 0b1100_1010.toByte()
    val b: Byte = 0b1010_1100.toByte()

    fun Byte.toBin(): String = this.toUByte().toString(2).padStart(8, '0')

    println("""
        value_a : %s
        value_b : %s
            AND : %s
            OR  : %s
            XOR : %s
            INV : %s
            
    """.trimIndent().format(
            a.toBin(),
            b.toBin(),
            (a and b).toBin(),
            (a or b).toBin(),
            (a xor b).toBin(),
            a.inv().toBin()
    ))

    val original: Byte = 119

    println("""
         Исходное значение : %4s ( %s )
              Обратный код : %4s ( %s )
        Дополнительный код : %4s ( %s ) 
    """.trimIndent().format(
            original, original.toBin(),
            original.inv(), original.inv().toBin(),
            original.inv() + 1, (original.inv() + 1).toByte().toBin()
    ))

    val string = "Hello, world!"
    val byteString = string.toByteArray(Charsets.UTF_8)
    val buildString = StringBuilder()

    print("Byte: "); byteString.forEachIndexed { i, v ->
        buildString.append(v.toHexString().uppercase())
        buildString.append(' ')
    }

    val toString = buildString.toString().replace(" ", "").hexToByteArray(HexFormat.UpperCase).toString(Charsets.UTF_8)

    println(buildString)
    println(toString)

    fun Byte.isBitSet(pos: Int): Boolean {
        require(pos in 0..7)
        return (this.toInt() and (1 shl pos)) != 0
    }

    fun Byte.setBit(pos: Int): Byte = (this.toInt() or (1 shl pos)).toByte()
    fun Byte.clearBit(pos: Int): Byte = (this.toInt() and (1 shl pos).inv()).toByte()

    val testByte: Byte = 0b1010_1100.toByte()
    println("Бит 3 установлен? ${testByte.isBitSet(3)}")
    println("Установка бита 0: ${testByte.setBit(0).toString(2)}")
    println("Сброс бита 7: ${testByte.clearBit(7).toString(2)}")

}

