package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPerfil : AppCompatActivity() {

    // Variáveis da tela mapeadas como TextView (conforme as tags do seu XML)
    private lateinit var mailUser: TextView
    private lateinit var usuarioUser: TextView
    private lateinit var bt_sair: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)

        // Esconde a barra superior (Toolbar)
        supportActionBar?.hide()

        // Inicializa o banco de dados conforme o slide 3 e 10
        db = FirebaseFirestore.getInstance()

        // Inicializa as conexões com o arquivo XML
        IniciarComponentes()

        // Configura o clique do botão Sair exatamente como no slide 3, 9 e 10
        bt_sair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            // Cria a intenção de voltar para a tela de Login do seu projeto
            val intent = Intent(this@TelaPerfil, FormCadastro::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Método onStart solicitado pelo professor nos slides 5 e 10
    override fun onStart() {
        super.onStart()

        // Pega o e-mail do usuário conectado no Auth
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        mailUser.text = userEmail

        // Se o e-mail não for nulo, chama a função para buscar o nome correspondente
        if (userEmail != null) {
            buscarNomeDoEmail(userEmail)
        }
    }

    // Nossa função de busca no Firestore detalhada nos slides 6 e 10
    fun buscarNomeDoEmail(email: String) {
        val usuariosRef = db.collection("Usuarios")

        // Cria a consulta para encontrar o documento com o e-mail correspondente
        val query = usuariosRef.whereEqualTo("email", email)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // O documento foi encontrado, pegamos a primeira ocorrência
                    val documento = querySnapshot.documents[0]
                    val nome = documento.getString("nome")

                    if (nome != null) {
                        usuarioUser.text = nome
                    } else {
                        println("Nome não encontrado para o e-mail $email")
                    }
                } else {
                    println("Nenhum documento encontrado para o e-mail $email")
                }
            }
            .addOnFailureListener { e ->
                println("Erro ao buscar documento: ${e.message}")
            }
    }

    // Vincula o Kotlin com os IDs reais do seu XML
    private fun IniciarComponentes() {
        mailUser = findViewById(R.id.textEmailUser)
        usuarioUser = findViewById(R.id.textNomeUser)
        bt_sair = findViewById(R.id.bt_deslogar) // ID correspondente ao seu botão "SAIR"
    }
}