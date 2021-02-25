package com.dizquestudios.evertectest.apps.debts.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dizquestudios.evertectest.core.debts.domain.Client;

/**
 *
 * @author sebas
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAll() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
