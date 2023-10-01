package br.tads.ufpr.routes.controllers;

import br.tads.ufpr.routes.services.AddressService;
import com.google.maps.model.AutocompletePrediction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AutocompletePrediction>> testAutocomplete(@RequestParam String input) {
        return ResponseEntity.ok(addressService.autocompletePredictions(input));
    }
}
