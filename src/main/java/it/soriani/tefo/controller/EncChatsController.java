package it.soriani.tefo.controller;

import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.entity.EncChats;
import it.soriani.tefo.repository.EncChatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author christiansoriani on 05/02/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + EncChatsController.API_CONTEXT)
@Slf4j
public class EncChatsController {

    protected static final String API_CONTEXT = "/encChats";
    private final EncChatsRepository encChatsRepository;

    @PostMapping("/allEncChats")
    public List<EncChats> getAllEncChats() {
        return encChatsRepository.findAll();
    }

}
