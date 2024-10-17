package com.csse.controller;

import com.csse.DTO.MonthlyWasteDto;
import com.csse.DTO.StatTrashbinDto;
import com.csse.DTO.Trashbin;
import com.csse.service.Imp.StatisticsServiceImple;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shaded_package.io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/stat")
public class StatisticeController {

    private final StatisticsServiceImple statisticsServiceImple;

    public StatisticeController(StatisticsServiceImple statisticsServiceImple) {
        this.statisticsServiceImple = statisticsServiceImple;
    }

    @GetMapping
    public ResponseEntity<Map<String,Double>> getWasteTypeByPercentage() {
        try {
            Map<String,Double> data = statisticsServiceImple.calculateTrashtypePercentage();
            return new ResponseEntity<>(data, org.springframework.http.HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //url: http://localhost:8080/api/stat/bininfo
    @GetMapping("/bininfo")
    public ResponseEntity<List<StatTrashbinDto>> getBinInfo() {
        try {
            List<StatTrashbinDto> data = statisticsServiceImple.getAllTrashbinInfo();
            return new ResponseEntity<>(data, org.springframework.http.HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //url: http://localhost:8080/api/stat/totalfortheweek
    @GetMapping("/totalfortheweek")
    public ResponseEntity<Double> getTotalForTheWeek() {
        try {
            double data = statisticsServiceImple.totalWasteCollectedForTheWeek();
            return new ResponseEntity<>(data, org.springframework.http.HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/totalbins")
    public ResponseEntity<Integer> getTotalBins() {
        try {
            int data = statisticsServiceImple.totalBinsAvailable();
            return new ResponseEntity<>(data, org.springframework.http.HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/manpower")
    public ResponseEntity<Integer> getManpower() {
        try {
            int data = statisticsServiceImple.totalWasteCollectionManpower();
            return new ResponseEntity<>(data, org.springframework.http.HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //url: http://localhost:8080/api/stat/graphdata
    @GetMapping("/graphdata")
    public ResponseEntity<List<MonthlyWasteDto>> getMonthlyStats() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(statisticsServiceImple.getFormattedMonthlyStatistics());
    }
}
