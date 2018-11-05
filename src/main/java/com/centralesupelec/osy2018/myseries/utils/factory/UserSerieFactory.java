package com.centralesupelec.osy2018.myseries.utils.factory;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.UserSerie;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.repository.UserSerieRepository;
import com.centralesupelec.osy2018.myseries.utils.exceptions.EpisodeNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.SerieNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerieFactory {

    private SerieRepository serieRepository;

    private UserSerieRepository userSerieRepository;

    private UserRepository userRepository;

    public UserSerieFactory(SerieRepository serieRepository, UserSerieRepository userSerieRepository, UserRepository userRepository) {
        this.serieRepository = serieRepository;
        this.userSerieRepository = userSerieRepository;
        this.userRepository = userRepository;
    }

    public UserSerie createUserSerie(long userId, long serieId)
            throws EpisodeNotFoundException, UserNotFoundException {
        UserSerie newUserSerie = new UserSerie();

        Optional<User> user = this.userRepository.findById(userId);
        Optional<Serie> serie = this.serieRepository.findById(serieId);

        if (serie.isPresent()) {
            newUserSerie.setSerie(serie.get());
        } else {
            throw new SerieNotFoundException("Serie not found with id " + serieId);
        }

        if (user.isPresent()) {
            newUserSerie.setUser(user.get());
        } else {
            throw new UserNotFoundException("User not found with id " + userId);
        }

        return newUserSerie;
    }

    public UserSerie createIfNotExistUserSerie(long userId, long serieId)
            throws SerieNotFoundException, UserNotFoundException {
        Optional<UserSerie> userSerie = this.userSerieRepository.findByUserIdAndSerieId(userId, serieId);

        if (userSerie.isPresent()) {
            return userSerie.get();
        } else {
            return this.createUserSerie(userId, serieId);
        }
    }

    public UserSerie updateOrCreateUserSerie(long userId, long serieId, int rate)
            throws SerieNotFoundException, UserNotFoundException {
        UserSerie newUserSerie = this.createIfNotExistUserSerie(userId, serieId);

        newUserSerie.setRate(rate);

        return newUserSerie;
    }

    public UserSerie updateOrCreateAndSaveUserSerie(long userId, long serieId, int rate)
            throws SerieNotFoundException, UserNotFoundException {
        UserSerie newUserSerie = this.updateOrCreateUserSerie(userId, serieId, rate);

        this.userSerieRepository.save(newUserSerie);

        return newUserSerie;
    }

}
