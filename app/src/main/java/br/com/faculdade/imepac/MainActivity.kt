package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var bt_entrada: Button
    private lateinit var textTelaCadastro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        IniciarComponentes()

        // GARANTIA PARA O PROFESSOR: Sempre que abrir esta tela, desloga qualquer um que estiver ativo
        FirebaseAuth.getInstance().signOut()

        // Configura o clique no texto para ir para a tela de cadastro
        textTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        // Configura o clique no botão de entrar
        bt_entrada.setOnClickListener { view ->
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT).show()
            } else if (senha.length < 6) {
                Snackbar.make(view, "A senha deve ter pelo menos 6 caracteres!", Snackbar.LENGTH_SHORT).show()
            } else {
                AutenticarUsuario(view, email, senha)
            }
        }
    }

    private fun AutenticarUsuario(view: View, email: String, senha: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    irParaTelaPrincipal()
                } else {
                    Snackbar.make(view, "Erro ao entrar: Verifique suas credenciais.", Snackbar.LENGTH_LONG).show()
                }
            }
    }

    // O BLOCO ONSTART FOI REMOVIDO DAQUI PARA NUNCA MAIS PULAR ESTA TELA

    private fun irParaTelaPrincipal() {
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    private fun IniciarComponentes() {
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        bt_entrada = findViewById(R.id.bt_entrada)
        textTelaCadastro = findViewById(R.id.text_tela_cadastro)
    }
}