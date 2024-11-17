package kayak.freestyle.competition.kflow.controllers;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import kayak.freestyle.competition.kflow.dto.HasId;
import kayak.freestyle.competition.kflow.exceptions.BadRequestException;
import kayak.freestyle.competition.kflow.services.GenericService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenericController<DTO extends HasId, SERVICE extends GenericService<?, DTO, ?, ?>> {

    protected final SERVICE service;

    @GetMapping
    public Collection<DTO> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public DTO findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    public DTO save(@Valid @RequestBody DTO user) {
        if (user.getId() != 0) {
            throw new BadRequestException("id must be null or zero");
        }
        return service.save(user);
    }

    @Transactional
    @PutMapping("{id}")
    public void update(@PathVariable long id, @Valid @RequestBody DTO user) {
        if (user.getId() == 0) {
            user.setId(id);
        } else if (user.getId() != id) {
            throw new BadRequestException("id in url and body must be the same");
        }
        service.save(user);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }

}
