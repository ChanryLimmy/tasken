package limmy.top.springtest.vo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author Limmy
 * @date 2020/5/3 12:57 下午
 */
@Getter
@Setter
public class TaskVO {
    @NonNull
    private String pname;
    @NonNull
    private String ppwd;
    @NonNull
    private String name;

    private String msg = "";

    private String state;
}
