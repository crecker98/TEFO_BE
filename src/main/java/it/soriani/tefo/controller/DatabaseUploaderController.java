package it.soriani.tefo.controller;

import it.soriani.tefo.constants.GenericConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@RestController
@RequestMapping(GenericConstants.DATABASE_UPLOADER_CONTROLLER_PATH)
public class DatabaseUploaderController {

    //TODO: implement swagger
    @PostMapping("/upload/{tableName}")
    public ResponseEntity<?> uploadDatabaseFrom(@PathVariable final String tableName, @RequestPart("file") MultipartFile file) {
        return null;
    }

}
