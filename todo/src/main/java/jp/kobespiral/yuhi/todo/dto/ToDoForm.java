package jp.kobespiral.yuhi.todo.dto;
import jp.kobespiral.yuhi.todo.entity.ToDo;
import lombok.Data;
@Data

public class ToDoForm {
    String title; //todoタイトル
    public ToDo toEntity() {
        ToDo t = new ToDo(null, title, null, false, null, null);
        return t;
    }
}