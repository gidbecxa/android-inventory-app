import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DateFormat.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun main() {

    nowDate()

    println()
    println("*****\n")

    extractFromString("Google HQ, Mountain View, CA, United States Of America")
    println("*****\n")

    generateCode()
    println()
    println("*****\n")

    addZeros(1)
    addZeros(100)
    addZeros(99)
    addZeros(7777)
    println()
    println("*****\n")

    println("***Format double function***\n")
    formatDouble(2.5E10)
    formatDouble(9.99999999E8)
    formatDouble(1.0000250255E7)
    println("*****\n")

    sumUp()
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun nowDate() {
    val day = Calendar.DAY_OF_MONTH.toString()
    val month = Calendar.MONTH.toString()
    println ("$day $month")

    val rightNow = Calendar.getInstance()
    println("Calendar get instance: $rightNow")
    println()

    val rightNow2 = Calendar.getInstance().time
    println("Calendar date: $rightNow2")
    println()

    val date = Date()
    println ("date: $date")
    println()

    val currentDate = SimpleDateFormat("dd-MM-yyyy").format(date)
    println("current date from Date: $currentDate\n")

    val time = SimpleDateFormat("HH:mm:ss").format(date)
    println("current time from Date: $time\n")

    val currentDate2 = getDateInstance().format(date).toString()
    println("get date instance from Date: $currentDate2\n")

    val dateTime = getDateTimeInstance().format(date).toString()
    println("get Date Time Instance from Date: $dateTime\n")

    val currentTime = getTimeInstance().format(date).toString()
    println("get time instance from Date: $currentTime\n")

    val calendar = Calendar.getInstance().time
    println("calendar full date: $calendar\n")

    val calendarTime = getTimeInstance().format(calendar).toString()
    println("calendar time: $calendarTime\n")

    val calendarDate = getDateInstance().format(calendar).toString()
    println("calendar date: $calendarDate\n")

    val calendarDateTime = getDateTimeInstance().format(calendar).toString()
    println("calendar date time: $calendarDateTime\n")

    val currentDate10 = SimpleDateFormat("ddMMyyyy").format(calendar).toString()
    println("current date from Calendar: $currentDate10\n")

    val defaultTitle = "Pricelist $currentDate10"
    println("default title: $defaultTitle\n")

    println ("default title length: ${defaultTitle.length}")

}

fun extractFromString(string: String) {
    println("Printing string extract...\n")
    val startIndex = 0
    val endIndex = 20
    val subString = string.subSequence(startIndex, endIndex)
    println("$subString...")
    println()
}

@RequiresApi(Build.VERSION_CODES.N)
fun generateCode() {
    println("Random code...")

    val sourceChars = "0123456789"
    val randomCode = Random().ints(8, 0, sourceChars.length)
                .toArray()
                .map(sourceChars::get)
                .joinToString("")
    val sortCode = "PL$randomCode"

    println(sortCode)
}

private fun formatDouble(double: Double) {
    val doubleString = String.format(Locale.UK, "%2f", double)
    println("Your double as as a string and decimal is: $doubleString")

    println("Using DecimalFormat...")
    val doubleDecimalFormat = DecimalFormat("00.00")
    val doubleToDecimal = doubleDecimalFormat.format(double)
    println("Your double as as a string and decimal is: $doubleToDecimal")

    println("Using BigDecimal...")
    val doubleRounded = BigDecimal(double).setScale(2, RoundingMode.HALF_UP).toString()
    println("Your double as as a string and decimal is: $doubleRounded\n")
}

fun addZeros(number: Int) {
    println("Invoice code...")

    var numberToString = number.toString()
    while (numberToString.length < 8) {
        numberToString = "0" + numberToString
    }
    //numberToString = "00" + numberToString
    println("PL$numberToString")
}

fun sumUp() {

    val num1 = 50
    val num2 = 30
    val sum = num1 + num2
    println ("The sum of $num1 and $num2 gives $sum\n*****\n")

    val xNull = null
    println("xNull returns $xNull\n")
    var x1: Double?
    var x10 = null

    var xDouble: Double
    xDouble = 20.0
    println("xDouble returns $xDouble\n")
    x1 = xDouble
    println("x1 = xDouble returns $x1\n")
    x1 = xNull
    println("x1 = xNull returns $x1\n")

    var xDouble2 = 25.5
    println("xDouble2 returns $xDouble2\n")

    var xString = xDouble2.toString()
    println("xDouble2 to String (xString) returns $xString\n")

    var xDouble3 = xString.toDouble()
    println("xString back to Double (xDouble3) returns $xDouble3\n")

    x1 = xDouble3
    println("x1 = xDouble3 returns $x1\n")

    var xNullString = xNull.toString()
    println("xNull to String (xNullString) returns $xNullString\n")

    //var xNullToDouble = xNullString.toDouble()
    //println("xNullString to Double returns $xNullToDouble")

    var xString2 = "200,25"

    //var xString2ToDbl = xString2.toDouble()
    //println("xString2 to Double returns $xString2ToDbl")

    var xString2ToDbl = xString2.toDoubleOrNull()
    println("xString2 to Double returns $xString2ToDbl\n")

    var bool1 = (xString2ToDbl == xNull)
    println("xString2ToDbl == xNull returns $bool1\n")

    x1 = xString2ToDbl
    println("x1 = xString2ToDbl returns $x1\n")

    println ("xString2ToDbl to String returns ${xString2ToDbl.toString()}\n")

    if (xString2ToDbl != null) {
        xDouble2 = xString2ToDbl
        println ("xDouble2 = xString2ToDbl returns $xDouble2\n")
    } else { println("Operation impossible!\n") }

    val sumTotal = 250.0
    val sumTotalFormatted = NumberFormat.getCurrencyInstance().format(sumTotal)
    println("Total: $sumTotalFormatted\n")

    val sumTotalFormatted2 = sumTotalFormatted.toString()
    println("Total as a String: $sumTotalFormatted2\n")

    var sumTotalFormatted3 = sumTotalFormatted2.filter { it.isLetterOrDigit() }
    println("Formatted total filtered: $sumTotalFormatted3\n")

    val reg = Regex("[^0-9,.]")
    sumTotalFormatted3 = reg.replace(sumTotalFormatted2, "")
    println("Sum Total extracted from currency instance: $sumTotalFormatted3\n")

    val sumTotalAsDouble = sumTotalFormatted3.toDouble()
    println("Sum Total as double: $sumTotalAsDouble\n")

    var frenchPrice = "255,25 Euros"
    println("frenchPrice: $frenchPrice\n")
    frenchPrice = reg.replace(frenchPrice, "")
    println("frenchPrice number extracted returns $frenchPrice\n")

    val reg2 = Regex("[^0-9]")
    frenchPrice = reg2.replace(frenchPrice, ".")
    println("French price format to English decimal format: $frenchPrice\n")

    val frenchPriceExtractDbl = frenchPrice.toDouble()
    println("French price as Double in English decimal format: $frenchPriceExtractDbl\n")

    val sumTtl = "0.0"
    val sumTtlToDbl = sumTtl.toDouble()
    println("sumTtl to Double = $sumTtlToDbl")

}