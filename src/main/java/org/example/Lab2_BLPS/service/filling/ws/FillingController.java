package org.example.Lab2_BLPS.service.filling.ws;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.filling.service.FillingService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/filling")
@RequiredArgsConstructor
public class FillingController {

    private final FillingService fillingService;

    @PostMapping(path = "/fillDb")
    public void sendReport() {
        fillingService.fillingDb();
    }
}
