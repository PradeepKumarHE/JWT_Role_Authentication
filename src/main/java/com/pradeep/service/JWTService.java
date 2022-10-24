package com.pradeep.service;

import com.pradeep.entity.JWTRequest;
import com.pradeep.entity.JWTResponse;
import com.pradeep.entity.User;
import com.pradeep.repository.UserRepository;
import com.pradeep.util.JWTUtil;
import net.bytebuddy.dynamic.TypeResolutionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JWTService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JWTResponse createJWTToken(JWTRequest jwtRequest) throws Exception {
        String username = jwtRequest.getUsername();
        String userpassword = jwtRequest.getUserpassword();
        authenticate(username, userpassword);

        UserDetails userDetails = loadUserByUsername(username);
        String generatedJwtToken = jwtUtil.generateJwtToken(userDetails);

        User user = userRepository.findById(username).get();
        return new JWTResponse(user, generatedJwtToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findById(username);

        if (optionalUser.isPresent()) {
            User myAppUser = optionalUser.get();
            //return new org.springframework.security.core.userdetails.User(myAppUser.getUsername(),myAppUser.getPassword());
            return new org.springframework.security.core.userdetails.User(
                    myAppUser.getUsername(),
                    myAppUser.getPassword(),
                    getAuthorities(myAppUser)
            );
        } else {
            throw new UsernameNotFoundException("Username is not valid");
        }
    }

    private Set getAuthorities(User user) {
        Set authorities = new HashSet();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
        return authorities;
    }

    private void authenticate(String username, String userpassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userpassword));
        } catch (DisabledException de) {
            throw new Exception("User is disabled");
        } catch (BadCredentialsException badCredentialsException) {
            throw new Exception("Bad Credentials Exception");
        }


    }
}
