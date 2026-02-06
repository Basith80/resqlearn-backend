package com.sih.disasterplatform.dashboard.controller;

import com.sih.disasterplatform.dashboard.dto.DashboardResponse;
import com.sih.disasterplatform.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponse get() {
        return dashboardService.getDashboard();
    }
}
