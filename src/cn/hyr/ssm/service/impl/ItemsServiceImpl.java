package cn.hyr.ssm.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hyr.ssm.exception.CustomException;
import cn.hyr.ssm.mapper.ItemsMapper;
import cn.hyr.ssm.mapper.ItemsMapperCustom;
import cn.hyr.ssm.model.Items;
import cn.hyr.ssm.model.ItemsCustom;
import cn.hyr.ssm.model.ItemsQueryVo;
import cn.hyr.ssm.service.ItemsService;

public class ItemsServiceImpl implements ItemsService {

	// 注入mapper 相当于DAO层
	@Autowired
	private ItemsMapperCustom itemsMapperCustom;

	@Autowired
	private ItemsMapper itemsMapper;

	// 商品查询列表
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {
		// 通过ItemsMapperCustom查询数据库
		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsListById(Integer id) throws Exception {
		Items items = itemsMapper.selectByPrimaryKey(id);

		if (items == null) {
			throw new CustomException("修改的商品信息不存在!");
		}

		// 进行业务处理
		// 比如json的封装 判断是否过去 将过去数据存入ItemsCustom的自定义属性中

		ItemsCustom itemsCustom = null;

		if (items != null) {
			itemsCustom = new ItemsCustom();
			// 拷贝
			BeanUtils.copyProperties(items, itemsCustom);
		}

		return itemsCustom;
	}

	@Override
	public void updateItemsListById(Integer id, ItemsCustom itemsCustom) throws Exception {
		// 添加业务校验,通常在service层对关键参数进行校验
		// 校验id是否为空

		// updateByPrimaryKeyWithBLOBs可以更新所有字段，包括大文本字段。要求必须传入id
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}
	
	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		
		Items items = itemsMapper.selectByPrimaryKey(id);
		if(items==null){
			throw new CustomException("修改的商品信息不存在!");
		}
		//中间对商品信息进行业务处理
		//....
		//返回ItemsCustom
		ItemsCustom itemsCustom = null;
		//将items的属性值拷贝到itemsCustom
		if(items!=null){
			itemsCustom = new ItemsCustom();
			BeanUtils.copyProperties(items, itemsCustom);
		}
		
		
		return itemsCustom;
		
	}

}
