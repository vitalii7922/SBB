package com.tsystems.javaschool.vm.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.vm.domain.Ticket;
import com.tsystems.javaschool.vm.domain.Trip;
import com.tsystems.javaschool.vm.dto.TripDTO;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.service.PassengerService;
import com.tsystems.javaschool.vm.service.PathService;
import com.tsystems.javaschool.vm.service.TrainService;
import com.tsystems.javaschool.vm.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private TrainService trainService;
    @Autowired
    private PathService pathService;
    @Autowired
    private TripService tripService;
    @Autowired
    private ObjectMapper json;
    @Autowired
    private PassengerService passengerService;

    @RequestMapping("/index")
    public String listTrains(Map<String, Object> map) {
        map.put("trainList", trainService.getAllTrains());
        map.put("pathList", pathService.getAllPaths());
        return "trip";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "redirect:/trip/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addTrip(@RequestParam("pathId") Long pathId, @RequestParam("trainId") Long trainId) {
        try {
            Trip trip = tripService.addTrip(pathId, trainId);
            TripDTO tripDTO = new TripDTO(trip.getId(),
                    trip.getTrain().getId(), trip.getTrain().getNumber(),
                    trip.getPath().getId(), trip.getPath().getTitle(), trip.getLastChange());
            return json.writeValueAsString(tripDTO);
        } catch (Exception e) {
            return "error " + e.toString();
        }
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public String selectTrip(@RequestParam("pathId") Long pathId, @RequestParam("trainId") Long trainId) {

        List<Trip> trips = tripService.getTripsByPathAndTrain(pathId, trainId);

        List<TripDTO> tripDTOs = new ArrayList<TripDTO>();
        for (Trip trip : trips) {
            TripDTO tripDTO = new TripDTO(trip.getId(),
                    trip.getTrain().getId(), trip.getTrain().getNumber(),
                    trip.getPath().getId(), trip.getPath().getTitle(), trip.getLastChange());
            tripDTOs.add(tripDTO);
        }

        try {
            return json.writeValueAsString(tripDTOs);
        } catch (JsonProcessingException e) {
            return "error " + e.toString();
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    String editTrip(@RequestParam("trip") String tripJSON) {
        try {
            TripDTO tripDTO = json.readValue(tripJSON, TripDTO.class);
            tripService.editTrip(tripDTO.getId(), tripDTO.getPathId(), tripDTO.getTrainId(), tripDTO.getLastChange());
            return "success";
        } catch (OutdateException e) {
            return "outdate " + e;
        } catch (Exception e) {
            return "error " + e;
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    String removeTrip(@RequestParam("tripId") Long tripId, @RequestParam("lci") Integer lci) {
        try {
            tripService.removeTrip(tripId, lci);
        } catch (OutdateException e) {
            return "outdate " + e;
        } catch (Exception e) {
            return "error " + e;
        }
        return "success";
    }

    @RequestMapping("/passengers/{tripId}")
    public String getPassengers(@PathVariable("tripId") Long tripId, ModelMap map) {
        try {
            Trip trip = tripService.findById(tripId);
            List<Ticket> tickets = passengerService.getTicketsOfTrip(tripId);
            Collections.sort(tickets);
            map.put("trip", trip);
            map.put("ticketList", tickets);
            return "trip/passengers";
        } catch (EntityNotFoundException e) {
            map.put("errors", Arrays.asList(e.getMessage()));
            return "msg";
        }
    }
}
