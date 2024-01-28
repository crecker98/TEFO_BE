package it.soriani.tefo.service;

import it.soriani.tefo.entity.Messages;
import it.soriani.tefo.repository.MessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Service
@RequiredArgsConstructor
public class MessagesService {

    private final MessagesRepository messagesRepository;

    public List<Messages> getAllMessages() {
        return messagesRepository.findAll();
    }

}
