package cn.hyr.ssm.mapper;

import java.util.List;

import cn.hyr.ssm.model.ItemsCustom;
import cn.hyr.ssm.model.ItemsQueryVo;

public interface ItemsMapperCustom {

	/**
	 * @category 商品查询列表
	 * @param itemsQueryVo
	 * @return
	 * @throws Exception
	 */
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
}