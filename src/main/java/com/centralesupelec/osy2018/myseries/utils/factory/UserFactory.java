package com.centralesupelec.osy2018.myseries.utils.factory;

import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.dto.ManagedUserVM;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.utils.exceptions.EmailAlreadyUsedException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.LoginAlreadyUsedException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserFactory {

    private UserRepository userRepository;

    public UserFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static User createUser(ManagedUserVM managedUserVM) {
        User newUser = new User();
        newUser.setLogin(managedUserVM.getLogin());
        newUser.setLastName(managedUserVM.getLastName());
        newUser.setFirstName(managedUserVM.getFirstName());
        newUser.setBirthday(managedUserVM.getBirthday());
        newUser.setPassword(managedUserVM.getPassword());
        newUser.setEmail(managedUserVM.getEmail());
        newUser.setDescription(managedUserVM.getDescription());
        newUser.setDateCreation(ZonedDateTime.now());

        return newUser;
    }

    public User createAndSaveUser(ManagedUserVM managedUserVM) throws LoginAlreadyUsedException, EmailAlreadyUsedException {
        if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        }

        User newUser = UserFactory.createUser(managedUserVM);

        this.userRepository.save(newUser);

        return newUser;
    }
}
