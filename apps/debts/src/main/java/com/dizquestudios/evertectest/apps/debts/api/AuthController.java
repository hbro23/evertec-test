package com.dizquestudios.evertectest.apps.debts.api;

import org.springframework.http.MediaType;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.dizquestudios.evertectest.apps.debts.config.security.AuthAPI;

/**
 *
 * @author Sebastian
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
            public record AuthenticationRequest(@JsonProperty("username") String username,
            @JsonProperty("password") String password) {

    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
            public record AuthenticationResponse(@JsonProperty("jwt") String jwt) {

    }

    private final AuthenticationManager authenticationManager;
    private final AuthAPI authAPI;

    public AuthController(AuthenticationManager authenticationManager, AuthAPI platziUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.authAPI = platziUserDetailsService;
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Generate a valid token.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Invalid credentials.")
    })
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            UserDetails userDetails = authAPI.loadUserByUsername(request.username());
            String jwt = authAPI.generateToken(userDetails);

            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
