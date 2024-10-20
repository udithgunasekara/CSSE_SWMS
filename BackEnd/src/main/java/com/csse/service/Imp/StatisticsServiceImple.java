package com.csse.service.Imp;

import com.csse.DTO.*;
import com.csse.repo.CollectionHistoryRepository;
import com.csse.repo.RepoInterface.ICollecitonPersonRepo;
import com.csse.repo.RepoInterface.ICollectionHistory;
import com.csse.repo.RepoInterface.ITrashBinRepository;
import com.csse.repo.TrashBinRepository;
import com.csse.repo.WasteCollectionPersonalRepo;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class StatisticsServiceImple {
    private final ICollectionHistory collectionHistoryRepository;
    private final ITrashBinRepository trashBinRepository;;
    private final ICollecitonPersonRepo wasteCollectionPersonalRepo;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;

    public StatisticsServiceImple(ICollectionHistory collectionHistoryRepository, ITrashBinRepository trashBinRepository, ICollecitonPersonRepo wasteCollectionPersonalRepo) {
        this.collectionHistoryRepository = collectionHistoryRepository;
        this.trashBinRepository = trashBinRepository;
        this.wasteCollectionPersonalRepo = wasteCollectionPersonalRepo;
    }

    public Map<String,Double> calculateTrashtypePercentage() throws ExecutionException, InterruptedException {
        List<CollectionHistory> allHistory = collectionHistoryRepository.getAllHistory();
        Map<String,Double> weightType = new HashMap<>();
        double totalweight = 0;

        for (CollectionHistory history : allHistory) {
            String trashbinID = history.getTagid();
            double weight = history.getWeight();
            Optional<Trashbin> trashbin = trashBinRepository.findTrashbinById(trashbinID);
            if(trashbin.isPresent()){
                String type = trashbin.get().getTrashbinType();
                if(weightType.containsKey(type)){
                    weightType.put(type, weightType.get(type) + weight);
                }else{
                    weightType.put(type, weight);
                }
                totalweight += weight;
            }

        }

        Map<String, Double> percentage = new HashMap<>();
        for (Map.Entry<String, Double> entry : weightType.entrySet()) {
            double value = (entry.getValue() / totalweight) * 100;
            percentage.put(entry.getKey(), value);
        }
        return percentage;
    }

    public List<StatTrashbinDto> getAllTrashbinInfo() throws ExecutionException, InterruptedException {
        List<StatTrashbinDto> trashbindetailslist = new ArrayList<>();

        List<Trashbin> allTrashbins = trashBinRepository.getAllTrashbins();

        for (Trashbin trashbin : allTrashbins) {
            String trashbinID = trashbin.getTrashbinId();
            String type = trashbin.getTrashbinType();
            Double weight = trashbin.getWeight();
            Double wasteLevel = trashbin.getWasteLevel();
            if(wasteLevel>50){
                StatTrashbinDto stattrash = new StatTrashbinDto(trashbinID, type, weight, wasteLevel);
                trashbindetailslist.add(stattrash);
            }
        }
        return trashbindetailslist;
    }

    public Double totalWasteCollectedForTheWeek() throws ExecutionException, InterruptedException {
        double totalWeight = 0;
        List< CollectionHistory> historyDoc = collectionHistoryRepository.historyOfPastWeek();
        for (CollectionHistory doc : historyDoc) {
            Double weight = doc.getWeight();
            if (weight != null) {
                totalWeight += weight;
            }
        }
        return totalWeight;
    }

    public Integer totalBinsAvailable() throws ExecutionException, InterruptedException {
        Integer count = 0;
        List<Trashbin> allTrashbins = trashBinRepository.getAllTrashbins();
        count = allTrashbins.size();
        return count;

    }

    public Integer totalWasteCollectionManpower () throws ExecutionException, InterruptedException {
        Integer count;
        List<WasteCollectionPersonal> personallist = wasteCollectionPersonalRepo.getAllWasteCollectionPersonal();
        count = personallist.size();
        return count;
    }

    //method to format the data into the graph format
    public Map<YearMonth, Map<String, Double>> getMonthlyWaste() throws ExecutionException, InterruptedException {
        List<CollectionHistory> allHistory = collectionHistoryRepository.getAllHistory();
        Map<YearMonth, Map<String, Double>> monthlyWaste = new HashMap<>();

        for (CollectionHistory history : allHistory) {
            try {
                LocalDate collectionDate = LocalDate.parse(history.getDate(),dateFormatter);
                YearMonth yearMonth = YearMonth.from(collectionDate);
                Trashbin bin = trashBinRepository.findTrashbinById(history.getTagid()).orElse(null);
                if (bin != null){
                    String wasteType = bin.getTrashbinType();
                    double weight = history.getWeight();

                    // Get or create the map for this month
                    Map<String, Double> monthData = monthlyWaste.computeIfAbsent(yearMonth, k -> new HashMap<>());

                    // Add weight to existing total or create new entry
                    monthData.merge(wasteType, weight, Double::sum);
                }
            }catch (Exception e){
                System.out.println("Error processing record with date: " + history.getDate());
                continue;
            }
        }

        return monthlyWaste;

    }

    public List<MonthlyWasteDto> getFormattedMonthlyStatistics() throws ExecutionException, InterruptedException {
        Map<YearMonth, Map<String, Double>> stats = getMonthlyWaste();
        List<MonthlyWasteDto> formattedStats = new ArrayList<>();

        for (Map.Entry<YearMonth, Map<String, Double>> entry : stats.entrySet()) {
            YearMonth yearMonth = entry.getKey();
            Map<String, Double> wasteTypes = entry.getValue();

            MonthlyWasteDto monthStat = new MonthlyWasteDto();
            monthStat.setMonth(yearMonth.getMonth().toString());
            monthStat.setWasteStats(wasteTypes);

            formattedStats.add(monthStat);
        }

        return formattedStats;
    }


}
