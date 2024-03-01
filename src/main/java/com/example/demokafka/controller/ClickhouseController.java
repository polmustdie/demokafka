package com.example.demokafka.controller;


import com.example.demokafka.model.GeoData;
import com.example.demokafka.service.GeoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/click")
public class ClickhouseController {

    private GeoService geoService;

    public ClickhouseController(GeoService geoService) {
        this.geoService = geoService;

    }

    @GetMapping("/findAll")
    @ResponseStatus(value = HttpStatus.OK)
    public Iterable<GeoData> getAllDataClick() {
        return geoService.getGeoDataClick();
    }



    @PostMapping("/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void save(@RequestBody @Valid GeoData data) {
        geoService.saveClick(data);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteById(@PathVariable int id) {
        geoService.deleteById(id);
    }

}