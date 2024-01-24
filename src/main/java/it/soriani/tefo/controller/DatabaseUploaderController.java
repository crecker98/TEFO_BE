package it.soriani.tefo.controller;

import it.soriani.tefo.component.SqliteComponent;
import it.soriani.tefo.constants.GenericConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + DatabaseUploaderController.API_CONTEXT)
@Slf4j
public class DatabaseUploaderController {

    protected static final String API_CONTEXT = "/database-uploader";

    private final SqliteComponent sqliteComponent;


    //TODO: implement swagger and AOP
    @PostMapping("/uploadDB/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> uploadDatabase(@RequestPart("file") MultipartFile file) throws IOException, SQLException {
        sqliteComponent.getUserUploadedFile(file);
        return ResponseEntity.ok().build();
    }

}
