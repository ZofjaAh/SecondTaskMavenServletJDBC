package com.aston.secondTask.service;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DTOFixtures;
import util.EntityFixtures;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoordinatorServiceTest {
    @InjectMocks
    private CoordinatorService coordinatorService;
    @Mock
    private CoordinatorDAO coordinatorDAO;

    @Test
    void shouldCreateCoordinatorCorrectly() {
        CoordinatorDTO coordinatorDTO = DTOFixtures.coordinator_1();
        CoordinatorEntity coordinatorEntity = EntityFixtures.getCoordinator_1();
        int coordinatorId = 1;
        when(coordinatorDAO.createCoordinator(coordinatorEntity)).thenReturn(coordinatorId);
        int result = coordinatorService.createCoordinator(coordinatorDTO);
        Assertions.assertEquals(coordinatorId, result);
    }

    @Test
    void shouldDeleteCoordinatorSuccessful() {
        int coordinatorId = 1;
        int numberChangedRows = 2;
        when(coordinatorDAO.deleteById(coordinatorId)).thenReturn(numberChangedRows);
        int result = coordinatorService.deleteById(coordinatorId);
        Assertions.assertEquals(numberChangedRows, result);
    }

    @Test
    void shouldUpdateCoordinatorNameSuccessful() {
        int coordinatorId = 1;
        int numberChangedRows = 1;
        String coordinatorName = "Stefan BadDancer";
        when(coordinatorDAO.updateCoordinatorName(coordinatorId, coordinatorName)).thenReturn(numberChangedRows);
        int result = coordinatorService.updateCoordinatorName(coordinatorId, coordinatorName);
        Assertions.assertEquals(numberChangedRows, result);
    }

    @Test
    void shouldSearchAllCoordinatorsSuccessful() {
        List<CoordinatorEntity> coordinatorEntityList = List.of(EntityFixtures.coordinator_1_0(), EntityFixtures.coordinator_2_0());
        Set<CoordinatorDTO> coordinatorDTOSet = Set.of(DTOFixtures.coordinator_1_0(), DTOFixtures.coordinator_2_0());
        when(coordinatorDAO.findAll()).thenReturn(coordinatorEntityList);
        Set<CoordinatorDTO> result = coordinatorService.findAll();
        List<String> coordinatorNames = coordinatorDTOSet.stream().map(CoordinatorDTO::getName).sorted().toList();
        List<String> resultNames = result.stream().map(CoordinatorDTO::getName).sorted().toList();
        Assertions.assertEquals(coordinatorDTOSet.size(), result.size());
        Assertions.assertEquals(coordinatorNames, resultNames);
    }

    @Test
    void shouldSearchCoordinatorByIdWithStudentsSuccessful() {
        CoordinatorEntity coordinatorEntity = EntityFixtures.coordinator_1_0()
                .withStudents(Set.of(EntityFixtures.student_2_0()));
        CoordinatorDTO coordinatorDTO = DTOFixtures.coordinator_1_0().withStudents(Set.of(DTOFixtures.student_2_0()));
        int coordinatorId = 1;
        when(coordinatorDAO.findCoordinatorWithStudentsByID(1)).thenReturn(coordinatorEntity);
        CoordinatorDTO result = coordinatorService.findById(coordinatorId);
        Assertions.assertEquals(coordinatorDTO.getName(), result.getName());
        Assertions.assertEquals(coordinatorDTO.getStudents().size(), result.getStudents().size());
    }


}