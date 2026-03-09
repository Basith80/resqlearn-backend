package com.sih.disasterplatform.service.drill;

import com.sih.disasterplatform.entity.VirtualDrill;
import com.sih.disasterplatform.repository.DrillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VirtualDrillService {

    private final DrillRepository drillRepository;

    public VirtualDrillService(DrillRepository drillRepository) {
        this.drillRepository = drillRepository;
    }

    public VirtualDrill save(VirtualDrill drill) {
        return drillRepository.save(drill);
    }

    public List<VirtualDrill> getAll() {
        return drillRepository.findAll();
    }

    public List<VirtualDrill> getByDisasterId(Long disasterId) {
        return drillRepository.findByDisaster_Id(disasterId);
    }

    // ✅ FIXED: delete was missing
    public void delete(Long id) {
        drillRepository.deleteById(id);
    }
}