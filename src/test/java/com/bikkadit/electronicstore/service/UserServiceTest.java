package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
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

             // we have to add image in images/users/pratik.png for smooth execution
        userService.deleteUser(userId);

        Mockito.verify(userRepository,Mockito.times(1)).delete(user1);
    }

    @Test
    public void getAllUsersTest()
    {

        User user1 = User.builder()
                .name("Vikram")
                .email("Vikram@gmail.com")
                .gender("male")
                .about("I am a Software Engineer")
                .imageName("vikram.png")
                .password("Vikram@123")
                .build();

        User user2 = User.builder()
                .name("Vivek")
                .email("vivek@gmail.com")
                .gender("male")
                .about("I am a Student")
                .imageName("vivek.png")
                .password("Vivek@123")
                .build();

        List<User> userList = Arrays.asList(user,user1,user2);

        Page<User> page=new PageImpl<>(userList);

        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUsers = userService.getAllUsers(1, 10, "name", "desc");

        Assertions.assertEquals(3,page.getContent().size());
        Assertions.assertEquals(3,allUsers.getContent().size());
    }

    @Test
    public void getUserByIdTest()
    {
        Mockito.when(userRepository.findById("panu123")).thenReturn(Optional.of(user));

        String userId="panu123";
        UserDto userDto = userService.getUserById(userId);

        Assertions.assertEquals(user.getName(),userDto.getName(),"name not matched : test case failed");
        Assertions.assertNotNull(userDto);
    }

    @Test
    public void getUserByEmail()
    {
        Mockito.when(userRepository.findByEmail("pratik@gmail.com")).thenReturn(Optional.of(user));

        String userEmail="pratik@gmail.com";
        UserDto userDto = userService.getUserByEmail(userEmail);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getImageName(),userDto.getImageName(),"imageName not matched : test case failed");
    }

    @Test
    public void searchUsersTest()
    {
        User user1 = User.builder()
                .name("Vikram")
                .email("Vikram@gmail.com")
                .gender("male")
                .about("I am a Software Engineer")
                .imageName("vikram.png")
                .password("Vikram@123")
                .build();

        User user2 = User.builder()
                .name("Vivek")
                .email("vivek@gmail.com")
                .gender("male")
                .about("I am a Student")
                .imageName("vivek.png")
                .password("Vivek@123")
                .build();

        List<User> userList = List.of(user, user1, user2);

        Mockito.when(userRepository.findByNameContaining("k")).thenReturn(userList);

        String keywords="k";
        List<UserDto> userDtos = userService.searchUsers(keywords);

        Assertions.assertNotNull(userDtos);
        Assertions.assertEquals(3,userDtos.size(),"size is not same : test case failed");
    }
}
