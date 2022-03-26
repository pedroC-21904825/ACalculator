package com.example.acalculator

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.acalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    private val operations = mutableListOf<String>()
    private val adapter = HistoryAdapter(::onOperationClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "O método onCreate foi invocado")
        super.onCreate(savedInstanceState)

        //Fora das Fichas - Retirar barra de notificações no modo horizontal - Ver flag descontinuada
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        //Fora das Fichas - Retirar barra de notificações no modo horizontal - Ver flag descontinuada

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        Log.i(TAG, "O método onDestroy foi invocado")
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()

        //Listeners Buttons
        binding.button00?.setOnClickListener { onClickSymbolNumber("00") }

        binding.button0.setOnClickListener { onClickSymbolNumber("0") }

        binding.button1.setOnClickListener { onClickSymbolNumber("1") }

        binding.button2.setOnClickListener { onClickSymbolNumber("2") }

        binding.button3.setOnClickListener { onClickSymbolNumber("3") }

        binding.button4.setOnClickListener { onClickSymbolNumber("4") }

        binding.button5.setOnClickListener { onClickSymbolNumber("5") }

        binding.button6.setOnClickListener { onClickSymbolNumber("6") }

        binding.button7.setOnClickListener { onClickSymbolNumber("7") }

        binding.button8.setOnClickListener { onClickSymbolNumber("8") }

        binding.button9.setOnClickListener { onClickSymbolNumber("9") }

        binding.buttonDiv.setOnClickListener { onClickSymbolOperation("/") }

        binding.buttonModulo.setOnClickListener { onClickSymbolOperation("%") }

        binding.buttonMulti.setOnClickListener {  onClickSymbolOperation("*")  }

        binding.buttonSub.setOnClickListener { onClickSymbolOperation("-") }

        binding.buttonAddition.setOnClickListener { onClickSymbolOperation("+") }

        binding.buttonDot.setOnClickListener {onClickSymbolOperation(".") }

        binding.buttonEquals.setOnClickListener {onClickEquals()}

        binding.buttonAllDelete.setOnClickListener {onClickSymbolDelete("C") }

        binding.buttonFirstDelete.setOnClickListener { onClickSymbolDelete("<") }
        //Listeners Buttons

        binding.rvHistoric?.layoutManager = LinearLayoutManager(this)
        binding.rvHistoric?.adapter = adapter

    } //onStart


    //Funções privadas
    private fun onClickSymbolNumber(symbol:String) {
        Log.i(TAG, "Cliquei no botão $symbol")
        if(binding.textVisor.text.last() == '0' && binding.textVisor.text.length < 2) {
            binding.textVisor.text = symbol
        } else {
            binding.textVisor.append(symbol)
        }
    }

    private fun onClickSymbolOperation(symbol:String) {
        Log.i(TAG, "Cliquei no botão $symbol")
        if(binding.textVisor.text.last() !in arrayOf('/','%','*','-','+','.')) {
            binding.textVisor.append(symbol)
        }
    }

    private fun onClickSymbolDelete(symbol:String) {
        Log.i(TAG, "Cliquei no botão $symbol")
        if (symbol == "C") {
            binding.textVisor.text = "0"
        } else if (symbol == "<"){
            if(binding.textVisor.text.length != 1 && binding.textVisor.text[0] != '0') {
                binding.textVisor.text = binding.textVisor.text.substring(0, binding.textVisor.text.length-1)
            } else {
                binding.textVisor.text = "0"
            }
        }
    }

    private fun onClickEquals() {
        Log.i(TAG, "Cliquei no botão =")
        val valorFinal : String
        val expression = ExpressionBuilder(
            binding.textVisor.text.toString()
        ).build()
        val expressao = expression.evaluate()
        valorFinal = if(expressao % 1 == 0.0) {
            expressao.toInt().toString()
        } else {
            expressao.toString()
        }
        operations.add(binding.textVisor.text.toString() + "=" + valorFinal)
        binding.textVisor.text = valorFinal
        adapter.updateItems(operations)
        Log.i(TAG, "O resultado da expressão é ${binding.textVisor.text}")
    }

    private fun onOperationClick(operation: String) {
        Toast.makeText(this, operation, Toast.LENGTH_LONG).show()
    }
    //Funções privadas

} // AppCompactActivity