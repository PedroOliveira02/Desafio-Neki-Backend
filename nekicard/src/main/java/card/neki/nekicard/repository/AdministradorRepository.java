package card.neki.nekicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import card.neki.nekicard.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
  UserDetails findByEmail(String email);
}
