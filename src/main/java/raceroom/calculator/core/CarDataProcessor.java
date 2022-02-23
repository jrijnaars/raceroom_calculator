package raceroom.calculator.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.CarDataEntity;
import raceroom.calculator.repositories.CarDataRepository;
import raceroom.calculator.rest.CarDataDTO;
import raceroom.calculator.rest.ItemDTO;
import raceroom.calculator.rest.SpecsDTO;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class CarDataProcessor {

    @Autowired
    CarDataRepository carDataRepository;

    public void insertCardataIntoDB(CarDataDTO carDataDTO) {
        ItemDTO itemDTO = carDataDTO.getContextDTO().getCdto().getItemDTO();
        SpecsDTO specsDTO = itemDTO.getSpecsDTO();
        String[] splittedWeight = specsDTO.getWeight().split(" ");
        BigDecimal weight = BigDecimal.valueOf(Double.parseDouble(splittedWeight[0].replaceAll("[^0-9]", "")));
        String[] splittedHorsepower = specsDTO.getHorsePower().split(" ");
        BigDecimal horsepower = BigDecimal.valueOf(Double.parseDouble(splittedHorsepower[0].replaceAll("[^0-9]", "")));

        CarDataEntity  carDataEntity = new CarDataEntity();
        carDataEntity.setCarName(itemDTO.getCarName());
        carDataEntity.setCarclass(itemDTO.getCarclassDTO().getCarClass());
        carDataEntity.setOrigin(specsDTO.getOrigin());
        carDataEntity.setProduction_year(specsDTO.getProduction_year());
        carDataEntity.setWeight(weight);
        carDataEntity.setEngine_type(specsDTO.getEngine_type());
        carDataEntity.setAspiration(specsDTO.getAspiration());
        carDataEntity.setDrive_type(specsDTO.getDrive_type());
        carDataEntity.setHorse_power(horsepower);
        carDataEntity.setPowerToWeigth(horsepower.divide(weight,3, RoundingMode.CEILING));
        carDataRepository.save(carDataEntity);
        log.info("Car " + carDataEntity.getCarName() +" is added to the database");
    }

    @SneakyThrows
    public void getCarDataFromRecources() {
        List<Path> collect;
        try (Stream<Path> paths = Files.walk(Paths.get("src/main/resources/cars"))) {
            collect = paths.filter(Files::isRegularFile)
                    .collect(Collectors.toList());

        }

        for (Path f: collect) {
            ObjectMapper mapper = new ObjectMapper();
            CarDataDTO carDataDTO = mapper.readValue(new File(f.toAbsolutePath().toString()), CarDataDTO.class);
            insertCardataIntoDB(carDataDTO);
        }
    }
}
