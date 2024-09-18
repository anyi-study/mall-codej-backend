package com.codej.springbootinit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codej.springbootinit.model.dto.coupon.CouponRequest;
import com.codej.springbootinit.model.entity.coupon.Coupon;
import com.codej.springbootinit.model.entity.skus.Skus;
import com.codej.springbootinit.model.vo.CouponPageVo;
import com.codej.springbootinit.model.vo.SkusPageVo;
import com.codej.springbootinit.service.CouponService;
import com.codej.springbootinit.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
* @author 10306
* @description 针对表【coupon(优惠券表)】的数据库操作Service实现
* @createDate 2024-09-18 05:27:34
*/
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon>
    implements CouponService{

    @Autowired
    private CouponMapper couponMapper;

    /**
     * 添加优惠券
     * @param name
     * @param type
     * @param value
     * @param total
     * @param minPrice
     * @param startTime
     * @param endTime
     * @param orders
     * @return
     */
    @Override
    public Coupon addCoupon(String name, Integer type, BigDecimal value, Integer total, BigDecimal minPrice, Integer startTime, Integer endTime, Integer orders) {
        Coupon coupon = new Coupon();
        coupon.setName(name);
        coupon.setType(type);
        coupon.setValue(value);
        coupon.setTotal(total);
        coupon.setMinPrice(minPrice);
        coupon.setStartTime(startTime);
        coupon.setEndTime(endTime);
        coupon.setOrders(orders);
        coupon.setCreateTime(System.currentTimeMillis());
        coupon.setUpdateTime(System.currentTimeMillis());
//        插入并返回插入的对象
        int insert = couponMapper.insert(coupon);
        if (insert > 0) {
            return coupon;
        }
        return null;
    }

    /**
     * 修改优惠券
     * @param id
     * @param name
     * @param type
     * @param value
     * @param total
     * @param minPrice
     * @param startTime
     * @param endTime
     * @param orders
     * @return
     */
    @Override
    public Boolean updateCoupon(Integer id, String name, Integer type, BigDecimal value, Integer total, BigDecimal minPrice, Integer startTime, Integer endTime, Integer orders) {
        Coupon coupon = couponMapper.selectById(id);
        coupon.setName(name);
        coupon.setType(type);
        coupon.setValue(value);
        coupon.setTotal(total);
        coupon.setMinPrice(minPrice);
        coupon.setStartTime(startTime);
        coupon.setEndTime(endTime);
        coupon.setOrders(orders);
        coupon.setUpdateTime(System.currentTimeMillis());
        int update = couponMapper.updateById(coupon);
        if (update > 0) {
            return true;
        }
        return false;
    }

    @Override
    public CouponPageVo getUserLevelList(Integer page, Integer limit) {
        // 调用MyBatis-Plus进行分页查询
        // 示例代码，具体实现需要根据实际数据库和业务逻辑调整
        Page<Coupon> couponPage = new Page<>(page, limit);
        LambdaQueryWrapper<Coupon> queryWrapper = new LambdaQueryWrapper<>();
        IPage<Coupon> result = couponMapper.selectPage(couponPage, queryWrapper);
        // 返回分页数据
        return new CouponPageVo(result.getRecords(), result.getTotal());
    }

    /**
     * 删除优惠券
     * @param id
     * @return
     */
    @Override
    public Boolean deleteCouponById(Integer id) {
        return couponMapper.deleteById(id) > 0;
    }

    /**
     * 修改优惠券状态(失效优惠券)
     * @param id
     * @return
     */
    @Override
    public Boolean updateCouponStatus(Integer id) {
        Coupon coupon = couponMapper.selectById(id);
        coupon.setStatus(0);
        int i = couponMapper.updateById(coupon);
        if (i > 0) {
            return true;
        }
        return false;
    }
}




