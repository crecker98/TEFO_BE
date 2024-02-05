package it.soriani.tefo.repository;

import it.soriani.tefo.entity.EncChats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author christiansoriani on 05/02/24
 * @project TEFO_BE
 */

@Repository
public interface EncChatsRepository extends JpaRepository<EncChats, Long> {
}
