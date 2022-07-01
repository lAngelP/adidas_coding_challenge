package net.langelp.subscriptionapi.controller;

import net.langelp.subscriptionapi.controller.dto.CreateUserRequestDto;
import net.langelp.subscriptionapi.controller.dto.UserDto;
import net.langelp.subscriptionapi.exception.EntityAlreadyFoundException;
import net.langelp.subscriptionapi.exception.EntityNotFoundException;
import net.langelp.subscriptionapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private void checkString(String str){
        if(Objects.isNull(str) || str.isEmpty()){
            throw new IllegalArgumentException("Null or empty");
        }
    }

    private void checkObject(Object obj){
        if(Objects.isNull(obj)){
            throw new IllegalArgumentException("Null object");
        }
    }

    private void checkNumberNotNegative(Integer number){
        if(Objects.isNull(number) || number <= 0){
            throw new IllegalArgumentException("Null or negative number");
        }
    }

    @PutMapping("/{email}")
    public UserDto addUser(@PathVariable String email, @RequestBody CreateUserRequestDto createUserRequestDto) throws EntityAlreadyFoundException {
        checkString(email);
        checkObject(createUserRequestDto.getBirthDate());
        checkObject(createUserRequestDto.getRgpdConsent());
        return this.userService.addUser(email, createUserRequestDto);
    }

    @DeleteMapping("/{email}/subscription/{campaignId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void cancelSubscription(@PathVariable String email, @PathVariable Long campaignId) throws EntityNotFoundException {
        checkString(email);
        this.userService.cancelSubscription(email, campaignId);
    }

    @PutMapping("/{email}/subscription/{campaignId}")
    public void addSubscription(@PathVariable String email, @PathVariable Long campaignId) throws EntityNotFoundException {
        checkString(email);
        this.userService.addSubscription(email, campaignId);
    }


    @GetMapping("/{email}/subscription/{campaignId}")
    public UserDto getSubscription(@PathVariable String email, @PathVariable Long campaignId) throws EntityNotFoundException {
        checkString(email);
        return this.userService.getSubscription(email, campaignId);
    }

    @GetMapping()
    public List<UserDto> getSubscriptions(@RequestParam Integer page, @RequestParam Integer size){
        checkNumberNotNegative(page);
        checkNumberNotNegative(size);
        return this.userService.getSubscriptions(page-1, size);
    }


}
