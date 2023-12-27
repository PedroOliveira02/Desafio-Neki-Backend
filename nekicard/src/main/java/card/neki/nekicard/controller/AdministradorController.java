package card.neki.nekicard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import card.neki.nekicard.Service.AdministradorService;
import card.neki.nekicard.dto.AdministradorDto;
import card.neki.nekicard.model.Administrador;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

  @Autowired
  AdministradorService administradorService;

  @PostMapping
  public ResponseEntity<Administrador> salvar(@Valid @RequestBody AdministradorDto administradorDto, UriComponentsBuilder uriBuilder) {
    Administrador administradorSalvo = administradorService.salvar(administradorDto);
    Long administradorId = administradorSalvo.getId();
    var uri = uriBuilder.path("/administradores/{id}").buildAndExpand(administradorId).toUri();
    return ResponseEntity.created(uri).body(administradorSalvo);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Administrador> remover(@PathVariable Long id) {
    administradorService.remover(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
