package it.soriani.tefo.repository;

import it.soriani.tefo.entity.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author christiansoriani on 29/01/24
 * @project TEFO_BE
 */

@Repository
public interface ChatsRepository extends JpaRepository<Chats, Integer> {
}
