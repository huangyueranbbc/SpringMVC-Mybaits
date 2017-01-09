package cn.hyr.ssm.service;

import java.util.List;

import cn.hyr.ssm.model.ItemsCustom;
import cn.hyr.ssm.model.ItemsQueryVo;

/**
 * 商品管理
 *
 */
public interface ItemsService {
	/**
	 * 商品查询列表
	 * 
	 * @param itemsQueryVo
	 * @return
	 * @throws Exception
	 */
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;

	/**
	 * 根据id查询商品信息
	 * 
	 * @param id
	 *            商品id
	 * @return
	 * @throws Exception
	 */
	public ItemsCustom findItemsListById(Integer id) throws Exception;

	/**
	 * 修改商品信息
	 * 
	 * @param id
	 *            商品id
	 * @param itemsCustom
	 * @throws Exception
	 */
	public void updateItemsListById(Integer id, ItemsCustom itemsCustom) throws Exception;

	public ItemsCustom findItemsById(Integer id)throws Exception;

}
