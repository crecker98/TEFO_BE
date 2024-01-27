package it.soriani.tefo.repository;

import it.soriani.tefo.entity.Dialogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author christiansoriani on 27/01/24
 * @project TEFO_BE
 */

@Repository
public interface DialogsRepository extends JpaRepository<Dialogs, Integer> {
}
