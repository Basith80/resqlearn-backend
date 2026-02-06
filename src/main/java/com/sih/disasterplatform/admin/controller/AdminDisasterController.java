package com.sih.disasterplatform.admin.controller;

import com.sih.disasterplatform.disaster.entity.Disaster;
import com.sih.disasterplatform.disaster.repository.DisasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/disasters")
@RequiredArgsConstructor
public class AdminDisasterController {

    private final DisasterRepository disasterRepository;

    @PostMapping
    public Disaster create(@RequestBody Disaster disaster) {
        return disasterRepository.save(disaster);
    }
}
