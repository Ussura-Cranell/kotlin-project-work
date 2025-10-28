package core

// import core.Other.LeetCode.leetCodeSolution
import core.Testing.AnnotationTesting.annotationTesting
import core.Testing.BinaryTesting.binaryTesting
import core.Testing.ClassTesting.classesTesting
import core.Testing.CollectionTesting.collectionTesting
import core.Testing.FunctionTesting.functionTesting
import core.Testing.GenericsTesting.genericsTesting
import core.Testing.HigherOrderFun.higherOrderFun
import core.Testing.LambdaTesting.lambdaTesting
import core.Testing.MultitaskTesting.multitaskTesting
import core.Testing.NullableTesting.nullableTesting
import core.Testing.OverloadingTesting.overloadingTesting
import core.Testing.StructuredCompete.structuredCompete

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("\nНачало работы программы...\n")

    // functionTesting()
    // classesTesting()
    // lambdaTesting()
    // collectionTesting()
    // binaryTesting()
    // nullableTesting()
    // overloadingTesting()
    // higherOrderFun()
    // genericsTesting()
    // annotationTesting()
    // multitaskTesting()
    structuredCompete()

    println("\nЗавершение работы программы...\n")
}