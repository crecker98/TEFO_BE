package it.soriani.tefo.component;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

/**
 * @author christiansoriani on 24/01/24
 * @project TEFO_BE
 */

@Component
@Getter
public class SqliteComponent {

    private String connection;

    public void getUserUploadedFile(@NotNull MultipartFile file) throws IOException, SQLException {
        Path tempFile = Files.createTempFile("temp", ".db");
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        connection = String.format("jdbc:sqlite:%s", tempFile);
    }

}
