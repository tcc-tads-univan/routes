package br.tads.ufpr.routes.services;

import br.tads.ufpr.routes.exception.AddressNotFound;
import br.tads.ufpr.routes.model.dto.RouteDirectionsResult;
import br.tads.ufpr.routes.model.dto.SaveUserAddressEventData;
import br.tads.ufpr.routes.model.dto.SearchAddressResponse;
import br.tads.ufpr.routes.model.entity.UserAddress;
import br.tads.ufpr.routes.model.mapper.StudentAddressMapper;
import br.tads.ufpr.routes.repository.UserAddressRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.PlaceAutocompleteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final static Logger log = LoggerFactory.getLogger(AddressService.class);
    private final GeoApiContext geoApiContext;
    private final UserAddressRepository repository;

    public AddressService(GeoApiContext geoApiContext, UserAddressRepository repository) {
        this.geoApiContext = geoApiContext;
        this.repository = repository;
    }

    public List<SearchAddressResponse> autocompletePredictions(String input) {
        PlaceAutocompleteRequest.SessionToken sessionToken = new PlaceAutocompleteRequest.SessionToken();

        try {
            log.info("autocompletePredictions({})", input);
            AutocompletePrediction[] response = PlacesApi
                    .placeAutocomplete(geoApiContext, input, sessionToken)
                    .types(PlaceAutocompleteType.ADDRESS)
                    .language("pt-BR")
                    .components(ComponentFilter.country("br"))
                    .await();

            log.info("autocompletePredictions({}) -> No. of results: {}", input, response.length);
            if (response.length == 0) {
                throw new AddressNotFound();
            }

            return Arrays.stream(response)
                    .map(r -> new SearchAddressResponse(r.placeId, r.description))
                    .toList();
        } catch (Exception e) {
            log.error("Error @autocompletePredictions({})", input, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ("Error searching for address: " + input), e);
        }
    }

    public void saveUserAddress(SaveUserAddressEventData event) {
        try {
            this.repository
                    .findByUserId(event.userId())
                    .ifPresent(userAddress -> this.repository.deleteById(userAddress.getId()));

            UserAddress entity = StudentAddressMapper.eventToEntity(event);
            this.repository.save(entity);
            log.info("Saved Student Address: {}", entity);
        } catch (Exception e) {
            log.error("saveStudentAddress({})", event, e);
        }
    }

    public RouteDirectionsResult findRouteDirections(Long driverId, List<Long> studentIds) {
        log.info("findRouteDirections({}, {})", driverId, studentIds);
        UserAddress garageAddress = this.repository.findByUserId(driverId).orElseThrow(AddressNotFound::new);
        String destination = garageAddress.getPlaceId();

        List<String> waypoints = this.repository.findAllByRelatedTo(driverId)
                .stream()
                .map(UserAddress::getPlaceId)
                .collect(Collectors.toList());

        studentIds.forEach(studentId -> {
            UserAddress studentAddress = this.repository.findByUserId(studentId).orElseThrow(AddressNotFound::new);
            waypoints.add(studentAddress.getPlaceId());
        });

        return new RouteDirectionsResult(waypoints, destination);
    }
}
