package csieReserve.controller;

import csieReserve.Repository.UserRepository;
import csieReserve.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserRepository userRepository;
    @PostMapping("/user/add")
    public void addUser(){
        User user = new User();
        userRepository.save(user);
    }

}
