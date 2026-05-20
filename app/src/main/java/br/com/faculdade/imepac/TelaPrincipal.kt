package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth // IMPORTANTE ADICIONAR ESSA LINHA LÁ EM CIMA

class TelaPrincipal : AppCompatActivity() {

    private lateinit var btPerfil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)
        supportActionBar?.hide()

        btPerfil = findViewById(R.id.bt_perfil)

        btPerfil.setOnClickListener {
            val intent = Intent(this, TelaPerfil::class.java)
            startActivity(intent)
        }
    }

    // SEGREDO PARA A APRESENTAÇÃO: Quando sair da Tela Principal, desloga do Firebase
    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }
}