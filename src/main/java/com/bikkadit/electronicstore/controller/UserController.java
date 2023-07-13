package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constant.ApiConstant;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.helper.ApiResponse;
import com.bikkadit.electronicstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    /**
     * @author Pratik Patil[P0511]
     * @apiNote this api is for save new user details
     * @param userDto
     * @return user
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        logger.info("Initiated request for save User details");
        UserDto savedUser = this.userService.createUser(userDto);
        logger.info("Completed request for save User details");
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for update user details
     * @param id
     * @param userDto
     * @return user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser( @PathVariable String id,@Valid @RequestBody UserDto userDto)
    {
        logger.info("Initiated request for update User details with id:{}",id);
        UserDto updatedUser = this.userService.updateUser(id, userDto);
        logger.info("Completed request for update User details with id:{}",id);
        return new ResponseEntity<>(updatedUser,HttpStatus.CREATED);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for delete user details by using id
     * @param id
     * @return message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String id)
    {
        logger.info("Initiated request for delete User details with id:{}",id);
        this.userService.deleteUser(id);
        logger.info("Completed request for delete User details with id:{}",id);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .message(ApiConstant.DELETE_USER+id)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get all user's details
     * @return List of all users details
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(value="pageNumber",defaultValue =ApiConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue =ApiConstant.PAGE_SIZE,required = false) int pageSize
    )
    {
        logger.info("Initiated request for get all User's details");
        List<UserDto> allUsers = this.userService.getAllUsers(pageNumber,pageSize);
        logger.info("Completed request for get all User's details");
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get user details by id
     * @param id
     * @return user
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id)
    {
        logger.info("Initiated request for get User details with id:{}",id);
        UserDto userById = this.userService.getUserById(id);
        logger.info("Completed request for get User details with id:{}",id);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for get user by email
     * @param email
     * @return user
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        logger.info("Initiated request for get User details with email:{}",email);
        UserDto userByEmail = this.userService.getUserByEmail(email);
        logger.info("Completed request for get User details with email:{}",email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }

    /**
     * @author Pratik Patil[P0511]
     * @apiNote This api is for search user by keywords
     * @param keyword
     * @return List of user details
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getUsersByNameContaining(@PathVariable String keyword)
    {
        logger.info("Initiated request for search User details with keyword:{}",keyword);
        List<UserDto> userDtos = this.userService.searchUsers(keyword);
        logger.info("Completed request for search User details with keyword:{}",keyword);
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }
}
