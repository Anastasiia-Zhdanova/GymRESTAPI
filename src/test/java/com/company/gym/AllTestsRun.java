package com.company.gym;

import com.company.gym.dao.*;
import com.company.gym.entity.*;
import com.company.gym.exception.AuthenticationExceptionTest;
import com.company.gym.exception.NotFoundExceptionTest;
import com.company.gym.exception.ValidationExceptionTest;
import com.company.gym.mapper.TraineeMapperTest;
import com.company.gym.mapper.TrainerMapperTest;
import com.company.gym.service.*;
import com.company.gym.util.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        //dao
        GenericDAOTest.class,
        TraineeDAOTest.class,
        TrainerDAOTest.class,
        TrainingDAOTest.class,
        TrainingTypeDAOTest.class,
        UserDAOTest.class,
        //service
        AuthServiceTest.class,
        //TraineeServiceTest.class,
        //TrainerServiceTest.class,
        //TrainingServiceTest.class,
        TrainingTypeServiceTest.class,
        //TraineeServiceFacadeTest.class,
        TrainerServiceFacadeTest.class,
        //entity
        TraineeTest.class,
        TrainerTest.class,
        TrainingTest.class,
        TrainingTypeTest.class,
        UserTest.class,
        //mapper
        TraineeMapperTest.class,
        TrainerMapperTest.class,
        //util
        HibernateUtilTest.class,
        PasswordUtilTest.class,
        QueryUtilTest.class,
        UserCredentialGeneratorTest.class,
        //UsernameUtilTest.class,
        //exception
        AuthenticationExceptionTest.class,
       // GlobalExceptionHandlerTest.class,
        NotFoundExceptionTest.class,
        ValidationExceptionTest.class,
        //controller
        //AuthenticationControllerTest.class,
        //TraineeControllerTest.class,
       // TrainerControllerTest.class,
        //TrainingControllerTest.class
})
public class AllTestsRun {

}