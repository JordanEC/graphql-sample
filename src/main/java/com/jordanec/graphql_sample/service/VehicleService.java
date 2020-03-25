package com.jordanec.graphql_sample.service;

import com.jordanec.graphql_sample.entity.Type;
import com.jordanec.graphql_sample.entity.Vehicle;
import com.jordanec.graphql_sample.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    TypeService typeService;

    @Transactional
    public Vehicle merge(Vehicle existing, Vehicle input, Boolean skipOnNull)
    {
        if (input.getType() != null || !skipOnNull)
        {
            if (input.getType() != null)
            {
                if (input.getType().getId() != null)
                {
                    existing.setType(typeService.getById(input.getType().getId()));
                } else if (!StringUtils.isEmpty(input.getType()))
                {
                    existing.setType(typeService.getByName(input.getType().getName()));
                }
            }
            else
            {
                existing.setType(input.getType());
            }
        }
        if (input.getModelCode() != null || !skipOnNull)
        {
            existing.setModelCode(input.getModelCode());
        }
        if (input.getBrandName() != null || !skipOnNull)
        {
            existing.setBrandName(input.getBrandName());
        }
        if (input.getTransmission() != null || !skipOnNull)
        {
            existing.setTransmission(input.getTransmission());
        }
        if (input.getYear() != null || !skipOnNull)
        {
            existing.setYear(input.getYear());
        }
        if (input.getLaunchDate() != null || !skipOnNull)
        {
            existing.setLaunchDate(input.getLaunchDate());
        }
        if (input.getCreatedTime() != null || !skipOnNull)
        {
            existing.setCreatedTime(input.getCreatedTime());
        }
        if (input.getDateTime() != null || !skipOnNull)
        {
            existing.setDateTime(input.getDateTime());
        }
        if (input.getDate() != null || !skipOnNull)
        {
            existing.setDate(input.getDate());
        }
        if (input.getTime() != null || !skipOnNull)
        {
            existing.setTime(input.getTime());
        }
        if (input.getPublished() != null || !skipOnNull)
        {
            existing.setPublished(input.getPublished());
        }
        return save(existing);
    }

    @Transactional
    public Vehicle create(final Type type,final String modelCode, final String brandName,
            final String transmission, final Integer year, final String createdTime, final String launchDate,
            final OffsetDateTime dateTime, final LocalDate date, final OffsetTime time) {
        Vehicle vehicle = new Vehicle();
        if (type != null)
        {
            if (type.getId() != null)
            {
                vehicle.setType(typeService.getById(type.getId()));
            }
            else if (!StringUtils.isEmpty(type.getName()))
            {
                vehicle.setType(typeService.getByName(type.getName()));
            }
        }
        vehicle.setModelCode(modelCode);
        vehicle.setBrandName(brandName);
        vehicle.setTransmission(transmission);
        vehicle.setYear(year);
        if (!StringUtils.isEmpty(launchDate))
        {
            vehicle.setLaunchDate(LocalDate.parse(launchDate));
        }
        if (!StringUtils.isEmpty(createdTime))
        {
            vehicle.setCreatedTime(LocalDateTime.parse(createdTime));
        }
        vehicle.setDateTime(dateTime);
        vehicle.setDate(date);
        vehicle.setTime(time);
        return vehicleRepository.save(vehicle);
    }
    @Transactional
    public Vehicle save(Vehicle vehicleInput, Boolean skipOnNull) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleInput.getId());
        if (vehicleOptional.isPresent())
        {
            Vehicle vehicle = vehicleOptional.get();
            return merge(vehicle, vehicleInput, skipOnNull == null ? false : skipOnNull);
        }
        else
        {
            return save(vehicleInput);
        }
    }

    @Transactional
    public Vehicle save(Vehicle vehicleInput) {
        if (vehicleInput.getType() != null)
        {
            if (vehicleInput.getType().getId() != null)
            {
                vehicleInput.setType(typeService.getById(vehicleInput.getType().getId()));
            }
            else if (!StringUtils.isEmpty(vehicleInput.getType().getName()))
            {
                vehicleInput.setType(typeService.getByName(vehicleInput.getType().getName()));
            }
        }
        return vehicleRepository.save(vehicleInput);
    }

    public List<Vehicle> findAll(final int count) {
        return vehicleRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getById(final int id) {
        return vehicleRepository.findById(id);
    }
}
