package org.example.Lab2_BLPS.service.report.ws;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.example.Lab2_BLPS.service.report.service.ReportService;
import org.example.Lab2_BLPS.service.report.ws.dto.ReportDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/userReport")
@RequiredArgsConstructor
public class ReportUserController {

    private final ReportService reportService;

    private final ConversionService conversionService;

    @PostMapping(path = "/sendReport")
    public ReportDto sendReport(@Valid @RequestBody ReportDto report) {
        return conversionService.convert(
                reportService.sendReport(conversionService.convert(report, ReportEntity.class)),
                ReportDto.class
        );
    }
}
