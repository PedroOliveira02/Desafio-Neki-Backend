package card.neki.nekicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import card.neki.nekicard.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
  Administrador findByEmail(String email);
}
