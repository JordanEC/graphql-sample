package com.jordanec.graphql_sample.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jordanec.graphql_sample.service.TypeService;
import com.jordanec.graphql_sample.service.VehicleService;
import com.jordanec.graphql_sample.entity.Type;
import com.jordanec.graphql_sample.entity.Vehicle;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

@Component
public class GraphQLDataFetchers
{
    @Autowired
    VehicleService vehicleService;
    @Autowired
    TypeService typeService;

    @Autowired
    ObjectMapper objectMapper;

    public DataFetcher createVehicle()
    {
        return dataFetchingEnvironment -> {
            Object typeObject = dataFetchingEnvironment.getArgument("type");
            Type type = objectMapper.convertValue(typeObject, Type.class);
            String modelCode = dataFetchingEnvironment.getArgument("modelCode");
            String brandName = dataFetchingEnvironment.getArgument("brandName");
            String transmission = dataFetchingEnvironment.getArgument("transmission");
            Integer year = dataFetchingEnvironment.getArgument("year");
            String launchDate = dataFetchingEnvironment.getArgument("launchDate");
            String createdTime = dataFetchingEnvironment.getArgument("createdTime");
            OffsetDateTime dateTime = dataFetchingEnvironment.getArgument("dateTime");
            LocalDate date = dataFetchingEnvironment.getArgument("date");
            OffsetTime time = dataFetchingEnvironment.getArgument("time");
            return vehicleService.create(type, modelCode, brandName, transmission, year, createdTime,
                    launchDate, dateTime, date, time);
        };
    }

    public DataFetcher createType()
    {
        return dataFetchingEnvironment -> {
            Object typeObject = dataFetchingEnvironment.getArgument("type");
            Type type = objectMapper.convertValue(typeObject, Type.class);
            return typeService.save(type);
        };
    }


    public DataFetcher updateVehicle()
    {
        return dataFetchingEnvironment -> {
            Object vehicleObject = dataFetchingEnvironment.getArgument("vehicle");
            Boolean skipOnNull = dataFetchingEnvironment.getArgument("skipOnNull");
            Vehicle vehicle = objectMapper.convertValue(vehicleObject, Vehicle.class);
            return vehicleService.save(vehicle, skipOnNull);
        };
    }

    public DataFetcher saveVehicle()
    {
        return dataFetchingEnvironment -> {
            Object vehicleObject = dataFetchingEnvironment.getArgument("vehicle");
            Vehicle vehicle = objectMapper.convertValue(vehicleObject, Vehicle.class);
            return vehicleService.save(vehicle);
        };
    }

    public DataFetcher allVehicles()
    {
        return dataFetchingEnvironment -> vehicleService.findAll();
    }
    public DataFetcher allTypes()
    {
        return dataFetchingEnvironment -> typeService.findAll();
    }

    public DataFetcher getVehicle()
    {
        return dataFetchingEnvironment -> {
            Integer id = dataFetchingEnvironment.getArgument("id");
            return vehicleService.getById(id);
        };
    }

    public DataFetcher type()
    {
        return dataFetchingEnvironment -> {
            Integer id = dataFetchingEnvironment.getArgument("id");
            return typeService.getById(id);
        };
    }

    public DataFetcher getVehicles()
    {
        return dataFetchingEnvironment -> {
            Integer count = dataFetchingEnvironment.getArgument("count");
            return vehicleService.findAll(count);
        };
    }
}
