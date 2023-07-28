package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


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

    @Test
    public void updateUserTest()
    {
        /*
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));

        UserDto userDto = userService.updateUser("adfvcb", (this.modelMapper.map(user, UserDto.class)));

        System.out.println(userDto.getAbout());
        Assertions.assertNotNull(userDto);
        */

        UserDto userDto = UserDto.builder()
                .name("Pratik Patil")
                .email("pratik@gmail.com")
                .gender("male")
                .about("I am a Software Engineer")
                .imageName("pratik.png")
                .password("Pratik@123")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updated = userService.updateUser("abcdxyz", userDto);
        System.out.println(updated.getName());
        Assertions.assertNotNull(updated);
        Assertions.assertEquals("I am a Software Engineer",updated.getAbout());

        Assertions.assertEquals(userDto.getName(),updated.getName(),"test cast failed : not updated");
    }

    @SneakyThrows
    @Test
    public void deleteUserTest()
    {
        User user1 = User.builder()
                .name("Pratik")
                .email("pratik@gmail.com")
                .gender("male")
                .about("I am a Mechanical Engineer")
                .imageName("pratik.png")
                .password("Pratik@123")
                .build();

        String userId="abcd";

        Mockito.when(userRepository.findById("abcd")).thenReturn(Optional.of(user1));

        userService.deleteUser(userId);

        Mockito.verify(userRepository,Mockito.times(1)).delete(user1);
    }
}
