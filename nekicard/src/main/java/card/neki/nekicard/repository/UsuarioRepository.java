package card.neki.nekicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import card.neki.nekicard.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Usuario findByEmail(String email);
}
