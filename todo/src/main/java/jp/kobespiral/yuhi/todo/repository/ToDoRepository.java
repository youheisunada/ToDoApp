package jp.kobespiral.yuhi.todo.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import jp.kobespiral.yuhi.todo.entity.ToDo;
@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long>{
    List<ToDo> findAll();
    List<ToDo> findByMidAndDone(String mid ,boolean done );
    List<ToDo> findByDone(boolean done);
    List<ToDo> findByMid(String mid);
}