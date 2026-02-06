package com.sih.disasterplatform.teacher.controller;

import com.sih.disasterplatform.disaster.service.SchoolDisasterAccessService;
import com.sih.disasterplatform.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/disasters")
@RequiredArgsConstructor
public class TeacherDisasterController {

    private final SchoolDisasterAccessService accessService;

    @PostMapping("/{disasterId}/unlock")
    public void unlock(@PathVariable Long disasterId) {
        accessService.unlock(SecurityUtil.getCurrentSchoolId(), disasterId);
    }
}
