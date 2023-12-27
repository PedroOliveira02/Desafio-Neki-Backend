package card.neki.nekicard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import card.neki.nekicard.Service.UsuarioService;
import card.neki.nekicard.dto.UsuarioDto;
import card.neki.nekicard.model.Usuario;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

  @GetMapping
  public ResponseEntity<List<UsuarioDto>> buscarTodos() {
    return ResponseEntity.ok(usuarioService.buscarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(usuarioService.buscarPorId(id));
  }

  @PostMapping
  public ResponseEntity<Usuario> salvar(@Valid @RequestBody UsuarioDto usuarioDto, UriComponentsBuilder uriBuilder) {
    Usuario usuarioSalvo = usuarioService.salvar(usuarioDto);
    Long usuarioId = usuarioSalvo.getId();
    var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioId).toUri();
    return ResponseEntity.created(uri).body(usuarioSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Usuario> atualizar(@Valid @RequestBody UsuarioDto usuarioDto, @PathVariable Long id) {
    // Usuario usuario = usuarioService.atualizar(usuarioDto, id);
    return ResponseEntity.ok(usuarioService.atualizar(usuarioDto, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Usuario> remover(@PathVariable Long id) {
    usuarioService.remover(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
