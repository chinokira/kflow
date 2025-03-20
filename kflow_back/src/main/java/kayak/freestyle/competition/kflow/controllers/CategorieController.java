package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.services.CategorieService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping("/{id}/participants")
    public Categorie getCategorieWithParticipants(@PathVariable Long id) {
        return categorieService.getCategorieWithParticipants(id);
    }
}
