package com.centralesupelec.osy2018.myseries.utils.factory;

import java.util.Optional;

import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.models.User;
import com.centralesupelec.osy2018.myseries.models.UserEpisode;
import com.centralesupelec.osy2018.myseries.repository.EpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.UserEpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.UserRepository;
import com.centralesupelec.osy2018.myseries.utils.exceptions.EpisodeNotFoundException;
import com.centralesupelec.osy2018.myseries.utils.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEpisodeFactory {

    private EpisodeRepository episodeRepository;

    private UserEpisodeRepository userEpisodeRepository;

    private UserRepository userRepository;

    public UserEpisodeFactory(EpisodeRepository episodeRepository, UserEpisodeRepository userEpisodeRepository, UserRepository userRepository) {
        this.episodeRepository = episodeRepository;
        this.userEpisodeRepository = userEpisodeRepository;
        this.userRepository = userRepository;
    }

    public UserEpisode createUserEpisode(long userId, long episodeId) throws EpisodeNotFoundException, UserNotFoundException {
        UserEpisode newUserEpisode = new UserEpisode();

        Optional<User> user = this.userRepository.findById(userId);
        Optional<Episode> episode = this.episodeRepository.findById(episodeId);

        if (episode.isPresent()) {
            newUserEpisode.setEpisode(episode.get());
        } else {
            throw new EpisodeNotFoundException("Episode not found with id " + episodeId);
        }

        if (user.isPresent()) {
            newUserEpisode.setUser(user.get());
        } else {
            throw new UserNotFoundException("User not found with id " + userId);
        }

        return newUserEpisode;
    }

    public UserEpisode createIfNotExistUserEpisode(long userId, long episodeId)
            throws EpisodeNotFoundException, UserNotFoundException {
        Optional<UserEpisode> userEpisode = this.userEpisodeRepository.findOneByUserIdAndEpisodeId(userId, episodeId);

        if (userEpisode.isPresent()) {
            return userEpisode.get();
        } else {
            return this.createUserEpisode(userId, episodeId);
        }
    }

    public UserEpisode updateOrCreateUserEpisode(long userId, long episodeId, Integer rate)
            throws EpisodeNotFoundException, UserNotFoundException{
        UserEpisode newUserEpisode = this.createIfNotExistUserEpisode(userId, episodeId);

        newUserEpisode.setRate(rate);

        return newUserEpisode;
    }

    public UserEpisode updateOrCreateAndSaveUserEpisode(long userId, long episodeId, Integer rate)
            throws EpisodeNotFoundException, UserNotFoundException {
        UserEpisode newUserEpisode = this.updateOrCreateUserEpisode(userId, episodeId, rate);

        this.userEpisodeRepository.save(newUserEpisode);

        return newUserEpisode;
    }

    public UserEpisode updateOrCreateUserEpisode(long userId, long episodeId, Boolean seen)
            throws EpisodeNotFoundException, UserNotFoundException {
        UserEpisode newUserEpisode = this.createIfNotExistUserEpisode(userId, episodeId);

        newUserEpisode.setSeen(seen);

        return newUserEpisode;
    }

    public UserEpisode updateOrCreateAndSaveUserEpisode(long userId, long episodeId, Boolean seen)
            throws EpisodeNotFoundException, UserNotFoundException {
        UserEpisode newUserEpisode = this.updateOrCreateUserEpisode(userId, episodeId, seen);

        this.userEpisodeRepository.save(newUserEpisode);

        return newUserEpisode;
    }

    public UserEpisode updateOrCreateUserEpisode(long userId, long episodeId, Integer rate, Boolean seen)
            throws EpisodeNotFoundException, UserNotFoundException {
        UserEpisode newUserEpisode = this.createIfNotExistUserEpisode(userId, episodeId);

        newUserEpisode.setSeen(seen);
        newUserEpisode.setRate(rate);

        return newUserEpisode;
    }

	public UserEpisode updateOrCreateAndSaveUserEpisode(Long userId, Long episodeId, Integer rate, Boolean seen) {
        UserEpisode newUserEpisode = this.updateOrCreateUserEpisode(userId, episodeId, rate, seen);

        this.userEpisodeRepository.save(newUserEpisode);

        return newUserEpisode;
    }

}
