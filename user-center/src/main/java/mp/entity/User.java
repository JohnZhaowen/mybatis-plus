package mp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName(value = "mp_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    @TableId(value = "id")
    private Long userId;

    @TableField("name")
    private String realName;

    private Integer age;

    private String email;

    private Long managerId;

    @TableField(value ="`date`")
    private LocalDateTime date;

    @TableField(exist = false)
    private transient String remark;

    private LocalDateTime createTime;
}
