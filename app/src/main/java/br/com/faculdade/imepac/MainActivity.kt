package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Comando para esconder a barra superior (Toolbar)
        supportActionBar?.hide()

        // Identificando o texto de "Crie uma conta"
        val textTelaCadastro = findViewById<TextView>(R.id.text_tela_cadastro)

        // Configurando o clique para ir para a tela de cadastro
        textTelaCadastro.setOnClickListener {
            // O erro que vai dar na linha abaixo é normal, pois ainda não criamos a FormCadastro
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
    }
}