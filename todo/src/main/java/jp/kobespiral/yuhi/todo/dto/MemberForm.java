package jp.kobespiral.yuhi.todo.dto;
import jp.kobespiral.yuhi.todo.entity.Member;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MemberForm {
    @Pattern(regexp ="[a-z0-9_\\-]{4,16}")
    String mid; //メンバーID．
    @NotBlank
    @Size(min = 1,max=32)
    String name; //名

    public Member toEntity() {
        Member m = new Member(mid, name);
        return m;
    }
}