import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class KotestJsTest: StringSpec({

    "first Kotest test Js" {
        println("1 js kotest")
        1.toString() shouldBe "1"
    }
})