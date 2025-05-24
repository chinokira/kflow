package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.services.CategorieService;
import kayak.freestyle.competition.kflow.mappers.ParticipantMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;
    private final ParticipantMapper participantMapper;

    @GetMapping("/{id}/participants")
    public List<ParticipantDto> getParticipantsByCategorie(@PathVariable Long id) {
        Categorie categorie = categorieService.getCategorieWithParticipants(id);
        return categorie.getParticipants().stream()
                .map(participantMapper::modelToDto)
                .collect(Collectors.toList());
    }
}
