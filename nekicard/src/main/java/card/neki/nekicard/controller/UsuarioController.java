package card.neki.nekicard.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import card.neki.nekicard.Service.UsuarioService;
import card.neki.nekicard.dto.UsuarioDto;
import card.neki.nekicard.model.Usuario;
import card.neki.nekicard.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuarios", description = "Endpoints dos usuários")
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

  @Autowired
  UsuarioRepository usuarioRepository;

  @GetMapping
  @Operation(summary = "Busca todos os usuarios", description = "Busca todos os cards dos usuarios", tags = {
      "Usuarios" }, responses = {
          @ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<List<UsuarioDto>> buscarTodos() {
    return ResponseEntity.ok(usuarioService.buscarTodos());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Busca cards por usuário", description = "Busca o card do usuário selecionado pelo id", tags = {
      "Usuarios" }, responses = {
          @ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(usuarioService.buscarPorId(id));
  }

  // @Transactional
  @PostMapping
  @Operation(summary = "Adiciona novo usuário", description = "Adiciona um card de um novo usuário ", tags = {
      "Usuarios" }, responses = {
          @ApiResponse(description = "Created", responseCode = "201"),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<?> salvar(@Valid @RequestBody UsuarioDto usuarioDto, UriComponentsBuilder uriBuilder) {
    Usuario usuarioSalvo = usuarioService.salvar(usuarioDto);
    Long usuarioId = usuarioSalvo.getId();
    var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioId).toUri();
    return ResponseEntity.created(uri).body(usuarioSalvo);
  }

  @PostMapping("/{id}/foto")
  @Operation(summary = "Adiciona foto do usuário", description = "Adiciona uma foto no card de um novo usuário ", tags = {
      "Usuarios" }, responses = {
          @ApiResponse(description = "Created", responseCode = "201"),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<?> saveFoto(@RequestParam("foto") MultipartFile foto, @PathVariable Long id) throws IOException {
    byte[] data = foto.getBytes();
    usuarioService.saveFoto(id, data);
    return ResponseEntity.ok("Imagem salva com sucesso!");
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualiza o Usuario selecionado", description = "Atualiza o card do Usuario selecionado pelo id", tags = {
      "Usuarios" }, responses = {
          @ApiResponse(description = "Updated", responseCode = "200"),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<Usuario> atualizar(@Valid @RequestBody UsuarioDto usuarioDto, @PathVariable Long id) {
    return ResponseEntity.ok(usuarioService.atualizar(usuarioDto, id));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta um Usuario", description = "Deleta um card do Usuario selecionado pelo id", tags = {
      "Usuarios" }, responses = {
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<Usuario> remover(@PathVariable Long id) {
    usuarioService.remover(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
