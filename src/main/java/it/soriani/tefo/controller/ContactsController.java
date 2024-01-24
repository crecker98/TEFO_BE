package it.soriani.tefo.controller;

import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.entity.Contacts;
import it.soriani.tefo.service.ContactsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author christiansoriani on 15/01/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + ContactsController.API_CONTEXT)
@Slf4j
public class ContactsController {

    protected static final String API_CONTEXT = "/contacts";

    private final ContactsService contactsService;

    //TODO: add swagger
    @PostMapping("/transferContacts")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Contacts> transferContacts() {
        return contactsService.saveAll();
    }

}
