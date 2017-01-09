package cn.hyr.ssm.model;

import java.util.List;

public class ItemsQueryVo {
	// 商品信息
	private Items items;

	// 对原始生成的vo进行扩展
	private ItemsCustom itemsCustom;

	private List<ItemsCustom> itemsList; // 批量修改

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public ItemsCustom getItemsCustom() {
		return itemsCustom;
	}

	public void setItemsCustom(ItemsCustom itemsCustom) {
		this.itemsCustom = itemsCustom;
	}

	public List<ItemsCustom> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsCustom> itemsList) {
		this.itemsList = itemsList;
	}

}
