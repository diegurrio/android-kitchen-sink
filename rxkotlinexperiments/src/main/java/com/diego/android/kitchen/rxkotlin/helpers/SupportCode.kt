fun exampleOf(description: String, action: () -> Unit) {
    println("\n--- Example of: $description ---")
    action()
}

fun <T> printWithLabel(label: String, element: T?) {
    println("$label $element")
}

sealed class Quote : Throwable() {
    class NeverSaidThat : Quote()
}

data class Movie(val title: String, val rating: Int)

const val landOfDroids = "Land of Droids"
const val wookieWorld = "Wookie World"
const val detours = "Detours"

const val episodeI = "The Phantom Menace"
const val episodeII = "Attack of the Clones"
const val theCloneWars = "The Clone Wars"
const val episodeIII = "Revenge of the Sith"
const val solo = "Solo: A Star Wars Story"
const val rogueOne = "Rogue One: A Star Wars Story"
const val episodeIV = "A New Hope"
const val episodeV = "The Empire Strikes Back"
const val episodeVI = "Return of the Jedi"
const val episodeVII = "The Force Awakens"
const val episodeVIII = "The Last Jedi"
const val episodeIX = "Episode IX"

val episodeIMovie = Movie("The Phantom Menace", 55)
val episodeIIMovie = Movie("Attack of the Clones", 66)
val episodeIIIMovie = Movie("Revenge of the Sith", 79)
val rogueOneMovie = Movie("Rogue One", 85)
val episodeIVMovie = Movie("A New Hope", 93)
val episodeVMovie = Movie("The Empire Strikes Back", 94)
val episodeVIMovie = Movie("Return Of The Jedi", 80)
val episodeVIIMovie = Movie("The Force Awakens", 93)
val episodeVIIIMovie = Movie("The Last Jedi", 91)
val tomatometerRatingsMovie = listOf(
    episodeI, episodeII, episodeIII, rogueOne, episodeIV, episodeV, episodeVI, episodeVII, episodeVIII)

const val itsNotMyFault = "It’s not my fault."
const val doOrDoNot = "Do. Or do not. There is no try."
const val lackOfFaith = "I find your lack of faith disturbing."
const val eyesCanDeceive = "Your eyes can deceive you. Don’t trust them."
const val stayOnTarget = "Stay on target."
const val iAmYourFather = "Luke, I am your father"
const val useTheForce = "Use the Force, Luke."
const val theForceIsStrong = "The Force is strong with this one."
const val mayTheForceBeWithYou = "May the Force be with you."
const val mayThe4thBeWithYou = "May the 4th be with you."
const val mayTheOdds = "And may the odds be ever in your favor"
const val liveLongAndProsper = "Live long and prosper"

val tomatometerRatings = listOf(
    episodeIMovie, episodeIIMovie, episodeIIIMovie, rogueOneMovie, episodeIVMovie, episodeVMovie, episodeVIMovie, episodeVIIMovie, episodeVIIIMovie)