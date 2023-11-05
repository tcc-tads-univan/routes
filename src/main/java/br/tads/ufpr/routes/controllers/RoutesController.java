package br.tads.ufpr.routes.controllers;

import br.tads.ufpr.routes.model.dto.SearchAddressResponse;
import br.tads.ufpr.routes.services.AddressService;
import br.tads.ufpr.routes.services.RouteService;
import com.google.maps.model.DirectionsResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RoutesController {
    private final AddressService addressService;
    private final RouteService routeService;

    public RoutesController(AddressService addressService, RouteService routeService) {
        this.addressService = addressService;
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<List<SearchAddressResponse>> searchAddresses(@RequestParam String address) {
        return ResponseEntity.ok(this.addressService.autocompletePredictions(address));
    }

    @GetMapping("calculate/{driverId}")
    public ResponseEntity<DirectionsResult> calculateDirections(@PathVariable Long driverId, @RequestParam String waypoint, @RequestParam String campus) {
        return ResponseEntity.ok(this.routeService.calculateDirections(driverId, campus, waypoint));
    }
}
