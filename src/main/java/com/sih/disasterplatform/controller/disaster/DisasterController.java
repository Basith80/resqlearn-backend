package com.sih.disasterplatform.controller.disaster;

import com.sih.disasterplatform.entity.Disaster;
import com.sih.disasterplatform.service.disaster.DisasterService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/disasters")
public class DisasterController {

    private final DisasterService disasterService;

    public DisasterController(DisasterService disasterService) {
        this.disasterService = disasterService;
    }

    @PostMapping
    public Disaster create(@RequestBody Disaster disaster) {
        return disasterService.save(disaster);
    }

    @GetMapping
    public List<Disaster> getAll() {
        return disasterService.getAll();
    }

    @GetMapping("/{id}")
    public Disaster getById(@PathVariable Long id) {
        return disasterService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        disasterService.deleteById(id);
    }
}
