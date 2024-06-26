package com.meli.pago.controllers;

import com.meli.pago.models.Auto;
import com.meli.pago.models.Owner;
import com.meli.pago.repositories.AutoRepository;
import com.meli.pago.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autos")
public class AutoController {

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping
    public ResponseEntity<Auto> createAuto(@RequestBody Auto auto) {
        if (auto.getOwner() != null && auto.getOwner().getId() == null) {
            ownerRepository.save(auto.getOwner());
        } else if (auto.getOwner() != null && auto.getOwner().getId() != null) {
            Owner existingOwner = ownerRepository.findById(auto.getOwner().getId()).orElse(null);
            auto.setOwner(existingOwner);
        }

        Auto savedAuto = autoRepository.save(auto);
        return ResponseEntity.ok(savedAuto);
    }


    @GetMapping
    public ResponseEntity<List<Auto>> getAllAutos() {
        List<Auto> autos = autoRepository.findAll();
        return ResponseEntity.ok(autos);
    }
}
