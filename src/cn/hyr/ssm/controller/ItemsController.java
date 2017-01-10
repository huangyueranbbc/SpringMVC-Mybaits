package cn.hyr.ssm.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.hyr.ssm.controller.validation.ValidateGroup1;
import cn.hyr.ssm.controller.validation.ValidateGroup2;
import cn.hyr.ssm.exception.CustomException;
import cn.hyr.ssm.model.ItemsCustom;
import cn.hyr.ssm.model.ItemsQueryVo;
import cn.hyr.ssm.service.ItemsService;

/**
 * 商品的controller
 * 
 * @author huangyueran
 *
 */
// @RequestMapping对url进行分类管理,定义根路径，最终访问根路径+子路径
@Controller
@RequestMapping(value = "/items")
public class ItemsController {

	// 注入service
	@Autowired
	private ItemsService itemsService;

	/**
	 * 商品列表查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryItems")
	public ModelAndView queryItems(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {
		// 调用Service查找数据库，查询商品列表。
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

		// 返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		// 相当于request的setAttribut,在jsp通过itemsList取数据
		modelAndView.addObject("itemsList", itemsList);

		// 指定视图
		// 下边的路径，如果在视图解析器中配置jsp路径的前缀和jsp路径的后缀，修改为
		// modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		// 上边的路径配置可以不在程序中指定jsp路径的前缀和jsp路径的后缀
		modelAndView.setViewName("items/itemsList");

		return modelAndView;
	}

	/**
	 * 商品信息修改页面显示 返回的是逻辑视图名
	 * 
	 * @param model
	 * @param items_id
	 * @return
	 * @throws Exception
	 */
	// @RequestParam
	// value指定前段字段名。不指定时要求字段名一致。required指定参数是否必须传递,是否可以为空。defaultValue默认值。
	@RequestMapping("/editItems")
	public String editItemsUI(Model model, @RequestParam(value = "id", required = true) Integer items_id)
			throws Exception {
		// 调用service根据id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsListById(items_id);

		// 根据id没有查询到商品 跑出异常
		if (itemsCustom == null) {
			throw new CustomException("修改商品不存在");
		}

		// // 返回ModelAndView
		// ModelAndView modelAndView = new ModelAndView();
		// modelAndView.addObject("itemsCustom", itemsCustom);

		// // 修改商品页面
		// modelAndView.setViewName("items/editItems");

		// 通过形参中的model将model数据传入页面
		model.addAttribute("itemsCustom", itemsCustom);

		return "items/editItems";
	}

	// 商品信息修改提交
	// @RequestMapping("/editItemsSubmit")
	// 限制http请求方法
	// 在需要校验的pojo前添加@Validated,在需要校验的pojo后面添加BindingResult
	// bindingResult参数接收校验结果信息。它们配对参数。
	// value={ValidateGroup1.class}指定校验分组
	// @ModelAttribute指定pojo在页面回显时request域中的key。还可以将方法返回值传递到页面
	// 图片上传
	@RequestMapping(value = "/editItemsSubmit", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editItemsSubmit(Model model, HttpServletRequest request, Integer id,
			@ModelAttribute(value = "itemsCustom") @Validated(value = { ValidateGroup1.class,
					ValidateGroup2.class }) ItemsCustom itemsCustom,
			BindingResult bindingResult, MultipartFile items_pic) throws Exception {
		// 调用service更新商品信息
		ModelAndView modelAndView = new ModelAndView();

		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage());
			}
			// 将错误信息传到页面
			model.addAttribute("allErrors", errors);

			// 最简单的回显 直接将回显数据写入到model
			// model.addAttribute("itemsCustom", itemsCustom);
			// 简单类型回显
			// int id=1;
			// model.addAttribute("id", id);

			modelAndView.setViewName("items/editItems");
		} else {
			// 校验通过

			// 上传图片
			// 原始文件名称
			String originalFilename = items_pic.getOriginalFilename();
			if (items_pic != null && originalFilename != null && originalFilename.length() > 0) {
				// 存储图片的位置：物理路径
				String pic_path = "E:\\Tomcat 6.0\\upload\\";

				// 新的文件名称 防止文件名冲突
				String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

				// 新图片 创建一个文件 但是没有数据
				File newFile = new File(pic_path + newFileName);

				// 将内存中的数据写入磁盘（写入创建的文件中）
				items_pic.transferTo(newFile);

				// 将新图片名称写到itemsCustom中 (或者将文件的路径存入数据库,这里使用的图片服务器，通过名称就可以找到该文件。)
				itemsCustom.setPic(newFileName);

			}

			// 返回ModelAndView
			modelAndView = new ModelAndView();
			itemsService.updateItemsListById(id, itemsCustom);

			// modelAndView.setViewName("success");

			// 重定向 重新发起请求request 无法共享request
			// modelAndView.setViewName("redirect:queryItems.action");
			modelAndView.setViewName("forward:queryItems.action");
		}

		// 页面转发 可以共享request

		return modelAndView;

	}

	// @ModelAttribute还可以将方法返回值传递到页面
	@ModelAttribute("itemtypes")
	public Map<String, String> getItemsTypes() {
		Map<String, String> itemTypes = new HashMap<String, String>();
		itemTypes.put("101", "数码");
		itemTypes.put("102", "母婴");

		return itemTypes;
	}

	// 商品的批量删除 使用数组传递参数
	/**
	 * @category 商品的批量删除 数组的参数绑定
	 * @param items_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteItems")
	public String deleteItems(Integer[] items_id) throws Exception {
		// 调用service批量删除商品
		// ......
		for(Integer i:items_id){
			System.out.println(i); 
		}

		return "success";
	}

	// 进入批量修改商品页面
	@RequestMapping("/editItemsList")
	public ModelAndView editItemsListUI(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {
		// 调用Service查找数据库，查询商品列表。
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

		// 返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		// 相当于request的setAttribut,在jsp通过itemsList取数据
		modelAndView.addObject("itemsList", itemsList);

		// 指定视图
		// 下边的路径，如果在视图解析器中配置jsp路径的前缀和jsp路径的后缀，修改为
		// modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		// 上边的路径配置可以不在程序中指定jsp路径的前缀和jsp路径的后缀
		modelAndView.setViewName("items/editItemsList");

		return modelAndView;
	}

	// 批量修改商品提交 使用List接受多个items的商品修改信息 List存放在包装对象ItemsQueryVo中
	/**
	 * List的参数绑定传递 商品批量修改信息的接收和修改
	 * 
	 * @param itemsQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "editItemsListSubmit")
	public String editItemsListSubmit(ItemsQueryVo itemsQueryVo) throws Exception {
		// 调用service批量修改商品信息

		return "success";
	}

	// RESTful
	// 查询商品信息，输出json
	/// itemsView/{id}里边的{id}表示占位符，通过@PathVariable获取占位符中的参数，
	// 如果占位符中的名称和形参名一致，在@PathVariable可以不指定名称
	// http://localhost:8080/springmvc_mybaits_3/items/itemsView/3  ----------->这就是restful  	/user/100/orderId/50/age/15/sex/男
	@RequestMapping("/itemsView/{id}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception {

		// 调用service查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(id);

		return itemsCustom;
	}

}
