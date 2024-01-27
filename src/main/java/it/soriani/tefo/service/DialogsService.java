package it.soriani.tefo.service;

import it.soriani.tefo.entity.Dialogs;
import it.soriani.tefo.repository.DialogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author christiansoriani on 27/01/24
 * @project TEFO_BE
 */

@Service
@RequiredArgsConstructor
public class DialogsService {

    private final DialogsRepository dialogsRepository;

    public List<Dialogs> getAllDialogs() {
        return dialogsRepository.findAll();
    }

}
