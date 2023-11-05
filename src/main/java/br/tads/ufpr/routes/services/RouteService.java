package br.tads.ufpr.routes.services;

import br.tads.ufpr.routes.exception.AddressNotFound;
import br.tads.ufpr.routes.exception.ExternalApiException;
import br.tads.ufpr.routes.model.entity.UserAddress;
import br.tads.ufpr.routes.repository.UserAddressRepository;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private static final Logger log = LoggerFactory.getLogger(RouteService.class);
    private final UserAddressRepository repository;
    private final GeoApiContext geoApiContext;

    public RouteService(UserAddressRepository repository, GeoApiContext geoApiContext) {
        this.repository = repository;
        this.geoApiContext = geoApiContext;
    }

    public DirectionsResult calculateDirections(Long driverId, String campusPlaceId, String waypointPlaceId) {
        try {
            UserAddress garageAddress = this.repository.findByUserId(driverId).orElseThrow(AddressNotFound::new);
            String garagePlaceId = garageAddress.getPlaceId();

            List<String> waypoints = this.repository.findAllByRelatedTo(driverId)
                    .stream()
                    .map(UserAddress::getPlaceId)
                    .collect(Collectors.toList());
            waypoints.add(waypointPlaceId);

            return DirectionsApi.newRequest(geoApiContext)
                    .originPlaceId(campusPlaceId)
                    .destinationPlaceId(garagePlaceId)
                    .waypointsFromPlaceIds(waypoints.toArray(String[]::new))
                    .optimizeWaypoints(true)
                    .mode(TravelMode.DRIVING)
                    .alternatives(false)
                    .await();
        } catch (IOException | InterruptedException | ApiException e) {
            log.error("calculateDirections({}, {}, {})", driverId, campusPlaceId, waypointPlaceId, e);
            throw new ExternalApiException(e);
        }
    }
}
