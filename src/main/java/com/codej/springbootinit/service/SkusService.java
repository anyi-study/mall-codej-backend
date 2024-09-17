package com.codej.springbootinit.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.model.dto.skus.SkusRequest;
import com.codej.springbootinit.model.entity.skus.Skus;
import com.codej.springbootinit.model.vo.SkusPageVo;
import io.swagger.models.auth.In;

import java.util.List;

/**
* @author 10306
* @description 针对表【skus(规格表)】的数据库操作Service
* @createDate 2024-09-17 23:47:14
*/
public interface SkusService extends IService<Skus> {

    /**
     * 添加规格
     * @param skusRequest
     * @return
     */
    Skus addSkus(SkusRequest skusRequest);

    /**
     * 修改规格
     * @param skusRequest
     * @return
     */
    Boolean updateSkus(SkusRequest skusRequest,Integer id);

    /**
     * 修改规格状态
     * @param status
     * @param id
     * @return
     */
    Boolean updateSkusStatus(Integer status, Integer id);

    /**
     * 获取规格列表
     * @param page
     * @param limit
     * @return
     */
    SkusPageVo getSkusList(Integer page, Integer limit);

    /**
     * 删除规格
     * @param ids
     * @return
     */
    Boolean deleteSkusAll(List<Integer> ids);
}
