package com.sih.disasterplatform.controller.emergency;

import com.sih.disasterplatform.entity.EmergencyContact;
import com.sih.disasterplatform.service.emergency.EmergencyContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency-contacts")
public class EmergencyContactController {

    private final EmergencyContactService service;

    public EmergencyContactController(EmergencyContactService service) {
        this.service = service;
    }

    @PostMapping
    public EmergencyContact create(@RequestBody EmergencyContact contact) {
        return service.save(contact);
    }

    @GetMapping
    public List<EmergencyContact> getAll() {
        return service.getAll();
    }
}
