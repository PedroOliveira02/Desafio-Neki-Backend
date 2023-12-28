package card.neki.nekicard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import card.neki.nekicard.dto.AdministradorDto;
import card.neki.nekicard.infra.exceptions.EmailCadastradoException;
import card.neki.nekicard.model.Administrador;
import card.neki.nekicard.repository.AdministradorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AdministradorService {

  @Autowired
  AdministradorRepository administradorRepository;

  public UserDetails salvar(AdministradorDto administradorDto) {
    emailExistenteAdmin(administradorDto.getEmail());
    String encryptedPassword = new BCryptPasswordEncoder().encode(administradorDto.getSenha());
    administradorDto.setSenha(encryptedPassword);
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
    adm.setId(administradorDto.getId());
    adm.setNome(administradorDto.getNome());
    adm.setEmail(administradorDto.getEmail());
    adm.setSenha(administradorDto.getSenha());
    return adm;
  }

  public void emailExistenteAdmin(String email) {
    UserDetails administrador = administradorRepository.findByEmail(email);
    if (administrador != null) {
      throw new EmailCadastradoException("Email já cadastrado");
    }
  }
}
