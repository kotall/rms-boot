package com.kotall.rms.common.dao.litemall;

import org.apache.ibatis.annotations.Mapper;

import com.kotall.rms.common.entity.litemall.LiteMallGoodsProductEntity;
import com.kotall.rms.common.dao.sys.BaseMapper;

/**
 * 商品货品表
 *
 * @author kotall
 * @date 2018年11月20日 下午12:58:29
 * @since 1.0.0
 */
@Mapper
public interface LiteMallGoodsProductMapper extends BaseMapper<LiteMallGoodsProductEntity> {
	
}