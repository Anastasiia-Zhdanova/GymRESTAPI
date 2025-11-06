package com.company.gym.mapper;

import com.company.gym.dto.response.TraineeShortResponse;
import com.company.gym.dto.response.TrainerProfileResponse;
import com.company.gym.dto.response.TrainerShortResponse;
import com.company.gym.dto.response.TrainingListResponse;
import com.company.gym.dto.response.TrainingTypeResponse;
import com.company.gym.entity.Trainee;
import com.company.gym.entity.Trainer;
import com.company.gym.entity.Training;
import com.company.gym.entity.TrainingType;
import com.company.gym.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerMapperTest {

    @Mock
    private TraineeMapper traineeMapper; // Mock the dependency

    @InjectMocks
    private TrainerMapper trainerMapper = Mappers.getMapper(TrainerMapper.class);

    private Trainer mockTrainer;
    private Trainee mockTrainee;
    private Training mockTraining;
    private TrainingType mockType;

    @BeforeEach
    void setUp() {
        // --- Entities Setup ---
        User user = new User();
        user.setUsername("coach.user");
        user.setFirstName("Coach");
        user.setLastName("User");
        user.setIsActive(true);

        mockType = new TrainingType("Weights");
        mockType.setId(10L);

        mockTrainer = new Trainer();
        mockTrainer.setUser(user);
        mockTrainer.setSpecialization(mockType);
        mockTrainer.setTrainees(new HashSet<>());

        User traineeUser = new User();
        traineeUser.setFirstName("Client");
        traineeUser.setLastName("C");
        mockTrainee = new Trainee();
        mockTrainee.setUser(traineeUser);

        mockTraining = new Training();
        mockTraining.setTrainingName("Lift");
        mockTraining.setTrainingDate(new Date());
        mockTraining.setTrainingDuration(90);
        mockTraining.setTrainingType(mockType);
        mockTraining.setTrainee(mockTrainee);
    }

    @Test
    void toTrainerProfileResponse_Success() {
        mockTrainer.getTrainees().add(mockTrainee);
        TraineeShortResponse mockShortResponse = new TraineeShortResponse();
        when(traineeMapper.toTraineeShortResponse(mockTrainee)).thenReturn(mockShortResponse);

        TrainerProfileResponse response = trainerMapper.toTrainerProfileResponse(mockTrainer);

        assertNotNull(response);
        assertEquals("coach.user", response.getUsername());
        assertEquals("Weights", response.getSpecialization().getTrainingTypeName());
        assertTrue(response.getIsActive());
        assertEquals(1, response.getTraineesList().size());
        assertTrue(response.getTraineesList().contains(mockShortResponse));
    }

    @Test
    void toTrainerProfileResponse_NullTrainees() {
        mockTrainer.setTrainees(null);

        TrainerProfileResponse response = trainerMapper.toTrainerProfileResponse(mockTrainer);

        assertNotNull(response.getTraineesList());
        assertTrue(response.getTraineesList().isEmpty());
    }

    @Test
    void toTrainerShortResponse_Success() {
        TrainerShortResponse response = trainerMapper.toTrainerShortResponse(mockTrainer);

        assertNotNull(response);
        assertEquals("coach.user", response.getUsername());
        assertEquals("Coach", response.getFirstName());
        assertEquals("Weights", response.getSpecialization().getTrainingTypeName());
        assertEquals(10L, response.getSpecialization().getId());
    }

    @Test
    void toTrainerShortResponseList_Success() {
        List<Trainer> trainers = List.of(mockTrainer);
        List<TrainerShortResponse> responses = trainerMapper.toTrainerShortResponseList(trainers);

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void toTrainingListResponse_Success() {
        TrainingListResponse response = trainerMapper.toTrainingListResponse(mockTraining);

        assertNotNull(response);
        assertEquals("Lift", response.getTrainingName());
        assertEquals("Weights", response.getTrainingType());
        assertEquals("Client C", response.getAssociatedUserName());
    }

    @Test
    void toTrainingListResponseList_Success() {
        List<Training> trainings = List.of(mockTraining);
        List<TrainingListResponse> responses = trainerMapper.toTrainingListResponse(trainings);

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void toTrainingListResponseList_NullInput() {
        assertNull(trainerMapper.toTrainingListResponse((List<Training>) null));
    }

    @Test
    void toTrainingTypeResponse_Success() {
        TrainingTypeResponse response = trainerMapper.toTrainingTypeResponse(mockType);

        assertNotNull(response);
        assertEquals("Weights", response.getTrainingTypeName());
        assertEquals(10L, response.getId());
    }

    @Test
    void toTrainingTypeResponseList_Success() {
        List<TrainingType> types = List.of(mockType);
        List<TrainingTypeResponse> responses = trainerMapper.toTrainingTypeResponseList(types);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Weights", responses.get(0).getTrainingTypeName());
    }
}