package com.sih.disasterplatform.service.education;

import com.sih.disasterplatform.entity.EducationContent;
import com.sih.disasterplatform.repository.EducationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public EducationContent save(EducationContent content) {
        return educationRepository.save(content);
    }

    public List<EducationContent> getAll() {
        return educationRepository.findAll();
    }

    public List<EducationContent> getByDisasterId(Long disasterId) {
        return educationRepository.findByDisaster_Id(disasterId);
    }
}
