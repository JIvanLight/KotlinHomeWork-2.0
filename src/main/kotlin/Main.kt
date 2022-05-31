import java.math.*

const val MIN_CONDITION_DISCOUNT_1LVL: Int = 100000 //In penny
const val MAX_CONDITION_DISCOUNT_1LVL: Int = 1000000 //In penny
const val DISCOUNT_1LVL: Int = 10000 //In penny
const val MIN_CONDITION_DISCOUNT_2LVL: Int = 1000100 //In penny
const val DISCOUNT_2LVL: Int = 5 //In percent
const val REGULAR_CUSTOMER_DISCOUNT: Int = 1 //In percent

enum class Discount : CalculatorDiscount {

    ONELVL {
        override fun calculateDiscount(money: BigInteger): BigInteger {
            return money.subtract(DISCOUNT_1LVL.toBigInteger())
        }
    },
    TWOLVL {
        override fun calculateDiscount(money: BigInteger): BigInteger {
            return money.toBigDecimal().multiply(DISCOUNT_2LVL.toBigDecimal().divide(100.toBigDecimal())).toBigInteger()
        }
    },
    REGULAR {
        override fun calculateDiscount(money: BigInteger): BigInteger {
            return money.toBigDecimal().multiply(REGULAR_CUSTOMER_DISCOUNT.toBigDecimal().divide(100.toBigDecimal()))
                .toBigInteger()
        }
    },
    NON {
        override fun calculateDiscount(money: BigInteger): BigInteger {
            return 0.toBigInteger()
        }
    }
}

fun defineDiscount(money: BigInteger): Discount {
    return when {
        money.compareTo(MIN_CONDITION_DISCOUNT_1LVL.toBigInteger()) >= 0 &&
                money.compareTo(MAX_CONDITION_DISCOUNT_1LVL.toBigInteger()) <= 0
        -> Discount.ONELVL

        money.compareTo(MIN_CONDITION_DISCOUNT_2LVL.toBigInteger()) >= 0
        -> Discount.TWOLVL

        else -> Discount.NON
    }
}

fun convertInPen(valRub: BigInteger): BigInteger {
    return valRub.multiply(100.toBigInteger())
}

fun calculateDiscountForRegularConsumer(money: BigInteger, regularCustomer: Boolean): BigInteger {
    return if (regularCustomer) money.toBigDecimal().multiply((DISCOUNT_2LVL / 100).toBigDecimal()).toBigInteger()
    else 0.toBigInteger()
}

fun validRegularConsumer(inString: String?): Boolean {
    when (inString) {
        "Да", "да", "ДА", "дА" -> return true
    }
    return false
}

fun main() {
    println("Введите сумму покупок (в рублях)")
    val moneyIn = readLine()
    try {
        val moneyRub = moneyIn?.toBigInteger() ?: 0.toBigInteger()
        println("Покупатель является постоянным клиентом? (Да/Нет)")
        val regularCustomerIn = readLine()
        val regularCustomer: Boolean = validRegularConsumer(regularCustomerIn)
        val money = convertInPen(moneyRub)
        println("покупка - $moneyRub руб.")
        readLine()

        val discountType: CalculatorDiscount = defineDiscount(money)

        val _price: BigInteger

        if (discountType == Discount.ONELVL) {
            val price = discountType.calculateDiscount(money)
            _price = price
            val inRub = price.divide(100.toBigInteger())
            val inPenReminder = price.mod(100.toBigInteger())
            println(
                "после применения ${DISCOUNT_1LVL / 100} руб. скидки -" +
                        " $inRub руб. $inPenReminder коп.\n"
            )
        } else if (discountType == Discount.TWOLVL) {
            val discountPen = discountType.calculateDiscount(money)
            val price = money.subtract(discountPen)
            _price = price
            val inRub = price.divide(100.toBigInteger())
            val inPenReminder = price.mod(100.toBigInteger())
            println(
                "после применения $DISCOUNT_2LVL% скидки -" +
                        " $inRub руб. $inPenReminder коп.\n"
            )
        } else _price = money

        if (regularCustomer) {
            val discountTypee = Discount.REGULAR
            val discountPen = discountTypee.calculateDiscount(_price)
            val price = _price.subtract(discountPen)
            val inRub = price.divide(100.toBigInteger())
            val inPenReminder = price.mod(100.toBigInteger())
            println(
                "после применения $REGULAR_CUSTOMER_DISCOUNT% скидки -" +
                        " $inRub руб. $inPenReminder коп."
            )
        }
    } catch (e: NumberFormatException) {
        println("Введено некорректное значение")
    }
}