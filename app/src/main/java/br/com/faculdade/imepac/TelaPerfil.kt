package br.com.faculdade.imepac // Mantenha o seu pacote original aqui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TelaPerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)

        // Esconde a barra superior
        supportActionBar?.hide()

        // 1. Encontrando o botão de sair pelo ID que demos no XML (bt_deslogar)
        val btDeslogar = findViewById<Button>(R.id.bt_deslogar)

        // 2. Configurando o clique para voltar ao Login
        btDeslogar.setOnClickListener {

            // Criando a intenção de voltar para a MainActivity (que é o seu Login)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Muito importante: usamos o finish() para que, ao sair,
            // a tela de perfil seja encerrada e o usuário não consiga
            // voltar para ela apertando o botão "voltar" do celular sem logar de novo.
            finish()
        }
    }
}