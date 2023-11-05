package br.tads.ufpr.routes.controllers;

import br.tads.ufpr.routes.model.dto.SearchAddressResponse;
import br.tads.ufpr.routes.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RoutesController {
    private final AddressService service;

    @Autowired
    public RoutesController(AddressService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SearchAddressResponse>> searchAddresses(@RequestParam String address) {
        return ResponseEntity.ok(this.service.autocompletePredictions(address));
    }

    @GetMapping("preview/{driverId}")
    public ResponseEntity<List<String>> calculateRoutePreview(@PathVariable Long driverId, @RequestParam String placeId) {
        return ResponseEntity.ok(this.service.calculateRoutesPreview(placeId, driverId));
    }
}
