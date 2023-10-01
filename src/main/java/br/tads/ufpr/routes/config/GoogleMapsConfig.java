package br.tads.ufpr.routes.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class GoogleMapsConfig {
    @Value("${routes.keys.google-maps}")
    private String apiKey;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GeoApiContext getGeoApiContext() {
        return new GeoApiContext.Builder().apiKey(this.apiKey).maxRetries(1).build();
    }
}
