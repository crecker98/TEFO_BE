package it.soriani.tefo.repository;

import it.soriani.tefo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findAllByContact_MutualAndStatusIsNot(Integer mutual, Integer status);

    List<Users> findAllByStatusIsNot(Integer status);

}
