import net.jqwik.api.*
import net.jqwik.kotlin.api.JqwikIntRange
import org.apache.commons.math3.primes.Primes
import org.assertj.core.api.Assertions
import java.util.function.Consumer

internal class PrimeFactorsPropertiesKt {
    @Property
    @Label("0 and 1 have no prime factors")
    fun noPrimeFactors(@ForAll anInt: @JqwikIntRange(max = 1) Int) {
        Assertions.assertThat(primeFactors(anInt)).isEmpty()
    }

    @Property
    @Report(Reporting.GENERATED)
    fun all_factors_are_primes(@ForAll anInt: @JqwikIntRange(min = FIRST_PRIME_NUMBER) Int) {
        val primeFactors = primeFactors(anInt)
        primeFactors.forEach(
            Consumer { factor: Int? ->
                Assertions.assertThat(
                    Primes.isPrime(
                        factor!!
                    )
                ).isTrue()
            }
        )
    }

    @Property
    fun product_of_primes_is_factorized_to_original_primes(
        @ForAll("primes") primes: List<Int>
    ) {
        val product = primes.map(Int::toLong)
            .fold(1L) { a, b -> a * b }
//            .reduce(1L) { a: Long, b: Long -> a * b }
        Assume.that(product <= Int.MAX_VALUE)
        val integers = primeFactors(Math.toIntExact(product))

        Assertions.assertThat(integers).isEqualTo(primes.sorted())
    }

    @Provide
    fun primes(): Arbitrary<List<Int>> {
        return Arbitraries.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31)
            .list()
            .ofMaxSize(10)
    }

    @Property
    @Label("multiply all factors")
    fun multiplyAllFactors(@ForAll anInt: @JqwikIntRange(min = FIRST_PRIME_NUMBER, max = Int.MAX_VALUE) Int) {
        val primeFactors = primeFactors(anInt)
        val allMultiply = primeFactors.stream()
            .reduce(1) { a: Int, b: Int -> a * b }
        Assertions.assertThat(allMultiply).isEqualTo(anInt)
    }

    @Property(maxDiscardRatio = 10)
    @Label("a prime number is the only prime factor")
    fun primeIsOnlyFactor(@ForAll anInt: @JqwikIntRange(min = FIRST_PRIME_NUMBER) Int) {
        Assume.that(Primes.isPrime(anInt))
        val primeFactor = primeFactors(anInt)
        Assertions.assertThat(primeFactor).containsExactly(anInt)
    }

    @Property
    @Label("IAE for numbers < 0")
    fun iaeForNegativeNumbers(
        @ForAll anInt: @JqwikIntRange(min = Int.MIN_VALUE, max = -1) Int
    ) {

        Assertions.assertThatThrownBy { primeFactors(anInt) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("$anInt is an invalid input.")
    }

    fun primeFactors(number: Int): List<Int> {
        if (number < 0) {
            throw IllegalArgumentException("$number is an invalid input.");
        }

        if (number < 2) {
            return emptyList()
        }
        val integers = Primes.primeFactors(number)
        println("$number -> $integers\n")
        return integers
    }

    companion object {
        private const val FIRST_PRIME_NUMBER = 2
    }
}