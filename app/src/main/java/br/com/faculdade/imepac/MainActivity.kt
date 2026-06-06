package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        // Desloga qualquer sessão anterior para garantir que o professor veja o fluxo completo
        FirebaseAuth.getInstance().signOut()

        textTelaCadastro.setOnClickListener {
            startActivity(Intent(this, FormCadastro::class.java))
        }

        bt_entrada.setOnClickListener { view ->
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            when {
                email.isEmpty() || senha.isEmpty() ->
                    Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT).show()
                senha.length < 6 ->
                    Snackbar.make(view, "A senha deve ter pelo menos 6 caracteres!", Snackbar.LENGTH_SHORT).show()
                else ->
                    AutenticarUsuario(view, email, senha)
            }
        }
    }

    private fun AutenticarUsuario(view: View, email: String, senha: String) {
        // Desabilita o botão durante o processo para evitar cliques duplos
        bt_entrada.isEnabled = false

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                bt_entrada.isEnabled = true  // Reabilita o botão
                if (task.isSuccessful) {
                    Log.d("LOGIN", "Login bem-sucedido!")
                    irParaDashboard()
                } else {
                    val erro = task.exception?.message ?: "Erro desconhecido"
                    Log.e("LOGIN", "Falha: $erro")
                    Snackbar.make(view, "Erro: $erro", Snackbar.LENGTH_LONG).show()
                }
            }
    }

    private fun irParaDashboard() {
        // ✅ CORRIGIDO: agora vai para TelaDashboard, não TelaPrincipal
        startActivity(Intent(this, TelaDashboard::class.java))
        finish()
    }

    private fun IniciarComponentes() {
        edit_email       = findViewById(R.id.edit_email)
        edit_senha       = findViewById(R.id.edit_senha)
        bt_entrada       = findViewById(R.id.bt_entrada)
        textTelaCadastro = findViewById(R.id.text_tela_cadastro)
    }
}