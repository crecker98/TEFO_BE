package it.soriani.tefo.service;

import it.soriani.tefo.entity.Users;
import it.soriani.tefo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author christiansoriani on 24/01/24
 * @project TEFO_BE
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

}
