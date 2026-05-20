package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPerfil : AppCompatActivity() {

    private lateinit var textNome: TextView
    private lateinit var textEmail: TextView
    private lateinit var btDeslogar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)

        
        supportActionBar?.hide()

        // 1. Inicializa todos os componentes da tela
        IniciarComponentes()

        // 2. Carrega o Nome e o E-mail reais do usuário logado
        carregarDadosUsuario()

        // 3. Configurando o clique para deslogar e voltar ao Login
        btDeslogar.setOnClickListener {

            // Garante que o usuário foi deslogado do Firebase ao clicar em sair
            FirebaseAuth.getInstance().signOut()

            // Criando a intenção de voltar para a MainActivity (que é o seu Login)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Muito importante: encerra a tela de perfil para não conseguir voltar no botão físico do celular
            finish()
        }
    }

    private fun carregarDadosUsuario() {
        // Pega o usuário conectado no momento
        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null) {
            // Define o e-mail direto da autenticação
            textEmail.text = usuarioAtual.email

            // Busca o nome salvao lá na coleção "Usuarios" do Firestore
            val db = FirebaseFirestore.getInstance()
            db.collection("Usuarios")
                .whereEqualTo("uid", usuarioAtual.uid)
                .get()
                .addOnSuccessListener { documentos ->
                    for (documento in documentos) {
                        val nomeDoBanco = documento.getString("nome")
                        textNome.text = nomeDoBanco
                    }
                }
                .addOnFailureListener {
                    textNome.text = "Erro ao carregar nome"
                }
        }
    }

    private fun IniciarComponentes() {
        btDeslogar = findViewById(R.id.bt_deslogar)

        // CORREÇÃO: Agora batendo certinho com o seu arquivo XML!
        textNome = findViewById(R.id.textNomeUser)
        textEmail = findViewById(R.id.textEmailUser)
    }
}