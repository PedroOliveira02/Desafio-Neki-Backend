package card.neki.nekicard.model;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import card.neki.nekicard.repository.AdministradorRepository;

@Component
public class AdministradorDataLoader implements ApplicationRunner {

  private final AdministradorRepository administradorRepository;

  public AdministradorDataLoader(AdministradorRepository administradorRepository) {
    this.administradorRepository = administradorRepository;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (administradorRepository.count() == 0) {
      Administrador administrador = new Administrador();
      administrador.setNome("Pedro");
      administrador.setEmail("pedro@neki.com.br");
      administrador.setSenha("12345");
      administradorRepository.save(administrador);
    }
  }
  
}
