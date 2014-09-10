package com.tsystems.javaschool.vm.web;


import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.dto.StationDTO;
import com.tsystems.javaschool.vm.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Controller
public class PathController {
    private final String root = "path";
    private final String rootWithSlash = "/" + root;
    private final String index = rootWithSlash + "/index";
    private final String redirect = "redirect:" + index;

    @Autowired
    StationService stationService;

    @RequestMapping(index)
    public String listPaths(Map<String, Object> map) {
        map.put("station", new StationDTO());
        List<StationDTO> stationDTOs = new ArrayList<StationDTO>();
        for (Station station : stationService.getAllStations()) {
            StationDTO stationDTO = new StationDTO(station.getId(), station.getTitle(), station.getTimeZone().getID());
            stationDTOs.add(stationDTO);
        }
        map.put("stationList", stationDTOs);

        return root;
    }

    @RequestMapping(rootWithSlash)
    public String home() {
        return redirect;
    }

    @RequestMapping(value = rootWithSlash + "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addStation(@Valid @ModelAttribute(value = "station") StationDTO stationDTO, BindingResult result) {

        if (result.hasErrors()) {
            return "";
        }
        Station station = new Station(stationDTO.getTitle(), TimeZone.getTimeZone(stationDTO.getTimeZone()));
        stationService.addStation(station);
        return station.getId().toString();
    }

    @RequestMapping(value = rootWithSlash + "/edit", method = RequestMethod.POST)
    public @ResponseBody
    String editTrain(@Valid @ModelAttribute(value = "station") Station station, BindingResult result) {

        if (result.hasErrors()) {
            return "";
        }

        stationService.editStation(station);
        return station.getId().toString();
    }

    @RequestMapping(value = rootWithSlash + "/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String removeTrain(@PathVariable("id") Long trainId) {
        stationService.removeStation(trainId);

        return "";
    }
}
