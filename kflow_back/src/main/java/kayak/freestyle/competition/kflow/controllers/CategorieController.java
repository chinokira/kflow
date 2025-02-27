package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.services.CategorieService;

@RestController
@RequestMapping("/categories")
public class CategorieController extends GenericController<CategorieDto, CategorieService> {

    public CategorieController(CategorieService service) {
        super(service);
    }

}
