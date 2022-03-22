package com.evoluum.localidades.controller;

import com.evoluum.localidades.helper.plugins.HttpResponseCsv;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertTrue;


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)

class LocationsCtrlTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void retornoListaEstadosJson() throws Exception {
        URI uri = new URI("/public/api/v1/locations");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testeDownloadArquivoCsv() throws Exception {
        URI uri = new URI("/public/api/v1/locations/csv");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.parseMediaType("text/csv"))).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertTrue(result.getResponse().getContentAsByteArray().length > 350000);
    }

    @Test
    public void testeRetornoIdUnicoCidade() throws Exception {
        URI uri = new URI("/public/api/v1/locations/Brasília");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[5300108]"));
    }

    @Test
    public void testeRetornoIdsCidades() throws Exception {
        URI uri = new URI("/public/api/v1/locations/Bonito");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[1501600,2602308,2904050,5002209]"));
    }

    @Test
    public void testeRetornoIdsCidades2() throws Exception {
        URI uri = new URI("/public/api/v1/locations/Jussara");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[2918506,4113007,5212204]"));
    }

    @Test
    public void testeParemetroSemAcentuacao() throws Exception {
        URI uri = new URI("/public/api/v1/locations/Brasilia");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[5300108]"));
    }

    @Test
    public void testeParemetroInvalido() throws Exception {
        URI uri = new URI("/public/api/v1/locations/_*_");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testeParemetroMisturaMaiusculoMinusculo() throws Exception {
        URI uri = new URI("/public/api/v1/locations/aleGRETe");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[4300406]"));
    }

}

