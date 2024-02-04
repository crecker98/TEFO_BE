package it.soriani.tefo.repository;

import it.soriani.tefo.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Page<Users> findAllByStatusIsNot(Pageable pageable, Integer status);

    Page<Users> findAll(Specification<Users> specification, Pageable pageable);

}
