package card.neki.nekicard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import card.neki.nekicard.repository.AdministradorRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return administradorRepository.findByEmail(username);
    }

}
