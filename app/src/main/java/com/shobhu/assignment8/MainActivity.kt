package com.shobhu.assignment8

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView

    // state
    private var currentInput: String = ""      // current number being typed
    private var pendingOperator: String? = null
    private var leftValue: Double? = null      // left operand
    private var justCalculated = false        // to replace input on next digit after "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

        // digits
        val digits = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        digits.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onDigit((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btnDot).setOnClickListener { onDot() }

        // operators
        findViewById<Button>(R.id.btnPlus).setOnClickListener { onOperator("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { onOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperator("/") }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { onPercent() }

        // controls
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClear() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { onBackspace() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEquals() }

        updateDisplay()
    }

    private fun onDigit(d: String) {
        if (justCalculated) {
            // user typed digit after result -> start new input
            currentInput = ""
            justCalculated = false
            pendingOperator = null
            leftValue = null
            tvExpression.text = ""
        }
        // avoid leading zeros like "000"
        if (currentInput == "0") currentInput = d else currentInput += d
        updateDisplay()
    }

    private fun onDot() {
        if (justCalculated) {
            currentInput = "0."
            justCalculated = false
            pendingOperator = null
            leftValue = null
            tvExpression.text = ""
        } else if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) currentInput = "0."
            else currentInput += "."
        }
        updateDisplay()
    }

    private fun onOperator(op: String) {
        if (currentInput.isEmpty() && leftValue == null) {
            // pressing operator with nothing typed -> allow negative sign with minus
            if (op == "-") {
                currentInput = "-"
                updateDisplay()
            }
            return
        }

        if (currentInput.isNotEmpty()) {
            val cur = currentInput.toDoubleOrNull()
            if (cur != null) {
                if (leftValue == null) {
                    leftValue = cur
                } else if (pendingOperator != null) {
                    leftValue = calculate(leftValue!!, cur, pendingOperator!!)
                }
            }
        }

        pendingOperator = op
        currentInput = ""
        justCalculated = false
        updateDisplay()
    }

    private fun onPercent() {
        // apply percent to current input
        val cur = currentInput.toDoubleOrNull()
        if (cur != null) {
            currentInput = (cur / 100.0).toString().trimEndZero()
            updateDisplay()
        }
    }

    private fun onBackspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            updateDisplay()
        }
    }

    private fun onClear() {
        currentInput = ""
        leftValue = null
        pendingOperator = null
        justCalculated = false
        tvExpression.text = ""
        tvResult.text = "0"
    }

    private fun onEquals() {
        if (pendingOperator == null) {
            // nothing to compute
            if (currentInput.isNotEmpty()) {
                tvResult.text = currentInput.trimEndZero()
                justCalculated = true
            }
            return
        }

        val right = currentInput.toDoubleOrNull()
        if (right == null) {
            // nothing typed; treat as left op left (e.g., 5 + = -> 5 + 5)
            if (leftValue != null) {
                val result = calculate(leftValue!!, leftValue!!, pendingOperator!!)
                tvResult.text = result.toString().trimEndZero()
                tvExpression.text = buildExpressionString(leftValue!!, pendingOperator!!, leftValue!!)
                leftValue = result
                justCalculated = true
            }
        } else {
            val result = calculate(leftValue ?: 0.0, right, pendingOperator!!)
            tvResult.text = result.toString().trimEndZero()
            tvExpression.text = buildExpressionString(leftValue ?: 0.0, pendingOperator!!, right)
            leftValue = result
            currentInput = ""
            justCalculated = true
        }
        // keep pendingOperator so user can chain "=" presses depending on behavior choices.
    }

    private fun calculate(a: Double, b: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> {
                if (abs(b) < 1e-12) {
                    Toast.makeText(this, "Can't divide by zero", Toast.LENGTH_SHORT).show()
                    a // return a unchanged (could be handled differently)
                } else a / b
            }
            else -> b
        }
    }

    private fun updateDisplay() {
        tvResult.text = if (currentInput.isEmpty()) (leftValue?.toString()?.trimEndZero() ?: "0") else currentInput.trimEndZero()
        tvExpression.text = buildExpressionPreview()
    }

    private fun buildExpressionPreview(): String {
        val left = leftValue?.toString()?.trimEndZero()
        val op = pendingOperator ?: ""
        val right = if (currentInput.isEmpty()) "" else currentInput
        return listOfNotNull(left, if (op.isEmpty()) null else op, if (right.isEmpty()) null else right)
            .joinToString(" ")
    }

    private fun buildExpressionString(left: Double, op: String, right: Double): String {
        return "${left.toString().trimEndZero()} $op ${right.toString().trimEndZero()}"
    }

    // extension to remove trailing .0 when not needed
    private fun String.trimEndZero(): String {
        if (!this.contains('.')) return this
        var s = this
        // handle scientific notation or parse double back to string with plain representation
        val d = this.toDoubleOrNull() ?: return this
        return d.toString().replace(Regex("""\.0+$"""), "").replace(Regex("""(\.\d*?[1-9])0+$"""), "$1")
    }

    private fun Double.trimEndZero(): String = this.toString().replace(Regex("""\.0+$"""), "")
}
