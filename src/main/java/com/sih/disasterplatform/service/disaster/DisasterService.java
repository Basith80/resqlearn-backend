package com.sih.disasterplatform.service.disaster;

import com.sih.disasterplatform.entity.Disaster;
import com.sih.disasterplatform.repository.DisasterRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DisasterService {

    private final DisasterRepository disasterRepository;

    public DisasterService(DisasterRepository disasterRepository) {
        this.disasterRepository = disasterRepository;
    }

    // Save or create new disaster
    public Disaster save(Disaster disaster) {
        return disasterRepository.save(disaster);
    }

    // Get all disasters
    public List<Disaster> getAll() {
        return disasterRepository.findAll();
    }

    // Get by ID
    public Disaster getById(Long id) {
        return disasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));
    }

    // Delete disaster
    public void deleteById(Long id) {
        disasterRepository.deleteById(id);
    }
}
