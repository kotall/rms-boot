package com.kotall.rms.web.controller.sys;

import com.kotall.rms.common.entity.sys.SysMenuEntity;
import com.kotall.rms.common.utils.Result;
import com.kotall.rms.core.annotation.SysLog;
import com.kotall.rms.core.service.sys.SysMenuService;
import com.kotall.rms.web.util.ResultKit;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单controller
 *
 * @author aracwong
 * @date 2017年8月10日 上午12:23:44
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	@Resource
	private SysMenuService sysMenuService;
	
	/**
	 * 用户菜单
	 * @return
	 */
	@RequestMapping("/user")
	public Result user(){
		List<SysMenuEntity> menuList = sysMenuService.listUserMenu(getUserId());
		return Result.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public List<SysMenuEntity> listMenu(@RequestParam Map<String, Object> params) {
		// 数据权限控制
		return sysMenuService.queryByList(params);
	}
	
	/**
	 * 选择菜单(添加、修改)
	 * @return
	 */
	@RequestMapping("/select")
	public Result select() {
		List<SysMenuEntity> menuList = sysMenuService.listNotButton();
		return ResultKit.msgNotCheckNull(menuList);
	}
	
	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 */
	@SysLog("新增菜单")
	@RequestMapping("/save")
	public Result save(@RequestBody SysMenuEntity menu) {
		boolean count = sysMenuService.save(menu);
		return ResultKit.msg(count);
	}

	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public Result info(@RequestBody Integer id) {
		SysMenuEntity menu = sysMenuService.getById(id);
		return ResultKit.msg(menu);
	}
	
	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
	@SysLog("修改菜单")
	@RequestMapping("/update")
	public Result update(@RequestBody SysMenuEntity menu) {
		boolean count = sysMenuService.update(menu);
		return ResultKit.msg(count);
	}
	
	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	@SysLog("删除菜单")
	@RequestMapping("/remove")
	public Result remove(@RequestBody Integer[] id) {
		int count = sysMenuService.batchRemove(id);
		return ResultKit.msg(id, count);
	}
	
}
