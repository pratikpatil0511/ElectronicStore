package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class UserServiceTest
{

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    User user;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void init()
    {
       user = User.builder()
                .name("Pratik")
                .email("pratik@gmail.com")
                .gender("male")
                .about("I am a Mechanical Engineer")
                .imageName("pratik.png")
                .password("Pratik@123")
                .build();
    }

    //create user
    @Test
    public void createUserTest()
    {
         Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto userDto = userService.createUser(this.modelMapper.map(user, UserDto.class));
        System.out.println(userDto.getName());

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("Pratik",userDto.getName());
    }
}
