package card.neki.nekicard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.neki.nekicard.dto.AdministradorDto;
import card.neki.nekicard.model.Administrador;
import card.neki.nekicard.model.Usuario;
import card.neki.nekicard.repository.AdministradorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AdministradorService {
  
  @Autowired
  AdministradorRepository administradorRepository;

  public Administrador salvar(AdministradorDto administradorDto) {
    emailExistenteAdmin(administradorDto.email());
    Administrador administrador = converterDtoparaModel(administradorDto);
    return administradorRepository.save(administrador);
  }

  public void remover(Long id) {
    if (!administradorRepository.existsById(id)) {
      throw new EntityNotFoundException();
    }
    administradorRepository.deleteById(id);
  }
  public Administrador converterDtoparaModel(AdministradorDto administradorDto) {
    Administrador adm = new Administrador();
    adm.setId(administradorDto.id());
    adm.setNome(administradorDto.nome());
    adm.setEmail(administradorDto.email());
    adm.setSenha(administradorDto.senha());
    return adm;
  }

  public void emailExistenteAdmin(String email) {
    Administrador administrador = administradorRepository.findByEmail(email);
    if (administrador!= null) {
      throw new IllegalArgumentException("Email já cadastrado");
    }
  }
}
