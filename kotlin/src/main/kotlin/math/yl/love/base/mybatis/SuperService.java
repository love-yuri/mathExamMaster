package math.yl.love.base.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuperService<Entity extends BaseEntity, Mapper extends BaseMapper<Entity>> {

    @Autowired
    protected SuperManager<Mapper, Entity> baseManager;
}
