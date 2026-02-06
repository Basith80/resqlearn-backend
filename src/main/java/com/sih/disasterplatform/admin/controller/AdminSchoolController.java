package com.sih.disasterplatform.admin.controller;

import com.sih.disasterplatform.school.entity.School;
import com.sih.disasterplatform.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/schools")
@RequiredArgsConstructor
public class AdminSchoolController {

    private final SchoolRepository schoolRepository;

    @PostMapping
    public School create(@RequestBody School school) {
        return schoolRepository.save(school);
    }
}
