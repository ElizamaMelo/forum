package br.com.eliza.forumalura.repository;

import br.com.eliza.forumalura.model.Curso;
import br.com.eliza.forumalura.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository <Curso, Long> {


    Curso findByNome(String nome);
}
