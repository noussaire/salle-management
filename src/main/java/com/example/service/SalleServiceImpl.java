package com.example.service;

import com.example.model.Salle;
import com.example.repository.SalleRepository;
import com.example.repository.SalleRepositoryImpl;
import com.example.util.PaginationResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class SalleServiceImpl implements SalleService {

    @Inject
    private SalleRepository salleRepository;

    public SalleServiceImpl() {
    }

    public SalleServiceImpl(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public void setSalleRepository(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.salleRepository = new SalleRepositoryImpl(entityManager);
    }

    @Override
    public List<Salle> getAvailableRooms(LocalDateTime debut, LocalDateTime fin) {
        return salleRepository.findAvailableRooms(debut, fin);
    }

    @Override
    public List<Salle> searchRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, int page, int size) {
        return salleRepository.searchRooms(nom, capaciteMin, debut, fin, page, size);
    }

    @Override
    public PaginationResult<Salle> searchRoomsPage(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin,
                                                  int page, int size) {
        List<Salle> items = searchRooms(nom, capaciteMin, debut, fin, page, size);
        long totalItems = countRooms(nom, capaciteMin, debut, fin);
        return new PaginationResult<>(items, page, size, totalItems);
    }

    @Override
    public long countRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin) {
        return salleRepository.countRooms(nom, capaciteMin, debut, fin);
    }
}
