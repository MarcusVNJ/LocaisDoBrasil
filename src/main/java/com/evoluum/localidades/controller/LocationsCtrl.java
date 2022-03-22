package com.evoluum.localidades.controller;

import com.evoluum.localidades.dto.LocationResult;
import com.evoluum.localidades.helper.plugins.HttpResponseCsv;
import com.evoluum.localidades.service.LocationService;
import com.evoluum.localidades.service.LocationsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/api/v1/locations")
public class LocationsCtrl extends HttpResponseCsv {

    private final LocationService service;

    public LocationsCtrl(LocationsServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LocationResult>> getLocations() {
        return this.service.getLocations().isPresent() ? ResponseEntity.ok(this.service.getLocations().get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{nameCity}")
    public ResponseEntity<List<Integer>> getCity(@PathVariable("nameCity") @NonNull String nameCity) {
        Optional<List<Integer>> idCity = service.getIdCity(nameCity);

        return idCity.isPresent() && !idCity.get().isEmpty() ? ResponseEntity.ok(this.service.getIdCity(nameCity).get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/csv")
    public ResponseEntity<?> getLocationsCsv (HttpServletResponse response) {
        try {
            requestLocationCsv(response, this.service.getLocationsToString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
