package limmy.top.springtest.vo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Limmy
 * @date 2020/5/3 12:54 下午
 */
@Getter
@Setter
@Accessors(chain = true)
public class PublisherVO {
    @NonNull
    private String name;
    @NonNull
    private String pwd;
    private boolean clean;
}
