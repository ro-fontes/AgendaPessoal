package com.example.listapersonagem.ui.activities;

//Aula Gravada 26/03/21 00:36:22.

//Importando as referencias

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

    //Criando variaveis staticas(Constantes)
    private static final String TITLE_APPBAR_EDITA_PERSONAGEM = "Editar Contato";
    private static final String TITLE_APPBAR_NOVO_PERSONAGEM = "Novo Contato";

    //criando as variaveis EditText
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private EditText campoRg;
    private EditText campoCep;
    private EditText campoGenero;

    private final PersonagemDAO dao = new PersonagemDAO(); //criando um objeto dao(Banco de daodos do personagem) com referencia a PersonagemDAO.java
    private Personagem personagem; // Criando um objeto de tipo Personagem

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Menu para savar ao clicar no check
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        //verificando se o botao que foi clicado eh o mesmo que foi escolhido
        if (itemId == R.id.activity_formulario_personagem_menu_salvar) {
            //Puxando a funcao de salvar o cadastro
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        //iniciando os campos de EditText
        inicializacaoCampos();
        //Configura o botao Salvar
        configuraBotao();
        carregaPersonagem();
    }

    private void carregaPersonagem() {
        //pegando o intent salvo e salvando ele na variavel dados
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            //Trocando o nome do titulo
            setTitle(TITLE_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            //setar o que esta salvo nos devidos EditText
            preencheCampos();
        } else {
            //Trocando o nome do titulo
            setTitle(TITLE_APPBAR_NOVO_PERSONAGEM);
            //cria um personagem caso nao exista
            personagem = new Personagem();
        }
    }

    private void preencheCampos() {
        //preenche os campos com as variaveis salvas
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
        campoTelefone.setText(personagem.getTelefone());
        campoEndereco.setText(personagem.getEndereco());
        campoRg.setText(personagem.getRg());
        campoCep.setText(personagem.getCep());
        campoGenero.setText(personagem.getGenero());
    }

    private void configuraBotao() {
        //Adicionando uma funcao ao botao de salvar e Pegando o botao
        Button botaoSalvar = findViewById(R.id.button_salvar);
        //adiciona um "Ouvinte" ao botaoSalvar
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Salva ou edita o formulario
                finalizaFormulario();
            }
        });
    }

    private void finalizaFormulario() {
        preenchePersonagem();
        //Verifica se o id for valido
        if (personagem.IdValido()) {
            //traz as informacoes para editar
            dao.edita(personagem);
        } else {
            //Salvando a informacao do personagem no dao
            dao.salva(personagem);
        }
        //Finalizando o Activity
        finish();
    }

    private void inicializacaoCampos() {
        //Pegando as referencias do EditText no activity_formulario_personagem e adicionando a variavel
        campoNome = findViewById(R.id.editText_nome);
        campoAltura = findViewById(R.id.editText_altura);
        campoNascimento = findViewById(R.id.editText_nascimento);
        campoTelefone = findViewById(R.id.editText_telefone);
        campoEndereco = findViewById(R.id.editText_endereco);
        campoRg = findViewById(R.id.editText_rg);
        campoCep = findViewById(R.id.editText_cep);
        campoGenero = findViewById(R.id.editText_genero);

        //Adicionando as mascaras de cep no texto
        SimpleMaskFormatter smfCep = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtwCep = new MaskTextWatcher(campoCep, smfCep);
        campoCep.addTextChangedListener(mtwCep);

        //Adicionando as mascaras de RG no texto
        SimpleMaskFormatter smfRG = new SimpleMaskFormatter("NN.NNN.NNN-N");
        MaskTextWatcher mtwRG = new MaskTextWatcher(campoRg, smfRG);
        campoRg.addTextChangedListener(mtwRG);

        //Adicionando as mascaras de telefone no texto
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(campoTelefone, smfTelefone);
        campoTelefone.addTextChangedListener(mtwTelefone);

        //adicionando as mascaras de altura no texto
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        //adicionando as mascaras de Nascimento no texto
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);
    }

    private void preenchePersonagem() {
        //Setando o nome dos campos com as variaveis internas
        String nome = campoNome.getText().toString();
        String altura = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String endereco = campoEndereco.getText().toString();
        String rg = campoRg.getText().toString();
        String cep = campoCep.getText().toString();
        String genero = campoGenero.getText().toString();

        //refenciando as strings com os respectivos itens
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
        personagem.setTelefone(telefone);
        personagem.setEndereco(endereco);
        personagem.setRg(rg);
        personagem.setCep(cep);
        personagem.setGenero(genero);
    }
}