package org.foxconn.bootstrapTest.entity;

import org.foxconn.bootstrapTest.util.StringArrayUtil;

public class LabelEntity {
	Integer	Id	;
	String	parentSkuno	;
	String	skuno	;
	String 	label;
	Integer	qty	;
	String	isPreDo	;
	String valid;
	String version;
	String description;
	String others;
	String ids;
	String parentSkunos;
	String skunos;
	
	
	
	public String getParentSkunos() {
		return parentSkunos;
	}
	public void setParentSkunos(String parentSkunos) {
		this.parentSkunos = parentSkunos;
	}
	public String getSkunos() {
		return skunos;
	}
	public void setSkunos(String skunos) {
		this.skunos = skunos;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		if(null!=ids){
			this.ids = StringArrayUtil.addDHAndFh(ids);
		}
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getParentSkuno() {
		return parentSkuno;
	}
	public void setParentSkuno(String parentSkuno) {
		this.parentSkuno = parentSkuno;
	}
	public String getSkuno() {
		return skuno;
	}
	public void setSkuno(String skuno) {
		this.skuno = skuno;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(String qty) {
		Integer q=0;
		try {
			 q = Integer.parseInt(qty);
		} catch (Exception e) {
			
		}
		this.qty = q;
	}
	public String getIsPreDo() {
		return isPreDo;
	}
	public void setIsPreDo(String isPreDo) {
		this.isPreDo = isPreDo;
	}
	@Override
	public String toString() {
		return "LabelEntity [Id=" + Id + ", parentSkuno=" + parentSkuno + ", skuno=" + skuno + ", label=" + label
				+ ", qty=" + qty + ", isPreDo=" + isPreDo + ", valid=" + valid + ", version=" + version
				+ ", description=" + description + ", others=" + others + ", ids=" + ids + ", parentSkunos="
				+ parentSkunos + ", skunos=" + skunos + "]";
	}
	
}
