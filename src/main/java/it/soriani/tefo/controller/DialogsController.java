package it.soriani.tefo.controller;

import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.entity.Dialogs;
import it.soriani.tefo.service.DialogsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author christiansoriani on 27/01/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + DialogsController.API_CONTEXT)
@Slf4j
public class DialogsController {

    protected static final String API_CONTEXT = "/dialogs";

    private final DialogsService dialogsService;

    public ResponseEntity<?> getAllDialogs() {
        final List<Dialogs> dialogs = dialogsService.getAllDialogs();
        return ResponseEntity.ok(dialogsService.getAllDialogs());
    }

}
