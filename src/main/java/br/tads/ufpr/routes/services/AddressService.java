package br.tads.ufpr.routes.services;

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

import java.util.List;

@Service
public class AddressService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    @Qualifier("getGeoApiContext")
    private GeoApiContext geoApiContext;

    public List<AutocompletePrediction> autocompletePredictions(String input) {
        PlaceAutocompleteRequest.SessionToken sessionToken = new PlaceAutocompleteRequest.SessionToken();

        try {
            LOGGER.info("Chamando a API do Google");
            AutocompletePrediction[] response = PlacesApi
                    .placeAutocomplete(geoApiContext, input, sessionToken)
                    .types(PlaceAutocompleteType.ADDRESS)
                    .language("pt-BR")
                    .components(ComponentFilter.country("br"))
                    .await();

            return List.of(response);
        } catch (Exception e) {
            LOGGER.error("Erro no consumo da API", e);
            throw new RuntimeException(e);
        }
    }
}
