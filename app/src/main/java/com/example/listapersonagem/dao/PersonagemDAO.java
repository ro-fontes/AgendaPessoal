package com.example.listapersonagem.dao;

import com.example.listapersonagem.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {
    //Cria as variaveis
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;

    //Salva o personagem
    public void salva(Personagem personagemSalvo) {
        personagemSalvo.setId((contadorDeId)); //seta um ID para todos os personagens salvos
        personagens.add(personagemSalvo);
        contadorDeId++; //Adiciona 1 na variavel contadora
    }

    //Edita o personagem escolhido
    public void edita(Personagem personagem) {
        Personagem personagemEscolhido = null;
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                personagemEscolhido = p;
            }
        }
        //se o personagem escolhido estiver vazio execulta o if
        if (personagemEscolhido != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    private Personagem buscaPersonagemID(Personagem personagem) {
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }

    //Busca todas as informacoes salvas
    public List<Personagem> todos() {
        return new ArrayList<>(personagens);
    }

    //Metodo para apagar itens do formulario
    public void remove(Personagem personagem) {
        Personagem personagemDevolvido = buscaPersonagemID(personagem);
        //Verificando se o personagemDevolvido nao esta vazio
        if (personagemDevolvido != null) {
            //removendo o objeto desejado
            personagens.remove(personagemDevolvido);
        }
    }
}
