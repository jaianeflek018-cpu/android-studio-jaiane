package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastro : AppCompatActivity() {

    private lateinit var edit_nome: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var btnCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)
        supportActionBar?.hide()

        // 1. Inicializa os componentes
        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email_cadastro)
        edit_senha = findViewById(R.id.edit_senha_cadastro)
        btnCadastrar = findViewById(R.id.bt_cadastrar)

        // 2. Configura o clique do botão
        btnCadastrar.setOnClickListener { view ->
            val nome = edit_nome.text.toString().trim()
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show()
            } else {
                cadastrarUsuario(view)
            }
        }
    }

    private fun cadastrarUsuario(view: View) {
        val email = edit_email.text.toString().trim()
        val senha = edit_senha.text.toString().trim()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    salvarDadosUsuario()
                    Snackbar.make(view, "Sucesso ao cadastrar!", Snackbar.LENGTH_LONG).show()
                    irParaMainActivity() // Agora ela existe aqui e o erro vai sumir!
                } else {
                    val erro = task.exception?.message
                    Snackbar.make(view, "Erro: $erro", Snackbar.LENGTH_LONG).show()
                }
            }
    }

    // AQUI ESTÁ A CORREÇÃO: Mudamos o nome da função para bater com o que você chamou lá em cima
    private fun irParaMainActivity() {
        val intent = Intent(this, MainActivity::class.java) // Aponta corretamente para a sua MainActivity
        startActivity(intent)
        finish()
    }

    private fun salvarDadosUsuario() {
        val db = FirebaseFirestore.getInstance()
        val nome = edit_nome.text.toString().trim()
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email

        if (usuarioID != null && email != null) {
            val usuarios = hashMapOf(
                "nome" to nome,
                "email" to email,
                "uid" to usuarioID
            )

            db.collection("Usuarios")
                .add(usuarios)
                .addOnSuccessListener {
                    println("Sucesso ao salvar no Firestore")
                }
                .addOnFailureListener { e ->
                    println("Erro ao salvar: $e")
                }
        }
    }
}