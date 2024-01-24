package it.soriani.tefo.service;

import it.soriani.tefo.component.SqliteComponent;
import it.soriani.tefo.entity.Contacts;
import it.soriani.tefo.error.ApplicationException;
import it.soriani.tefo.repository.ContactsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author christiansoriani on 24/01/24
 * @project TEFO_BE
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactsService {

    private final SqliteComponent sqliteComponent;
    private final ContactsRepository contactsRepository;

    public List<Contacts> saveAll() {
        List<Contacts> contactsList = readAllContactsFromSqlite();
        return contactsRepository.saveAll(contactsList);
    }

    private List<Contacts> readAllContactsFromSqlite() {
        Connection conn = null;
        Statement statement = null;
        List<Contacts> result = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(sqliteComponent.getConnection());
            statement = conn.createStatement();
            String query = "SELECT * FROM contacts";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result.add(Contacts.builder()
                        .uid(resultSet.getInt("uid"))
                        .mutual(resultSet.getInt("mutual"))
                        .build()
                );
            }
        } catch (SQLException e) {
            throw ApplicationException.of(String.valueOf(e.getErrorCode()), e.getSQLState());
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                log.error("Errore durante la chiusura dello statement", e);
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                log.error("Errore durante la chiusura della connessione", e);
            }
        }
        return result;
    }

}
