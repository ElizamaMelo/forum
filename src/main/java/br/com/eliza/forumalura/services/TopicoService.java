package br.com.eliza.forumalura.services;

import br.com.eliza.forumalura.model.Topico;
import br.com.eliza.forumalura.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    private TopicoRepository repository;

    @Autowired
    public TopicoService(TopicoRepository repository){
        this.repository = repository;

    }

    public void delete (Topico topico){
        repository.delete(topico);
    }

    public List<Topico> findAll(){
        return (List<Topico>) repository.findAll();
    }

    public Topico findById(Long id){
        return repository.findById(id).get();
    }

    public Topico update(Topico topico){
        Topico t = repository.findById(topico.getId()).get();
        t.setCurso(t.getCurso());
        t.setTitulo(t.getTitulo());
        t.setAutor(t.getAutor());
        return null;
    }


    public List<Topico> findByCurso_Nome(String nomeCurso) {
        return repository.findByCurso_Nome(nomeCurso);
    }
}

