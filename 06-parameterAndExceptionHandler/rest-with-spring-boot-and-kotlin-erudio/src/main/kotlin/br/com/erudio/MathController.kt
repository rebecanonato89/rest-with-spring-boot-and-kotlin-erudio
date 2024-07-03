package br.com.erudio

import br.com.erudio.exceptions.UnsupportedMathOperationException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class MathController {

    val counter: AtomicLong = AtomicLong()

    @RequestMapping(value = ["/sum/{numberOne}/{numberTwo}"])
    fun sum(@PathVariable(value="numberOne") numberOne: String,
            @PathVariable(value="numberTwo") numberTwo: String
    ): Double {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) throw UnsupportedMathOperationException("Please set a numeric value!")
        return convertToDouble(numberOne) + convertToDouble(numberTwo)
    }

    private fun convertToDouble(strNumber: String): Double {
       if(strNumber.isNullOrBlank()) return 0.0
        val number = strNumber.replace(",".toRegex(), ".")
        return if(isNumeric(number)) number.toDouble() else 0.0
    }

    private fun isNumeric(number: String): Boolean {
        if(number.isNullOrBlank()) return false
        val numberRegex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return number.matches(numberRegex)
    }
}
