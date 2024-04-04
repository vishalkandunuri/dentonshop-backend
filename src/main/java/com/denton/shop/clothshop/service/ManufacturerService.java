package com.denton.shop.clothshop.service;

import com.denton.shop.clothshop.model.Manufacturer;
import com.denton.shop.clothshop.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public ResponseEntity<?> addManufacturer(Manufacturer manufacturer){
        try{
            Manufacturer m = new Manufacturer();
            m.setName(manufacturer.getName());
            m.setAddress(manufacturer.getAddress());
            manufacturerRepository.save(m);
            return new ResponseEntity<>("Manufacturer added Successfully.", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to add Manufacturer",HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateManufacturer(Manufacturer manufacturer){
        try{
            Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(manufacturer.getId());
            if(optionalManufacturer.isPresent()){
                Manufacturer m = optionalManufacturer.get();
                m.setName(manufacturer.getName());
                m.setAddress(manufacturer.getAddress());
                manufacturerRepository.save(m);
                return new ResponseEntity<>("Manufacturer updated Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Manufacturer Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to update Manufacturer", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> deleteManufacturer(long manufacturerId){
        try{
            Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(manufacturerId);
            if(optionalManufacturer.isPresent()){
                manufacturerRepository.delete(optionalManufacturer.get());
                return new ResponseEntity<>("Manufacturer Deleted Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Manufacturer Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to delete Manufacturer", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<List<Manufacturer>> getAllManufacturers(){
        try{
            List<Manufacturer> manufacturers = manufacturerRepository.findAll();
            return new ResponseEntity<>(manufacturers, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
