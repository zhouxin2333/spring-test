package cool.zhouxin.multiple.datasource.different.entity.separate.database.api.entity.db2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhouxin
 * @since 2020/6/3 10:55
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class TableDB2 {

    @Id
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;
}
