package kayak.freestyle.competition.kflow.controllers;

import kayak.freestyle.competition.kflow.services.CategorieService;

import org.springframework.web.bind.annotation.*;


import kayak.freestyle.competition.kflow.dto.CategorieDto;

@RestController
@RequestMapping("/categories")
public class CategorieController extends GenericController<CategorieDto, CategorieService>{

    public CategorieController(CategorieService service) {
        super(service);
    }

}
