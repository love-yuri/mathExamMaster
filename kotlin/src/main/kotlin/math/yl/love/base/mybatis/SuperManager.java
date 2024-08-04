package math.yl.love.base.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuperManager<Mapper extends BaseMapper<Entity>, Entity> extends ServiceImpl<Mapper , Entity> implements IService<Entity> {

    @Autowired
    private Mapper baseMapper;
}
