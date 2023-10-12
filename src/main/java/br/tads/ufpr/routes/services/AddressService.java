package br.tads.ufpr.routes.services;

import br.tads.ufpr.routes.exception.AddressNotFound;
import br.tads.ufpr.routes.model.dto.SearchAddressResponse;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.PlaceAutocompleteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AddressService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    @Qualifier("getGeoApiContext")
    private GeoApiContext geoApiContext;

    public List<SearchAddressResponse> autocompletePredictions(String input) {
        PlaceAutocompleteRequest.SessionToken sessionToken = new PlaceAutocompleteRequest.SessionToken();

        try {
            LOGGER.info("Buscando pelo endereço: {}", input);
            AutocompletePrediction[] response = PlacesApi
                    .placeAutocomplete(geoApiContext, input, sessionToken)
                    .types(PlaceAutocompleteType.ADDRESS)
                    .language("pt-BR")
                    .components(ComponentFilter.country("br"))
                    .await();

            LOGGER.info("Busca por: {} - Qtd. resultados: {}", input, response.length);
            if (response.length == 0) {
                throw new AddressNotFound();
            }

            return Arrays.stream(response)
                    .map(r -> new SearchAddressResponse(r.placeId, r.description))
                    .toList();
        } catch (Exception e) {
            LOGGER.error("Erro na busca pelo endereço: {}", input, e);
            throw new RuntimeException(e);
        }
    }
}
