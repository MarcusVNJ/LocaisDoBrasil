package com.evoluum.localidades.service;

import com.evoluum.localidades.dto.LocationResult;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LocationsServiceTest {

    @Autowired
    private LocationService service;

    @Test
    public void testeMetodoGetLocations() {
        Optional<List<LocationResult>> locations = this.service.getLocations();
        assertTrue(locations.isPresent());
        assertFalse(locations.get().isEmpty());
    }

    @Test
    public void testenomeCidadeNulo() {
        Optional<List<Integer>> cities = this.service.getIdCity(null);
        assertTrue(cities.isPresent());
        assertTrue(cities.get().isEmpty());
    }

    @Test
    public void testeParametroComAcento() {
        Optional<List<Integer>> cities = this.service.getIdCity("São Paulo");
        assertTrue(cities.isPresent());
        assertFalse(cities.get().isEmpty());
        assertEquals(3550308, cities.get().get(0));
    }

    @Test
    public void testeParametroMinusculo() {
        Optional<List<Integer>> cities = this.service.getIdCity("rio de janeiro");
        assertTrue(cities.isPresent());
        assertFalse(cities.get().isEmpty());
        assertEquals(3304557, cities.get().get(0));
    }

    @Test
    public void testeParametroMaiusculo() {
        Optional<List<Integer>> cities = this.service.getIdCity("RIO DE JANEIRO");
        assertTrue(cities.isPresent());
        assertFalse(cities.get().isEmpty());
        assertEquals(3304557, cities.get().get(0));
    }

    @Test
    public void testeMultiplosResultados() {
        Optional<List<Integer>> cities = this.service.getIdCity("Bonito");
        assertTrue(cities.isPresent());
        assertFalse(cities.get().isEmpty());
        assertEquals(Arrays.asList(1501600, 2602308, 2904050, 5002209), cities.get());
    }

    @Test
    public void testeMetodoGetCity() {
        Optional<List<Integer>> cities = this.service.getIdCity("Valparaíso de Goiás");
        assertTrue(cities.isPresent());
        assertFalse(cities.get().isEmpty());
        assertEquals(5221858, cities.get().get(0));
    }

    @Test
    public void testeMetodoResultadoParaCsv() {
        List<String> locationsToString = this.service.getLocationsToString();
        assertFalse(locationsToString.isEmpty());
        assertTrue(locationsToString.size() > 5569);
    }


}

