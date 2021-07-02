package jp.kobespiral.yuhi.todo.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jp.kobespiral.yuhi.todo.dto.ToDoForm;
import jp.kobespiral.yuhi.todo.entity.Member;
import jp.kobespiral.yuhi.todo.entity.ToDo;
import jp.kobespiral.yuhi.todo.service.MemberService;
import jp.kobespiral.yuhi.todo.service.ToDoService;

@Controller
public class ToDoController {
   @Autowired
   ToDoService tService;
   @Autowired
   MemberService mService;
   /**
    * todo ログイン 画面の表示
    * @return　ログイン画面
    */
   @GetMapping("/")
   String ShowLogIn(Model model) {
       String mid = new String();
       model.addAttribute("ID", mid);
       return "index";
   }
   /**
    * todo ログインのIDを受け付け5
    * @param  mid 
    * @return todo
    */
   @PostMapping("/login")
    String LogIn(@RequestParam("ID") String mid,  Model model,RedirectAttributes redirectAttributes ) {
        Member m = mService.getMember(mid);
        model.addAttribute("ID",m.getMid());
        redirectAttributes.addAttribute("mid",mid);
        return "redirect:/{mid}/todo";
   }

   /**
    * @param mid
    * @param form
    * @return
    */
    @GetMapping("/{mid}/todo")
     String ShowToDoList(@PathVariable String mid, Model model){
        List<ToDo> todolist = tService.getToDoList(mid);
        List<ToDo> donelist = tService.getDoneList(mid);
        model.addAttribute("ToDoList",todolist);
        model.addAttribute("DoneList",donelist);
        ToDoForm tdform = new ToDoForm();
        model.addAttribute("form", tdform);
        model.addAttribute("ID", mid);
        return "list";
     }

   /**
    * todo追加
    * @param mid
    * @param model
    * @return
    */

   @PostMapping("/{mid}/todo/create")
   String CreateToDoList(@ModelAttribute(name = "form") ToDoForm tdf, @PathVariable String mid, Model model){
       model.addAttribute("form", tdf);
       tService.createToDo(mid, tdf); 
       model.addAttribute("ID", mid);
    
       List<ToDo> todolist = tService.getToDoList(mid);
       List<ToDo> donelist = tService.getDoneList(mid);    
       model.addAttribute("ToDoList", todolist);
       model.addAttribute("DoneList", donelist);

       ToDoForm tdform = new ToDoForm();
       model.addAttribute("form", tdform);
       return "list";
   }


   /**
    * 完了
    * @param form
    * @param model
    * @return
    */


    @GetMapping("/{mid}/{seq}/todo/done")
    String ToDoDone(@PathVariable Long seq,@PathVariable String mid, Model model){
        model.addAttribute("ID", mid);
        tService.doneToDo(seq);
        List<ToDo> todolist = tService.getToDoList(mid);
        List<ToDo> donelist = tService.getDoneList(mid);    
        model.addAttribute("ToDoList", todolist);
        model.addAttribute("DoneList", donelist);
        ToDoForm tdform = new ToDoForm();
        model.addAttribute("form", tdform);
        return "list";
    }



    /**
     * みんなのtodoへ以降用　
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/member/todo")
    String ShowAllList(Model model){
       List<ToDo> Alltodolist = tService.getToDoList();
       List<ToDo> Alldonelist = tService.getDoneList();
       model.addAttribute("AllToDoList",Alltodolist);
       model.addAttribute("AllDoneList",Alldonelist);
       return "allist";
    }

}