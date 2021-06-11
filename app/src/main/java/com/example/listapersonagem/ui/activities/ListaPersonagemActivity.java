package com.example.listapersonagem.ui.activities;

//Importando as referencias

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity extends AppCompatActivity {

    //Declarando as variaveis
    public static final String TITLE_APPBAR = "Agenda Pessoal";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        //Trocando o nome do cabeçalho
        setTitle(TITLE_APPBAR);
        //Criando o botao float(FAB) e adicionando uma funcao a ela
        botaoFAB();
        configuraLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Limpa e cria os itens do formulario
        atualizaPersonagem();
    }

    private void atualizaPersonagem() {
        //Limpando a lista
        adapter.clear();
        //Remonta a lista com as informacoes de dao
        adapter.addAll(dao.todos());
    }

    private void remove(Personagem personagem) {
        //Remove o personagem do banco de dados
        dao.remove(personagem);
        //Remove o personagem da lista
        adapter.remove(personagem);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Menu para remover um item da lista ao segurar
        getMenuInflater().inflate(R.menu.activity_lista_personagens_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Apaga e cria um menu de dialogo para apagar o item selecionado
        configuraMenu(item);
        return super.onContextItemSelected(item);
    }

    private void configuraMenu(@NonNull MenuItem item) {
        //Pega o id do Menu
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_personagem_menu_remover) {
            //cria um menu de alerta ao clicar remover
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Contato")
                    .setMessage("Tem certeza que deseja remover?")
                    //ao clicar no sim, execulta a funcao para apagar o personagem
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            //remove o item que foi escolhido
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Nao", null)
                    .show();
        }
    }

    private void configuraLista() {
        //refenrecia da lista na activity
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        //Pegando os personagens e adicionando a lista
        listaDePersonagem(listaDePersonagens);
        //ao clicar em um item na lista
        configuraItemPerClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    //ao clicar em um item na lista
    private void configuraItemPerClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Sobreescrevendo o metodo para realizar a selecao do personagem
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Adicionando a posicao da lista aonde o player clicou
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(position);
                //Criando uma Intent para trocar para a activity ao clicar em um item da lista
                abreFormularioModoEditar(personagemEscolhido);
            }
        });
    }

    //Abre o formulario no modo edicao
    private void abreFormularioModoEditar(Personagem personagem) {
        //cria um Intent para ir ao formulario
        Intent vaiParaFormulario = new Intent(this, FormularioPersonagemActivity.class);
        //trazendo as informacoes pelo putExtra do personagem
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagem);
        //Trocando para a activity FormularioPersonagemActivity
        startActivity(vaiParaFormulario);
    }

    private void listaDePersonagem(ListView listaDePersonagens) {
        //cria um adapter com a referencia da lista
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }

    //Pegando e setando o botao Float(FAB)
    private void botaoFAB() {
        //Pegando o FloatingActionButton(FAB)
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);
        //Ao clicar no botao FAB, realiza uma funcao
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Troca para o activity FormularioPersonagemActivity
                abreFormularioSalvar();
            }
        });
    }

    private void abreFormularioSalvar() {
        //troca pra activity FormularioPersonagemActivity
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }
}


