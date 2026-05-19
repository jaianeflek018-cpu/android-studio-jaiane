package br.com.faculdade.imepac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TelaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)

        // Esconde a barra de suporte (Toolbar)
        supportActionBar?.hide()
    }
}