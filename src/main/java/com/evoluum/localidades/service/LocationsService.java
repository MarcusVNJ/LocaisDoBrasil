package com.evoluum.localidades.service;

import com.evoluum.localidades.dto.LocationResult;
import com.evoluum.localidades.model.City;
import com.evoluum.localidades.model.State;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Collator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LocationsService {

    private static final String URLLOCATIONS = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
    private static final String URLCITIES = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/municipios";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private final RestTemplate restTemplate;

    public LocationsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Optional<List<LocationResult>> getLocations() {
        logger.info("Buscando estados...");
        return Optional.of(getStades().stream().flatMap(estado -> {
            return this.getCities(estado.getId()).stream().parallel().map(city -> LocationResult.createdLocationDto(city, estado));
        }).collect(Collectors.toList()));
    }

    @Cacheable("cities")
    public Optional<List<Integer>> getIdCity(String nameCity) {
        return Optional.of(getStades().stream().flatMap(estado -> {
            logger.info(String.format("Buscando cidades do estado de %s", estado.getSigla()));
            return this.getCities(estado.getId()).stream().filter(city -> this.compareNameCity(city.getNome(), nameCity));
        }).map(City::getId).collect(Collectors.toList()));
    }

    private List<State> getStades() {
        ResponseEntity<State[]> locationsResult = this.restTemplate.getForEntity(URLLOCATIONS, State[].class);

        return Arrays.asList(Objects.requireNonNull(locationsResult.getBody()));
    }

    private List<City> getCities(Integer idState) {
        ResponseEntity<ArrayNode> cities = Objects.requireNonNull(this.restTemplate.getForEntity(this.urlCity(idState), ArrayNode.class));
        return StreamSupport.stream(Objects.requireNonNull(cities.getBody()).spliterator(), true).map(city -> {
            return new City(city.get("id").asInt(), city.get("nome").asText(), city.findPath("mesorregiao").get("nome").asText());
        }).collect(Collectors.toList());
    }

    private String urlCity(Integer nameState) {
        return String.format(URLCITIES, nameState);
    }

    private Boolean compareNameCity(String nomaCity1, String nameCity2) {
        Collator collator = Collator.getInstance(new Locale("pt", "BR"));
        collator.setStrength(Collator.PRIMARY);

        return nameCity2 != null ? collator.compare(nomaCity1, nameCity2) == 0 : false;
    }

    public List<String> getLocationsToString() {
        return this.getLocations().get().stream().parallel().map(LocationResult::toStringCsv).collect(Collectors.toList());
    }

}
