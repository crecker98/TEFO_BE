package it.soriani.tefo.validation;

import it.soriani.tefo.entity.Users;
import it.soriani.tefo.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.soriani.tefo.constants.GenericConstants.CODE_NOT_FOUND_ERROR;
import static it.soriani.tefo.constants.GenericConstants.NOT_FOUND_ERROR;

/**
 * @author christiansoriani on 30/01/24
 * @project TEFO_BE
 */

@Service
public class UsersValidation {

    public void checkUsersList(List<Users> usersList) {
        if (usersList.isEmpty()) {
            throw NotFoundException.of(CODE_NOT_FOUND_ERROR, String.format(NOT_FOUND_ERROR, "users"));
        }
    }

}
