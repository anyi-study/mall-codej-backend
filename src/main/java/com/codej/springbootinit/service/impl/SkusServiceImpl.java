package com.codej.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.model.dto.skus.SkusRequest;
import com.codej.springbootinit.model.entity.Manager;
import com.codej.springbootinit.model.entity.Role;
import com.codej.springbootinit.model.entity.skus.Skus;
import com.codej.springbootinit.model.vo.ManagerPageVo;
import com.codej.springbootinit.model.vo.SkusPageVo;
import com.codej.springbootinit.service.SkusService;
import com.codej.springbootinit.mapper.SkusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 10306
* @description 针对表【skus(规格表)】的数据库操作Service实现
* @createDate 2024-09-17 23:47:14
*/
@Service
public class SkusServiceImpl extends ServiceImpl<SkusMapper, Skus>
    implements SkusService{
    @Autowired
    private SkusMapper skusMapper;

    @Override
    public Skus addSkus(SkusRequest skusRequest) {
        Skus skus = new Skus();
        skus.setCreateTime(System.currentTimeMillis());
        skus.setUpdateTime(System.currentTimeMillis());
        skus.setName(skusRequest.getName());
        skus.setType(0);
        skus.setStatus(skusRequest.getStatus());
        skus.setOrders(skusRequest.getOrders());
        skus.setDefaults(skusRequest.getDefaults());
        int insert = skusMapper.insert(skus);
        if (insert <= 0) {
            return null;
        }
        return skus;
    }

    /**
     * 修改规格
     * @param skusRequest
     * @return
     */
    @Override
    public Boolean updateSkus(SkusRequest skusRequest,Integer id) {
        Skus skus = new Skus();
        skus.setId(id);
        skus.setUpdateTime(System.currentTimeMillis());
        skus.setName(skusRequest.getName());
        skus.setType(0);
        skus.setStatus(skusRequest.getStatus());
        skus.setOrders(skusRequest.getOrders());
        skus.setDefaults(skusRequest.getDefaults());
        int update = skusMapper.updateById(skus);
        return update > 0;
    }

    /**
     * 修改规格状态
     * @param status
     * @param id
     * @return
     */
    @Override
    public Boolean updateSkusStatus(Integer status, Integer id) {
        Skus skus = skusMapper.selectById(id);
        skus.setStatus(status);
        return skusMapper.updateById(skus) > 0;
    }

    /**
     * 获取规格列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public SkusPageVo getSkusList(Integer page, Integer limit) {
        // 调用MyBatis-Plus进行分页查询
        // 示例代码，具体实现需要根据实际数据库和业务逻辑调整
        Page<Skus> managerPage = new Page<>(page, limit);
        LambdaQueryWrapper<Skus> queryWrapper = new LambdaQueryWrapper<>();
        IPage<Skus> result = skusMapper.selectPage(managerPage, queryWrapper);
        // 返回分页数据
        return new SkusPageVo(result.getRecords(), result.getTotal());
    }

    /**
     * 批量删除规格
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteSkusAll(List<Integer> ids) {
//        查询ids有没有不存在于数据库的
        for (Integer id : ids) {
            Skus skus = skusMapper.selectById(id);
            if (skus == null) {
                return false;
            }
        }
        return skusMapper.deleteBatchIds(ids) > 0;
    }
}




