package com.personallibrary.personal_library_backend.controller;

import com.personallibrary.personal_library_backend.dto.ApiResponse;
import com.personallibrary.personal_library_backend.dto.dashboard.DashboardResponse;
import com.personallibrary.personal_library_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ApiResponse<DashboardResponse> getDashboard() {

        return ApiResponse.<DashboardResponse>builder()
                .success(true)
                .message("Dashboard loaded successfully.")
                .data(dashboardService.getDashboard())
                .build();

    }
}
