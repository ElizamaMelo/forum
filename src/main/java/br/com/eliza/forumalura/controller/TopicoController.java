package br.com.eliza.forumalura.controller;

import br.com.eliza.forumalura.controller.dto.response.TopicoDto;
import br.com.eliza.forumalura.controller.dto.request.TopicoForm;
import br.com.eliza.forumalura.model.Topico;
import br.com.eliza.forumalura.repository.CursoRepository;
import br.com.eliza.forumalura.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping (value = "/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> listar(String nomeCurso){
        if (nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        }
        else {
            List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }

    /*código 201(created) devemos devolver a URI do recurso que acabou de ser criado.
    ex.: URI endereco = new URI("http://localhost:8080/topicos");
    Para evitar passar o endereço completo da URI (pois pode mudar futuramente), utilizamos
    a classe URIBuilder, que já pega automaticamente o endereço do servidor
    e só precisa passar o final da URI.
    ex.: URI endereco = uriBuilder.path("/topicos").toUri();*/

    @PostMapping
    @Transactional
    public ResponseEntity <TopicoDto> cadastrar (@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder){
       Topico topico = topicoForm.converter(cursoRepository);
      topicoRepository.save(topico);
       URI uri = uriBuilder.path("topicos/{id}").buildAndExpand(topico.getId()).toUri();
       return ResponseEntity.created(uri).body(new TopicoDto(topico));

    }

    @GetMapping("/{id}")
    @Transactional
    public  ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }

        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarTopicoForm form){
       Topico topico = form.atualizar(id, topicoRepository);
       return ResponseEntity.ok(new TopicoDto(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover (@PathVariable Long id){
      topicoRepository.deleteById(id);
      return ResponseEntity.ok().build();

    }




}
