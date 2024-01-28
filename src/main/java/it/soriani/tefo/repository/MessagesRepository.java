package it.soriani.tefo.repository;

import it.soriani.tefo.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {
}
