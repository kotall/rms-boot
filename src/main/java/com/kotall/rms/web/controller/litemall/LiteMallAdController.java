package com.kotall.rms.web.controller.litemall;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kotall.rms.core.annotation.SysLog;
import com.kotall.rms.web.controller.sys.AbstractController;
import com.kotall.rms.common.utils.Page;
import com.kotall.rms.common.utils.Result;
        import com.kotall.rms.web.util.ResultKit;
import com.kotall.rms.common.entity.litemall.LiteMallAdEntity;
import com.kotall.rms.core.service.litemall.LiteMallAdService;

/**
 * 广告表
 *
 * @author kotall
 * @date 2018年11月13日 下午6:08:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/litemall/ad")
public class LiteMallAdController extends AbstractController {
	
	@Autowired
	private LiteMallAdService liteMallAdService;
	
	/**
	 * 列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<LiteMallAdEntity> list(@RequestBody Map<String, Object> params) {
		return liteMallAdService.listLiteMallAd(params);
	}
		
	/**
	 * 新增
	 * @param liteMallAd
	 * @return
	 */
	@SysLog("新增广告表")
	@RequestMapping("/save")
	public Result save(@RequestBody LiteMallAdEntity liteMallAd) {
	    int count = liteMallAdService.saveLiteMallAd(liteMallAd);
		return ResultKit.msg(count);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public Result getById(@RequestBody Long id) {
		LiteMallAdEntity liteMallAd = liteMallAdService.getLiteMallAdById(id);
		return ResultKit.msg(liteMallAd);
	}
	
	/**
	 * 修改
	 * @param liteMallAd
	 * @return
	 */
	@SysLog("修改广告表")
	@RequestMapping("/update")
	public Result update(@RequestBody LiteMallAdEntity liteMallAd) {
        int count = liteMallAdService.updateLiteMallAd(liteMallAd);
		return  ResultKit.msg(count);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@SysLog("删除广告表")
	@RequestMapping("/remove")
	public Result batchRemove(@RequestBody Long[] id) {
	    int count = liteMallAdService.batchRemove(id);
		return ResultKit.msg(count);
	}
	
}
