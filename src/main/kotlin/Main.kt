import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

const val PERCENTAGE_COMMISSION = 0.75

fun calculateCommission(value: BigInteger, percentageCommission: Double): BigInteger {
    val result = value.toBigDecimal().multiply(percentageCommission.toBigDecimal().divide(100.toBigDecimal()))
        .setScale(0, RoundingMode.HALF_UP).toBigInteger()

    if (result.compareTo(3500.toBigInteger()) > 0) return result
    else return 3500.toBigInteger()
}

fun convertInPen(valRubPen: BigDecimal): BigInteger {
    return valRubPen.multiply(100.toBigDecimal()).toBigInteger()
}

fun main() {
    println("Enter the amount in the format RUB.PEN")
    val inString: String? = readLine()
    try {
        val inMoneyTransferRubPen: BigDecimal = inString?.toBigDecimal() ?: 0.00.toBigDecimal()

        val moneyTransferInPen = convertInPen(inMoneyTransferRubPen)

        val amount = calculateCommission(moneyTransferInPen, PERCENTAGE_COMMISSION)

        println("Comission $amount penny")

    } catch (e: NumberFormatException) {
        println("Invalid value entered")
    }
}