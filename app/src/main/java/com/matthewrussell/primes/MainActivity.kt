package com.matthewrussell.primes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.WindowManager
import android.widget.*
import com.hanks.htextview.base.HTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get the views
        var resultTextView : HTextView = findViewById(R.id.resultTextView)
        var numberEditText : EditText = findViewById(R.id.entryEditText)
        var infoButton : ImageButton = findViewById(R.id.infoButton)

        // setup the info button
        infoButton.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            var msg = SpannableString("Apache License Version 2.0\n" +
                    "http://www.apache.org/licenses/LICENSE-2.0")
            Linkify.addLinks(msg, Linkify.ALL)
            builder.setMessage(msg)
            builder.setTitle("HTextView License")
            builder.show().findViewById<TextView>(android.R.id.message)?.movementMethod = LinkMovementMethod.getInstance()

        }

        // stop the keyboard from automatically appearing
        // use the custom buttons to force valid input
        numberEditText.inputType = 0

        // get the input buttons
        var inputButtons = Array<Button?>(10, { null })
        inputButtons[0] = findViewById(R.id.inputButton0)
        inputButtons[1] = findViewById(R.id.inputButton1)
        inputButtons[2] = findViewById(R.id.inputButton2)
        inputButtons[3] = findViewById(R.id.inputButton3)
        inputButtons[4] = findViewById(R.id.inputButton4)
        inputButtons[5] = findViewById(R.id.inputButton5)
        inputButtons[6] = findViewById(R.id.inputButton6)
        inputButtons[7] = findViewById(R.id.inputButton7)
        inputButtons[8] = findViewById(R.id.inputButton8)
        inputButtons[9] = findViewById(R.id.inputButton9)
        for (button in inputButtons) {
            button?.setOnClickListener {
                numberEditText.text.append(button.text)
            }
        }

        // handle the delete/backspace button
        var deleteButton : Button = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener {
            if (numberEditText.text.isNotEmpty())
                numberEditText.text.delete(numberEditText.text.length-1,numberEditText.text.length)
        }

        // handle taps on the check button
        var checkButton : Button = findViewById(R.id.checkButton)
        checkButton.setOnClickListener {
            // grab the number from the text field
            var numberString = numberEditText.text.toString()

            // check for an empty string
            if (numberString.isEmpty()) {
                // empty string
                Toast.makeText(this, "Please enter a number!", Toast.LENGTH_SHORT).show()
            } else {
                // convert the string to an integer
                try {
                    var numberInt = numberString.toLong()

                    if (isPrime(numberInt)) {
                        // this number is prime!
                        resultTextView.animateText(numberInt.toString() + " is prime")
                    } else {
                        resultTextView.animateText(numberInt.toString() + " is not prime")
                    }
                    entryEditText.text.clear()
                } catch (e: Exception) {
                    Toast.makeText(this, "Too large. Please enter a smaller number.", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    /** Check if a given number is prime */
    fun isPrime(number: Long): Boolean {
        // find the maximum factor we should divide by to check for primeness
        var stopFactor : Long = Math.sqrt(number.toDouble()).toLong()

        // check each potential factor
        for (potentialFactor in 2..stopFactor) {
            if (number % potentialFactor == 0L) {
                // number was evenly divided by the potential factor
                // this number cannot be prime
                return false
            }
        }

        // if we reached this far, then the number must be prime
        return true
    }
}
