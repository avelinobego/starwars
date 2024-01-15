package br.com.americanas.digital.starwars.service;

import org.springframework.stereotype.Service;

import br.com.americanas.digital.starwars.repository.LocalPlanetasRepository;

@Service
public class DatabaseService {

    private final PlanetasService apiService;
    private final LocalPlanetasRepository repository;

    public DatabaseService(PlanetasService apiService, LocalPlanetasRepository repository) {
        this.apiService = apiService;
        this.repository = repository;
    }

}