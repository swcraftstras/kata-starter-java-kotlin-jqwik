import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.Report;
import net.jqwik.api.Reporting;
import net.jqwik.api.Tuple;
import net.jqwik.api.arbitraries.ListArbitrary;
import net.jqwik.api.constraints.Size;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*
Bowling Rules
The game consists of 10 frames. In each frame the player has two rolls to knock down 10 pins. The score for the frame
is the total number of pins knocked down, plus bonuses for strikes and spares.

A spare is when the player knocks down all 10 pins in two rolls. The bonus for that frame is the number of pins
knocked down by the next roll.

A strike is when the player knocks down all 10 pins on his first roll. The frame is then completed with a single roll.
The bonus for that frame is the value of the next two rolls.

In the tenth frame a player who rolls a spare or strike is allowed to roll the extra balls to complete the frame.
However no more than three balls can be rolled in tenth frame.

Requirements
Write a class Game that has two methods

void roll(int) is called each time the player rolls a ball. The argument is the number of pins knocked down.
int score() returns the total score for that game.
 */
class BowlingProperties {

    // Il y a 10 frames de 2 lancers max
    // soit 20 lancers
    // si on fait pas de strike ni spare

    // Un score ne peut pas dépasser 300

    //


    // Un strike : 10 quilles sur le premier lancer d'un frame


    // ➡️ que des non spare et non strike score = somme des quilles
    //  - que des 0 score 0


    @Property(seed = "-12322456160539622")
    @Label("que des non spare et non strike score")
    @Report(Reporting.GENERATED)
    void fdsdfs(@ForAll("_frames") @Size(10) List<Tuple.Tuple2<Integer, Integer>> frames) {

        //Pas de strike ni de spare
        Assume.that(frames.stream().allMatch(frame -> frame.get1() + frame.get2() < 10));

        // Act
        int score = score(frames);

        // Assert
        assertThat(score).isEqualTo(
                frames.stream()
                        .mapToInt(tuple -> tuple.get1() + tuple.get2())
                        .sum());

//        Assume.that(frame.first + frame.second < 10);
//        assertThat(primeFactors(anInt)).isEmpty();
    }

    private int score(List<Tuple.Tuple2<Integer, Integer>> frames) {
        return frames.stream()

                .mapToInt(tuple -> tuple.get1() + tuple.get2())

                .sum();
    }

    record Frame(int firstThrow, int secondThrow) {
    }

    @Provide
    ListArbitrary<Frame> _frames() {
        // générer des paires
        // filtrer les paires dont la somme est <10
        // filtrer les paires dont la somme est >=0

    // @Mathieu: on a lancé le jisti du code with me pour continuer à s'entednre. => fin de la récré, ma fille est réveillée
        // merci pour le kata, je dois vous laisser
        // je regarderai pourquoi je n'ai pas cette fenêtre de chat dans code with me pour une éventuelle prochaine fois :D

        // Devriefons en texte alors.
        // -> on sait écrire un génarateur simple
        // -> faire des assumptions pour réduire l'ensemble pris en compte
        // -> on sait rejouer un test avec une seed
        // -> peut etre creéer un générateur de type custom
        // -> apparemment on peut utiliser jqwik aussi pour des tests paramétrés classique (mode "example")

        // TIL : PBT : c'est plus facile de penser propriété que exemple
        //

        // Ensuite on voudrait en imaginant qu'on continue
        // - ré-écrire des tests paramétrés en utilisant jqwik
        // - tests de caractérisation
        //   - Gilded rose

        // un point d'alerte cependant sur la lisibilité des générateurs et notamment sur la manière de filtrer
        // typiquement => comment passer le filter ci-dessous directement en paramètre du générateur afin d'éviter le Assume.that ?
        // et plus généralement => est-ce qu'il faut les garder près des tests ou se faire une "bibliothèque" de générateurs ?
        return Arbitraries
                .integers()
                .between(1, 10)
                .tuple2()
                .filter(tuple -> tuple.get1() + tuple.get2() <= 10)
                .map(tuple -> new Frame(tuple.get1(), tuple.get2()))
                .list();


    }

    @Provide
    Arbitrary<List<Integer>> frames() {

        return Arbitraries.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31)
                .list()
                .ofMaxSize(10);
    }
    // sauf pour le dernier si on fait un strike
    //
    // Max des lancers?
    //

//    @Property
//    @Label("0 and 1 have no prime factors")
//    void noPrimeFactors(@ForAll @IntRange(max = 1) int anInt) {
//        assertThat(primeFactors(anInt)).isEmpty();
//    }

//    @Property
//    @Report(Reporting.GENERATED)
//    void all_factors_are_primes(@ForAll @IntRange(min = FIRST_PRIME_NUMBER) int anInt) {
//        List<Integer> primeFactors = primeFactors(anInt);
//
//        primeFactors.forEach(
//                factor -> assertThat(isPrime(factor)).isTrue()
//        );
//    }
//
//    @Property
//    void product_of_primes_is_factorized_to_original_primes(
//            @ForAll("primes") List<Integer> primes) {
//        long product = primes.stream().mapToLong(Long::valueOf)
//                .reduce(1L, (a, b) -> a * b);
//        Assume.that(product <= Integer.MAX_VALUE);
//
//        List<Integer> integers = primeFactors(Math.toIntExact(product));
//        primes.sort(Integer::compareTo);
//        assertThat(integers).isEqualTo(primes);
//    }
//
//    @Provide
//    Arbitrary<List<Integer>> primes() {
//        return Arbitraries.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31)
//                .list()
//                .ofMaxSize(10);
//    }
//
//    @Property
//    @Label("multiply all factors")
//    void multiplyAllFactors(@ForAll @IntRange(min = FIRST_PRIME_NUMBER) int anInt) {
//        List<Integer> primeFactors = primeFactors(anInt);
//
//        Integer allMultiply = primeFactors.stream()
//                .reduce(1, (a, b) -> a * b);
//        assertThat(allMultiply).isEqualTo(anInt);
//    }
//
//    @Property(maxDiscardRatio = 10)
//    @Label("a prime number is the only prime factor")
//    void primeIsOnlyFactor(@ForAll @IntRange(min = FIRST_PRIME_NUMBER) int anInt) {
//        Assume.that(Primes.isPrime(anInt));
//
//        List<Integer> primeFactor = primeFactors(anInt);
//        assertThat(primeFactor).containsExactly(anInt);
//    }
//
//    @Property
//    @Label("IAE for numbers < 0")
//    void iaeForNegativeNumbers(@ForAll @IntRange(min = Integer.MIN_VALUE, max = -1) int anInt) {
//        assertThatThrownBy(() -> primeFactors(anInt))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage(anInt + " is an invalid input.");
//    }
//
//    List<Integer> primeFactors(int number) {
//        if (number < 0) {
//            throw new IllegalArgumentException(number + " is an invalid input.");
//        }
//        if (number < 2) {
//            return Collections.emptyList();
//        }
//        List<Integer> integers = Primes.primeFactors(number);
//        System.out.println(number + " -> " + integers + "\n");
//        return integers;
//
//    }

}
