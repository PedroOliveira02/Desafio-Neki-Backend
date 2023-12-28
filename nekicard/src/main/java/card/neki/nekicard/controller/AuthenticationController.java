package card.neki.nekicard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import card.neki.nekicard.dto.AuthenticationDto;
import card.neki.nekicard.dto.LoginResponseDto;
import card.neki.nekicard.infra.exceptions.LoginBadRequestException;
import card.neki.nekicard.infra.security.TokenService;
import card.neki.nekicard.model.Administrador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoint de autenticação")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  @Operation(summary = "Login do administrador", description = "Faz o login para autenticar o administrador para ter acesso aos outros endpoints", tags = {
      "Authentication" }, responses = {
          @ApiResponse(description = "Success", responseCode = "200"),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDto data) {
    try {
      var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
      var auth = this.authenticationManager.authenticate(usernamePassword);
      var admindetails = (Administrador) auth.getPrincipal();
      var id = admindetails.getId();
      var token = tokenService.generateToken(admindetails);
      return ResponseEntity.ok(new LoginResponseDto(id, token));
    } catch (AuthenticationException e) {
      throw new LoginBadRequestException("Credenciais inválidas. Verifique seu email e senha!");
    }

  }
}
