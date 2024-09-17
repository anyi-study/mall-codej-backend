package com.codej.springbootinit.service;

import com.codej.springbootinit.model.dto.coupon.CouponRequest;
import com.codej.springbootinit.model.entity.coupon.Coupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codej.springbootinit.model.vo.CouponPageVo;

import java.math.BigDecimal;

/**
* @author 10306
* @description 针对表【coupon(优惠券表)】的数据库操作Service
* @createDate 2024-09-18 05:27:34
*/
public interface CouponService extends IService<Coupon> {
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
    Coupon addCoupon(String name, Integer type, BigDecimal value, Integer total, BigDecimal minPrice, Integer startTime, Integer endTime, Integer orders);


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

    Boolean updateCoupon(Integer id, String name, Integer type, BigDecimal value, Integer total, BigDecimal minPrice, Integer startTime, Integer endTime, Integer orders);

    /**
     *  获取优惠券列表
     * @param page
     * @param limit
     * @return
     */
    CouponPageVo getSkusList(Integer page, Integer limit);

    /**
     * 删除优惠券
     * @param id
     * @return
     */
    Boolean deleteCouponById(Integer id);

    /**
     * 失效优惠券
     * @param id
     * @return
     */
    Boolean updateCouponStatus(Integer id);
}
