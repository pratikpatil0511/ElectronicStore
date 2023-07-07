package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
       //generating unique id
        String uid = UUID.randomUUID().toString();
        userDto.setId(uid);
        User user = this.modelMapper.map(userDto, User.class);
        logger.info("Request sent to User Repository to save User details");
        this.userRepository.save(user);
        logger.info("User details saved successfully");
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        User user = this.userRepository.findById(id)
                                       .orElseThrow(() -> new RuntimeException(ApiConstant.USER_NOT_FOUND+id));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        logger.info("Request sent to User Repository to update User details");
        this.userRepository.save(user);
        logger.info("User details updated successfully");
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public void deleteUser(String id) {
        User user = this.userRepository.findById(id)
                                       .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.USER_NOT_FOUND+id));
        logger.info("Request sent to User Repository to delete User details with id:"+id);
         this.userRepository.delete(user);
         logger.info("User details deleted successfully with id:"+id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Request sent to User Repository to get all User's details");
        List<User> userList = this.userRepository.findAll();
        logger.info("All User's details fetched successfully");
        List<UserDto> userDtoList = userList.stream()
                      .map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto getUserById(String id) {
        logger.info("Request sent to User Repository to get User details with id:"+id);
        User user = this.userRepository.findById(id)
                                       .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.USER_NOT_FOUND+id));
        logger.info("User details fetched successfully with id:"+id);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Request sent to User Repository to get User details with email:"+email);
        User user = this.userRepository.findByEmail(email)
                                       .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.USER_NOT_FOUND+email));
        logger.info("User details fetched successfully with email:"+email);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUsers(String keywords) {
        logger.info("Request sent to User Repository to search User details with keywords:"+keywords);
        List<User> userList = this.userRepository.findByNameContaining(keywords);
        logger.info("User details fetched successfully with keywords:"+keywords);
        List<UserDto> userDtoList = userList.stream()
                      .map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }
}
