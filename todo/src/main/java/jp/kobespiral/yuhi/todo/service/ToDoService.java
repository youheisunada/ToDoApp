package jp.kobespiral.yuhi.todo.service;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.kobespiral.yuhi.todo.dto.ToDoForm;
import jp.kobespiral.yuhi.todo.entity.ToDo;
import jp.kobespiral.yuhi.todo.exception.ToDoAppException;
import jp.kobespiral.yuhi.todo.repository.ToDoRepository;

@Service
public class ToDoService {
   @Autowired
   ToDoRepository tRepo;
   /**
    * todoを作成
    * @param form
    * @return
    */
   public ToDo createToDo(String mid, ToDoForm form) {
       Date created_time = new Date();
       ToDo  t= form.toEntity();
       t.setMid(mid);
       t.setCreatedAt(created_time);
       return tRepo.save(t);
   }

   public ToDo doneToDo(Long seq){
       Date done_time = new Date();
       ToDo t = getToDo(seq);
       t.setDoneAt(done_time);
       t.setDone(true);
       return tRepo.save(t);
   }

   public ToDo updateToDo(Long seq, ToDoForm form){
       ToDo t = form.toEntity();
       ToDo s = tRepo.findById(seq).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, seq + ": No such member exists"));
       s.setTitle(t.getTitle());
       return tRepo.save(s);
   }

   public ToDo deleteToDo(Long seq){
       ToDo s = tRepo.findById(seq).orElseThrow(
                () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, seq + ": No such member exists"));
       tRepo.delete(s);
       return null;
   }
   
   /**
    * 番号からtodoを取得
    * @param 
    * @return
    */
   public ToDo getToDo(Long seq) {
       ToDo s = tRepo.findById(seq).orElseThrow(
                () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, seq + ": No such member exists"));
       return s;
   }
   /**
    * midのtodoリストをしゅとく
    * @return
    */

   public List<ToDo> getToDoList(String mid) {
       return tRepo.findByMidAndDone(mid, false);
   }
   /**
    * midのdoneリストを取得
    */
   public List<ToDo> getDoneList(String mid) {
       return tRepo.findByMidAndDone(mid,true );
   }
   /**
    * 全員のtodoリストを取得
    */

   public List<ToDo> getToDoList(){
       return  tRepo.findByDone(false);
   }

   public List<ToDo> getDoneList(){
       return tRepo.findByDone(true);
   }

}
