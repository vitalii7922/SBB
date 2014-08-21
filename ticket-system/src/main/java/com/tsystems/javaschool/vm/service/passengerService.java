package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.PassengerDAO;
import com.tsystems.javaschool.vm.dao.TicketDAO;
import com.tsystems.javaschool.vm.domain.*;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class PassengerService {
    TicketDAO ticketDAO;
    PassengerDAO passengerDAO;

    public PassengerService(PassengerDAO passengerDAO, TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
        this.passengerDAO = passengerDAO;
    }

    public Passenger addPassenger(String firstName, String lastName, Date birthDate) {
        Passenger passenger = new Passenger(firstName, lastName, birthDate);
        EntityTransaction trx = passengerDAO.getTransaction();
        try {
            trx.begin();
            passengerDAO.create(passenger);
            trx.commit();
        } finally {
            if (trx.isActive()) {
                trx.rollback();
            }
        }
        return passenger;
    }

    /**
     * Метод, возвращащий кол-во доступных для покупки мест на рейс между желаемыми станциями отправления и прибытия
     * @param departure
     * @param arrive
     * @return кол-во доступных для покупки мест
     */
    public int countFreePlacesOfTrip(Board departure, Board arrive) {
        Trip trip = departure.getTrip();
        Path path = trip.getPath();
        //TODO: поменять код, чтобы станции брались из расписания, а не маршрута
        List<Station> stations = path.getStations();
        List<Ticket> tickets = ticketDAO.getTicketsOfTrip(trip);

        int begin = stations.indexOf(departure.getStation());
        int end = stations.indexOf(arrive.getStation());
        int max = 0;
        int[] fillness = new int[end - begin];
        for (int i = begin; i < end; i++) {
            int c = 0;
            for (Ticket t : tickets) {
                if (stations.indexOf(t.getDeparture().getStation()) <= i &&
                        stations.indexOf(t.getArrive().getStation()) >= i + 1) {
                    c++;
                }
            }
            fillness[i - begin] = c;
            if (c > max) {
                max = c;
            }
        }
        return trip.getTrain().getPlacesQty() - max;
    }

    public boolean isPassengerOnTrip(Passenger passenger, Trip trip) {
        List<Ticket> tickets = ticketDAO.getTicketsOfTrip(trip);

        for (Ticket t : tickets) {
            if (t.getPassenger().equals(passenger)) {
                return true;
            }
        }
        return false;
    }

    public boolean canBuyTicket(Passenger passenger, Board departure, Board arrive) throws Exception {
        final long TEN_MINUTES = 1000L * 60 * 10;
        if (countFreePlacesOfTrip(departure, arrive) <= 0) {
            throw new Exception("Out of free spaces");
        }
        if (isPassengerOnTrip(passenger, departure.getTrip())) {
            throw new Exception("Passenger has already bought ticket on this trip");
        }
        if (departure.getDepartureTime().getTime() - (new Date()).getTime() < TEN_MINUTES ) {
            throw new Exception("Less than ten minutes before train departure");
        }
        return true;
    }

    public Ticket buyTicket(Passenger passenger, Board departure, Board arrive) throws Exception {
        if (canBuyTicket(passenger, departure, arrive)) {
            Ticket ticket = new Ticket(passenger, departure, arrive);
            EntityTransaction trx = ticketDAO.getTransaction();
            try {
                trx.begin();
                ticketDAO.create(ticket);
                trx.commit();
            } finally {
                if (trx.isActive()) {
                    trx.rollback();
                    return null;
                }
            }
            return ticket;
        }
        return null;
    }

    public Ticket buyTicket(String firstName, String lastName, Date birthDate, Board departure, Board arrive) throws Exception {
        Passenger passenger = passengerDAO.findByNameAndBirthDate(firstName, lastName, birthDate);
        if (passenger == null) {
            passenger = addPassenger(firstName, lastName, birthDate);
            if (passenger == null) {
                return null;
            }
        }
        if (canBuyTicket(passenger, departure, arrive)) {
            Ticket ticket = new Ticket(passenger, departure, arrive);
            EntityTransaction trx = ticketDAO.getTransaction();
            try {
                trx.begin();
                ticketDAO.create(ticket);
                trx.commit();
            } finally {
                if (trx.isActive()) {
                    trx.rollback();
                    return null;
                }
            }
            return ticket;
        }
        return null;
    }

}
