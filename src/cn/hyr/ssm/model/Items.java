package cn.hyr.ssm.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cn.hyr.ssm.controller.validation.ValidateGroup1;
import cn.hyr.ssm.controller.validation.ValidateGroup2;

public class Items {
	private Integer id;

	// 校验名称 名称是否在1-30个字符中间
	// groups:使用哪一组的分组校验规则，可以定义多个分组。 相当于指定分组，将此校验归于groups属性中的ValidateGroup1分组
	@Size(min = 1, max = 30, message = "{items.name.length.error}", groups = { ValidateGroup1.class })
	private String name;

	private Float price;

	private String pic;

	// 飞空校验
	@NotNull(message = "{items.createtime.isNUll}",groups={ValidateGroup2.class})
	private Date createtime;

	private String detail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic == null ? null : pic.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail == null ? null : detail.trim();
	}
}