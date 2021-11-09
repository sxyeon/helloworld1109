package com.yedam.member;

public class ItemVO {
	public int prodId;
	public String prodItem;
	public String prodDesc;
	public double likeIt;
	public int originPrice;
	public int salePrice;
	public String prodImage;
	
	public int getProdId() {
		return prodId;
	}
	
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	
	public String getProdItem() {
		return prodItem;
	}
	
	public void setProdItem(String prodItem) {
		this.prodItem = prodItem;
	}
	
	public String getProdDesc() {
		return prodDesc;
	}
	
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	
	public double getLikeIt() {
		return likeIt;
	}
	
	public void setLikeIt(double likeIt) {
		this.likeIt = likeIt;
	}
	
	public int getOriginPrice() {
		return originPrice;
	}
	
	public void setOriginPrice(int originPrice) {
		this.originPrice = originPrice;
	}
	
	public int getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	
	public String getProdImage() {
		return prodImage;
	}
	
	public void setProdImage(String prodImage) {
		this.prodImage = prodImage;
	}

	@Override
	public String toString() {
		return "ItemVO [prodId=" + prodId + ", prodItem=" + prodItem + ", prodDesc=" + prodDesc + ", likeIt=" + likeIt
				+ ", originPrice=" + originPrice + ", salePrice=" + salePrice + ", prodImage=" + prodImage + "]";
	}

}
