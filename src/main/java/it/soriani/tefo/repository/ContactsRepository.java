package it.soriani.tefo.repository;

import it.soriani.tefo.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Integer> {
}
