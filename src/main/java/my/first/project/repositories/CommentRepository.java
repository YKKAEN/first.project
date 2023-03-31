package my.first.project.repositories;

import my.first.project.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CommentRepository extends JpaRepository<Comments,Long> {

}

