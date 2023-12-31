package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.PageHelper;
import com.bikkadit.electronicstore.helper.PageableResponse;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Request sent to User Repository to save User details");
       //generating unique id
        String uid = UUID.randomUUID().toString();
        userDto.setId(uid);
        User user = this.modelMapper.map(userDto, User.class);
        User savedUser = this.userRepository.save(user);
        logger.info("User details saved successfully");
        return this.modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        logger.info("Request sent to User Repository to update User details");
        User user = this.userRepository.findById(id)
                                       .orElseThrow(() -> new RuntimeException(ApiConstant.USER_NOT_FOUND+id));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        User updatedUser = this.userRepository.save(user);
        logger.info("User details updated successfully");
        return this.modelMapper.map(updatedUser,UserDto.class);
    }

    @Override
    public void deleteUser(String id) {
        logger.info("Request sent to User Repository to delete User details with id:{}"+id);
        User user = this.userRepository.findById(id)
                                       .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.USER_NOT_FOUND+id));
        String fullPath = imagePath + user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
            logger.info("Image deleted successfully : {}",user.getImageName());
        }
        catch(NoSuchFileException ex)
        {
            logger.info("Image not found in folder : {}",user.getImageName());
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.userRepository.delete(user);
        logger.info("User details deleted successfully with id:{}"+id);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Request sent to User Repository to get all User's details");
        Sort sort;
        if(sortDir.equalsIgnoreCase("desc"))
        {
            sort = Sort.by(sortBy).descending();
        }
        else
        {
            sort=Sort.by(sortBy).ascending();
        }
        Pageable pageable= PageRequest.of(pageNumber-1,pageSize,sort); //byDefault first pageNo is 0
        Page<User> page = this.userRepository.findAll(pageable);
        logger.info("All User's details fetched successfully");

        PageableResponse<UserDto> pageableResponse = PageHelper.getPageableResponse(page, UserDto.class);
        return pageableResponse;
    }

    @Override
    public UserDto getUserById(String id) {
        logger.info("Request sent to User Repository to get User details with id:{}"+id);
        User user = this.userRepository.findById(id)
                                       .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.USER_NOT_FOUND+id));
        logger.info("User details fetched successfully with id:{}"+id);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Request sent to User Repository to get User details with email:{}"+email);
        User user = this.userRepository.findByEmail(email)
                                       .orElseThrow(() -> new ResourceNotFoundException(ApiConstant.USER_NOT_FOUND+email));
        logger.info("User details fetched successfully with email:{}"+email);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUsers(String keywords) {
        logger.info("Request sent to User Repository to search User details with keywords:{}"+keywords);
        List<User> userList = this.userRepository.findByNameContaining(keywords);
        logger.info("User details fetched successfully with keywords:{}"+keywords);
        List<UserDto> userDtoList = userList.stream()
                      .map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }
}
