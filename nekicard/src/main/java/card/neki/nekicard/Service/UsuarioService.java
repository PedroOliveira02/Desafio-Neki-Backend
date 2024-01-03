package card.neki.nekicard.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.neki.nekicard.dto.UsuarioDto;
import card.neki.nekicard.infra.exceptions.EmailCadastradoException;
import card.neki.nekicard.infra.exceptions.ResourceNotFoundException;
import card.neki.nekicard.model.Usuario;
import card.neki.nekicard.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {

  @Autowired
  UsuarioRepository usuarioRepository;

  public Usuario salvar(UsuarioDto usuarioDto) {
    emailExistente(usuarioDto.email());
    Usuario usuario = converterDtoParaModel(usuarioDto);
   
    return usuarioRepository.save(usuario);
  }

  public Usuario atualizar(UsuarioDto usuarioDto, Long id) {
    Usuario usuario = usuarioRepository.getReferenceById(id);
    Usuario usuarioExistente = usuarioRepository.findByEmail(usuarioDto.email());
    if (usuarioExistente != null && !usuarioExistente.getId().equals(id)) {
      throw new EmailCadastradoException("Email já cadastrado");
    }
    usuario.setNomeCompleto(usuarioDto.nomeCompleto());
    usuario.setNomeSocial(usuarioDto.nomeSocial());
    usuario.setDataNascimento(usuarioDto.dataNascimento());
    // usuario.setFoto(usuarioDto.foto());
    usuario.setEmail(usuarioDto.email());
    usuario.setTelefone(usuarioDto.telefone());
    usuario.setLinkedIn(usuarioDto.linkedIn());
    usuario.setGithub(usuarioDto.github());
    usuario.setFacebook(usuarioDto.facebook());
    usuario.setInstagram(usuarioDto.instagram());

    return usuarioRepository.save(usuario);
  }

  public void remover(Long id) {
    if (!usuarioRepository.existsById(id)) {
      throw new EntityNotFoundException();
    }
    usuarioRepository.deleteById(id);
  }

  public UsuarioDto buscarPorId(Long id) {
    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    return converterModelParaDto(usuario);
  }

  public List<UsuarioDto> buscarTodos() {
    return usuarioRepository.findAll()
        .stream()
        .map(u -> converterModelParaDto(u))
        .collect(Collectors.toList());
  }

  public Usuario converterDtoParaModel(UsuarioDto usuarioDto) {
    Usuario usuario = new Usuario();
    usuario.setNomeCompleto(usuarioDto.nomeCompleto());
    usuario.setNomeSocial(usuarioDto.nomeSocial());
    usuario.setDataNascimento(usuarioDto.dataNascimento());
    usuario.setEmail(usuarioDto.email());
    usuario.setTelefone(usuarioDto.telefone());
    usuario.setLinkedIn(usuarioDto.linkedIn());
    usuario.setGithub(usuarioDto.github());
    usuario.setFacebook(usuarioDto.facebook());
    usuario.setInstagram(usuarioDto.instagram());
    return usuario;
  }

  public UsuarioDto converterModelParaDto(Usuario usuario) {
    return new UsuarioDto(
        usuario.getId(),
        usuario.getNomeCompleto(),
        usuario.getNomeSocial(),
        usuario.getDataNascimento(),
        usuario.getFoto(),
        usuario.getEmail(),
        usuario.getTelefone(),
        usuario.getLinkedIn(),
        usuario.getGithub(),
        usuario.getFacebook(),
        usuario.getInstagram());
  }

  public void emailExistente(String email) {
    Usuario usuario = usuarioRepository.findByEmail(email);
    if (usuario != null) {
      throw new EmailCadastradoException("Email já cadastrado");
    }
  }

  public Usuario findById(Long id) {
    return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
  }

  public Usuario saveFoto(Long id, byte[] foto) {
    Usuario usuarioSalvo = findById(id);
    usuarioSalvo.setFoto(foto);
    return usuarioRepository.save(usuarioSalvo);
}


}