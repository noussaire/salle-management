package com.example;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.service.SalleService;
import com.example.service.SalleServiceImpl;
import com.example.util.PaginationResult;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SallePU");
        EntityManager em = emf.createEntityManager();

        try {
            seedData(em);

            SalleService salleService = new SalleServiceImpl();
            ((SalleServiceImpl) salleService).setEntityManager(em);

            LocalDateTime debut = LocalDateTime.of(2024, 6, 1, 9, 0);
            LocalDateTime fin = LocalDateTime.of(2024, 6, 1, 11, 0);

            List<Salle> sallesDisponibles = salleService.getAvailableRooms(debut, fin);
            System.out.println("Salles disponibles entre " + debut + " et " + fin + ":");
            for (Salle salle : sallesDisponibles) {
                System.out.println("- " + salle.getNom() + " (Capacite: " + salle.getCapacite() + ")");
            }

            PaginationResult<Salle> page = salleService.searchRoomsPage(null, 15, debut, fin, 1, 2);
            System.out.println();
            System.out.println("Recherche paginee: page " + page.getCurrentPage() + "/" + page.getTotalPages()
                    + ", total=" + page.getTotalItems());
            for (Salle salle : page.getItems()) {
                System.out.println("- " + salle.getNom() + " (Capacite: " + salle.getCapacite() + ")");
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void seedData(EntityManager em) {
        em.getTransaction().begin();

        Salle salleA = new Salle("Salle A", 20);
        Salle salleB = new Salle("Salle B", 40);
        Salle salleC = new Salle("Salle C", 12);
        Salle salleD = new Salle("Salle D", 30);

        em.persist(salleA);
        em.persist(salleB);
        em.persist(salleC);
        em.persist(salleD);

        em.persist(new Reservation(salleA,
                LocalDateTime.of(2024, 6, 1, 8, 30),
                LocalDateTime.of(2024, 6, 1, 10, 0)));
        em.persist(new Reservation(salleC,
                LocalDateTime.of(2024, 6, 1, 13, 0),
                LocalDateTime.of(2024, 6, 1, 14, 0)));

        em.getTransaction().commit();
    }
}
