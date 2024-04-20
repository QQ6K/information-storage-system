package ru.task.iss.security.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.task.iss.exceptions.AppError;
import ru.task.iss.security.dtos.AuthDto;
import ru.task.iss.security.dtos.JwtRequest;
import ru.task.iss.security.utils.JwtTokenUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws JsonProcessingException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        ObjectMapper objectMapper = new ObjectMapper();
        AuthDto authDto = new AuthDto();
        authDto.setLogin(userDetails.getUsername());
        List<String> roles = new ArrayList<>();
        Collection<GrantedAuthority> ga = new ArrayList<>(userDetails.getAuthorities());
        for (GrantedAuthority role : ga) {
            roles.add(role.toString());
        }
        authDto.setRoles(roles);
        String jsonAuth = objectMapper.writeValueAsString(authDto);

        return ResponseEntity.ok()
                //.header(HttpHeaders.SET_COOKIE, "Bearer " + token.toString())
                .header(HttpHeaders.SET_COOKIE, token)
                .body(jsonAuth);
    }

}
