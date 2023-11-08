package br.tads.ufpr.routes.controllers;

import br.tads.ufpr.routes.model.dto.RouteDirectionsResult;
import br.tads.ufpr.routes.model.dto.SearchAddressResponse;
import br.tads.ufpr.routes.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RoutesController {
    private final AddressService addressService;

    public RoutesController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<SearchAddressResponse>> searchAddresses(@RequestParam String address) {
        return ResponseEntity.ok(this.addressService.autocompletePredictions(address));
    }

    @GetMapping("directions")
    public ResponseEntity<RouteDirectionsResult> findRouteDirections(@RequestParam Long driverId,
                                                                     @RequestParam Long studentId) {
        return ResponseEntity.ok(this.addressService.findRouteDirections(driverId, studentId));
    }
}
