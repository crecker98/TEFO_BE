package it.soriani.tefo.service;

import it.soriani.tefo.entity.Contacts;
import it.soriani.tefo.repository.ContactsRepository;
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
public class ContactsService {

    private final ContactsRepository contactsRepository;

    public List<Contacts> getAllContacts() {
        return contactsRepository.findAll();
    }

}
