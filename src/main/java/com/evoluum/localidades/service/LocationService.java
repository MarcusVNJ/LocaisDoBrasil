package com.evoluum.localidades.service;

import com.evoluum.localidades.dto.LocationResult;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Optional<List<LocationResult>> getLocations();

    Optional<List<Integer>> getIdCity(String nameCity);

    List<String> getLocationsToString();

}
