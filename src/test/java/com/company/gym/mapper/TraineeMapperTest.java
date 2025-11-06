package com.company.gym.mapper;

import com.company.gym.dto.response.TraineeProfileResponse;
import com.company.gym.dto.response.TraineeShortResponse;
import com.company.gym.dto.response.TrainerShortResponse;
import com.company.gym.dto.response.TrainingListResponse;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeMapperTest {

    @Mock
    private TrainerMapper trainerMapper; // Mock the dependency

    @InjectMocks
    private TraineeMapper traineeMapper = Mappers.getMapper(TraineeMapper.class);

    private Trainee mockTrainee;
    private Trainer mockTrainer;
    private Training mockTraining;

    @BeforeEach
    void setUp() {
        // --- Entities Setup ---
        User user = new User();
        user.setUsername("client.user");
        user.setFirstName("Client");
        user.setLastName("User");
        user.setIsActive(true);

        mockTrainee = new Trainee();
        mockTrainee.setUser(user);
        mockTrainee.setDateOfBirth(new Date());
        mockTrainee.setAddress("Test Address");
        mockTrainee.setTrainers(new HashSet<>());
        mockTrainee.setTrainings(new HashSet<>());

        User trainerUser = new User();
        trainerUser.setFirstName("Coach");
        trainerUser.setLastName("T");
        mockTrainer = new Trainer();
        mockTrainer.setUser(trainerUser);

        TrainingType type = new TrainingType("Cardio");
        mockTraining = new Training();
        mockTraining.setTrainingName("Run");
        mockTraining.setTrainingDate(new Date());
        mockTraining.setTrainingDuration(60);
        mockTraining.setTrainingType(type);
        mockTraining.setTrainer(mockTrainer);
    }

    @Test
    void toTraineeProfileResponse_Success() {
        mockTrainee.getTrainers().add(mockTrainer);
        TrainerShortResponse mockShortResponse = new TrainerShortResponse();
        when(trainerMapper.toTrainerShortResponse(mockTrainer)).thenReturn(mockShortResponse);

        TraineeProfileResponse response = traineeMapper.toTraineeProfileResponse(mockTrainee);

        assertNotNull(response);
        assertEquals("client.user", response.getUsername());
        assertEquals("Client", response.getFirstName());
        assertTrue(response.getIsActive());
        assertEquals(1, response.getTrainersList().size());
        assertTrue(response.getTrainersList().contains(mockShortResponse));
    }

    @Test
    void toTraineeProfileResponse_NullTrainers() {
        mockTrainee.setTrainers(null);

        TraineeProfileResponse response = traineeMapper.toTraineeProfileResponse(mockTrainee);

        assertNotNull(response.getTrainersList());
        assertTrue(response.getTrainersList().isEmpty());
    }

    @Test
    void toTraineeShortResponse_Success() {
        TraineeShortResponse response = traineeMapper.toTraineeShortResponse(mockTrainee);

        assertNotNull(response);
        assertEquals("client.user", response.getUsername());
        assertEquals("Client", response.getFirstName());
    }

    @Test
    void toTrainingListResponse_Success() {
        TrainingListResponse response = traineeMapper.toTrainingListResponse(mockTraining);

        assertNotNull(response);
        assertEquals("Run", response.getTrainingName());
        assertEquals("Cardio", response.getTrainingType());
        assertEquals("Coach T", response.getAssociatedUserName());
    }

    @Test
    void toTrainingListResponseList_Success() {
        List<Training> trainings = List.of(mockTraining);
        List<TrainingListResponse> responses = traineeMapper.toTrainingListResponse(trainings);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Run", responses.get(0).getTrainingName());
    }

    @Test
    void toTrainingListResponseList_NullInput() {
        assertNull(traineeMapper.toTrainingListResponse((List<Training>) null));
    }
}