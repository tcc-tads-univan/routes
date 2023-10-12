package br.tads.ufpr.routes.controllers;

import br.tads.ufpr.routes.model.dto.SearchAddressResponse;
import br.tads.ufpr.routes.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RoutesController {
    private final AddressService addressService;

    @Autowired
    public RoutesController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<SearchAddressResponse>> searchAddresses(@RequestParam String address) {
        return ResponseEntity.ok(addressService.autocompletePredictions(address));
    }
}
