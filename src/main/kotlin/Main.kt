import java.lang.NumberFormatException

fun formattingNumberOfPeople(numberOfLikes: UInt): String {
    if (numberOfLikes % 10U == 1U) {
        return "человеку"
    } else return "людям"
}

fun main() {
    println("Задайте количество лайков")
    try {
        val value = readLine()
        val likes: UInt = value?.toUInt() ?: 0U

        println("\"Понравилось $likes ${formattingNumberOfPeople(likes)}\"")

    } catch (e: NumberFormatException) {
        println("Введено некорректное значение")
    }
}