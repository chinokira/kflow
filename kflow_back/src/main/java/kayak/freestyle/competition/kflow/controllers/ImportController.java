package kayak.freestyle.competition.kflow.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.ImportCompetitionDto;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.services.ImportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
public class ImportController {

    private final ImportService importService;

    @PostMapping("/validate")
    public ResponseEntity<List<String>> validateImport(@RequestBody ImportCompetitionDto importDto) {
        List<String> errors = importService.validateImport(importDto);
        if (errors.isEmpty()) {
            return ResponseEntity.ok(errors);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @PostMapping("/competition")
    public ResponseEntity<Competition> importCompetition(@RequestBody ImportCompetitionDto importDto) {
        // Valider d'abord
        List<String> errors = importService.validateImport(importDto);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Importer si valide
        Competition competition = importService.importCompetition(importDto);
        return ResponseEntity.ok(competition);
    }
}
