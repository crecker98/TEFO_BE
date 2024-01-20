package it.soriani.tefo.controller;

import it.soriani.tefo.constants.GenericConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@RestController
@RequestMapping(GenericConstants.DATABASE_UPLOADER_CONTROLLER_PATH)
public class DatabaseUploaderController {

    @PostMapping("/upload/{tableName}")
    public ResponseEntity<?> uploadDatabaseFrom() {
        return null;
    }

}
