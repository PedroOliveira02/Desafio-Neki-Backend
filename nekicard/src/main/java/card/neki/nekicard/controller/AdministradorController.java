package card.neki.nekicard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/administradores")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Administradores", description = "Endpoints dos Administradores")
public class AdministradorController {

  @Autowired
  AdministradorService administradorService;

  @PostMapping
  @Operation(summary = "Adiciona novo Administrador", description = "Adiciona novo Administrador da Nekicard", tags = {
      "Administradores" }, responses = {
          @ApiResponse(description = "Created", responseCode = "201"),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<?> salvar(@Valid @RequestBody AdministradorDto administradorDto,
      UriComponentsBuilder uriBuilder) {
    AdministradorDto newAdminDto = new AdministradorDto();
    newAdminDto.setNome(administradorDto.getNome());
    newAdminDto.setEmail(administradorDto.getEmail());
    newAdminDto.setSenha(administradorDto.getSenha());
    UserDetails administradorSalvo = administradorService.salvar(newAdminDto);
    Long administradorId = newAdminDto.getId();
    var uri = uriBuilder.path("/administradores/{id}").buildAndExpand(administradorId).toUri();
    return ResponseEntity.created(uri).body(administradorSalvo);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta um Administrador", description = "Deleta um Administrador selecionado pelo id", tags = {
      "Administradores" }, responses = {
          @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
          @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<Administrador> remover(@PathVariable Long id) {
    administradorService.remover(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
