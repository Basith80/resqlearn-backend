package com.sih.disasterplatform.service.emergency;

import com.sih.disasterplatform.entity.EmergencyContact;
import com.sih.disasterplatform.repository.EmergencyContactRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmergencyContactService {

    private final EmergencyContactRepository repository;

    public EmergencyContactService(EmergencyContactRepository repository) {
        this.repository = repository;
    }

    public EmergencyContact save(EmergencyContact contact) {
        return repository.save(contact);
    }

    public List<EmergencyContact> getAll() {
        return repository.findAll();
    }
}
