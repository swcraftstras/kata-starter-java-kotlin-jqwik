import net.jqwik.api.*
import net.jqwik.api.Tuple.Tuple2
import net.jqwik.api.constraints.IntRange
import org.assertj.core.api.Assertions

@Label("FizzBuzz...")
internal class FizzBuzzExamplesKt {
    private fun fizzBuzz(anInt: Int): String {
        val fizzBuzzFor = getFizzBuzzFor(anInt)
        System.out.printf("%3d -> %s\n", anInt, fizzBuzzFor)
        return fizzBuzzFor
    }

    private fun getFizzBuzzFor(anInt: Int): String {
        if (anInt % 15 == 0) {
            return "FizzBuzz"
        }
        if (anInt % 3 == 0) {
            return "Fizz"
        }
        return if (anInt % 5 == 0) {
            "Buzz"
        } else Integer.toString(anInt)
    }

    @Group
    @Label("Calling fizzBuzz with...")
    internal inner class Properties {
        @Property
        @Label("multiple of 3 starts with 'Fizz'")
        fun multiple3StartsWithFizz(@ForAll("multipleOf3") anInt: Int): Boolean {
            return fizzBuzz(anInt).startsWith("Fizz")
        }

        @Provide
        @Suppress("unused")
        fun multipleOf3(): Arbitrary<Int> {
            return Arbitraries.integers().between(1, 33).map { i: Int -> i * 3 }
        }

        @Property
        @Label("multiple of 5 ends with 'Buzz'")
        fun divisibleBy5EndsWithBuzz(@ForAll anInt: @IntRange(min = 1, max = 100) Int) {
            Assume.that(anInt % 5 == 0)
            Assertions.assertThat(fizzBuzz(anInt))
                .endsWith("Buzz")
        }

        @Property
        @Label("number that is not a multiple of 3 nor 5 returns the number itself")
        fun indivisiblesReturnThemselves(@ForAll("notMultiple") anInt: Int) {
            Assertions.assertThat(fizzBuzz(anInt))
                .isEqualTo(Integer.toString(anInt))
        }

        @Property(maxDiscardRatio = 20)
        @Label("a multiple of 3 and 5 returns 'FizzBuzz'")
        @Report(Reporting.GENERATED)
        fun multipleOf3and5ReturnFizzBuzz(@ForAll anInt: @IntRange(min = 1, max = 100) Int): Boolean {
            Assume.that(anInt % 3 == 0)
            Assume.that(anInt % 5 == 0)
            return fizzBuzz(anInt) == "FizzBuzz"
        }

        @Provide
        @Suppress("unused")
        fun notMultiple(): Arbitrary<Int> {
            return Arbitraries.integers().between(1, 100)
                .filter { i: Int -> i % 3 != 0 }
                .filter { i: Int -> i % 5 != 0 }
        }
    }

    @Group
    @Label("FizzBuzz examples...")
    internal inner class Examples {
        @Example
        fun fizzBuzzOf2Is2() {
            Assertions.assertThat(fizzBuzz(2)).isEqualTo("2")
        }

        @Data
        @Suppress("unused")
        fun fizzBuzzExamples(): Iterable<Tuple2<Int, String>> {
            return Table.of(
                Tuple.of(1, "1"),
                Tuple.of(3, "Fizz"),
                Tuple.of(4, "4"),
                Tuple.of(5, "Buzz"),
                Tuple.of(7, "7"),
                Tuple.of(15, "FizzBuzz")
            )
        }

        @Property
        @FromData("fizzBuzzExamples")
        fun fizzBuzzWorks(@ForAll index: Int, @ForAll result: String?) {
            Assertions.assertThat(fizzBuzz(index)).isEqualTo(result)
        }
    }
}