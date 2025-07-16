package com.Week3Homework.Homework.service;


import com.Week3Homework.Homework.dto.ProfessorDto;
import com.Week3Homework.Homework.dto.ProfessorWithSubjectsDto;
import com.Week3Homework.Homework.entities.ProfessorEntity;
import com.Week3Homework.Homework.repository.ProfessorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;


    public ProfessorService(ProfessorRepository professorRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }


    public List<ProfessorDto> getAllProfessors() {
        List<ProfessorEntity> professors = professorRepository.findAll();

        if (professors.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Nenhum professor encontrado");
        }

        return professors.stream()
                .map(professor -> modelMapper.map(professor, ProfessorDto.class))
                .toList();
    }


    public ProfessorWithSubjectsDto getProfessorById(Long id) {
        ProfessorEntity professor = professorRepository.findProfessorWithSubjectsById(id)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Professor não encontrado"));

        return modelMapper.map(professor, ProfessorWithSubjectsDto.class);
    }

    public ProfessorDto createProfessor(ProfessorDto professorDto) {
        ProfessorEntity professorEntity = modelMapper.map(professorDto, ProfessorEntity.class);
        ProfessorEntity savedProfessor = professorRepository.save(professorEntity);
        return modelMapper.map(savedProfessor, ProfessorDto.class);
    }


}
