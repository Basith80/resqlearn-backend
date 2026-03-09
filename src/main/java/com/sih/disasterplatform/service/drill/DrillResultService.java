package com.sih.disasterplatform.service.drill;

import com.sih.disasterplatform.entity.DrillResult;
import com.sih.disasterplatform.repository.DrillResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrillResultService {

    private final DrillResultRepository repository;

    public DrillResultService(DrillResultRepository repository) {
        this.repository = repository;
    }

    public DrillResult save(DrillResult result) {
        return repository.save(result);
    }

    public List<DrillResult> getByStudentId(Long studentId) {
        return repository.findByStudent_Id(studentId);
    }

    public List<DrillResult> getByDrillId(Long drillId) {
        return repository.findByDrill_Id(drillId);
    }

    public List<DrillResult> getByUserEmail(String email) {
        return repository.findByUserEmail(email);
    }
}