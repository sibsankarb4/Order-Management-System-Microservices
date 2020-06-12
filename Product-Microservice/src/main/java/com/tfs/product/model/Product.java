package com.tfs.product.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * this entity represents product  entity
 * @author AvkashKumar
 *
 */
@Document(collection="Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({
    "producId",
    "productName",
    "productDesc",
    "qtyInStock",
    "prodCategory",
    "price",
    "prodManufacturer",
    "prodMfgDate",
    "version",
    "vendor",
    "createdBy",
    "createdDate",
    "updatedBy",
    "updatedDate"
})
public class Product implements Serializable {
private static final long serialVersionUID = 1L;

 @Id
 @JsonProperty("producId")
 private String producId;
 @JsonProperty("productName")
  private String productName;
 @JsonProperty("productDesc")
  private String productDesc;
 @JsonProperty("qtyInStock")
  private Integer qtyInStock;
 @JsonProperty("prodCategory")
  private String prodCategory;
 @JsonProperty("price")
  private String price;
 @JsonProperty("prodManufacturer")
  private String prodManufacturer;
 @JsonProperty("prodMfgDate")
  private String prodMfgDate;
 @JsonProperty("vendor")
  private String vendor;
 @JsonProperty("version")
  private String version;
 @JsonProperty("createdBy")
  private String createdBy;
 @JsonProperty("createdDate")
  private String createdDate;
 @JsonProperty("updatedBy")
  private String updatedBy;
 @JsonProperty("updatedDate")
  private String updatedDate;
/**
 * @return the producId
 */
public String getProducId() {
	return producId;
}
/**
 * @param producId the producId to set
 */
public void setProducId(String producId) {
	this.producId = producId;
}
/**
 * @return the productName
 */
public String getProductName() {
	return productName;
}
/**
 * @param productName the productName to set
 */
public void setProductName(String productName) {
	this.productName = productName;
}
/**
 * @return the productDesc
 */
public String getProductDesc() {
	return productDesc;
}
/**
 * @param productDesc the productDesc to set
 */
public void setProductDesc(String productDesc) {
	this.productDesc = productDesc;
}

/**
 * @return the qtyInStock
 */
public Integer getQtyInStock() {
	return qtyInStock;
}
/**
 * @param qtyInStock the qtyInStock to set
 */
public void setQtyInStock(Integer qtyInStock) {
	this.qtyInStock = qtyInStock;
}
/**
 * @return the prodCategory
 */
public String getProdCategory() {
	return prodCategory;
}
/**
 * @param prodCategory the prodCategory to set
 */
public void setProdCategory(String prodCategory) {
	this.prodCategory = prodCategory;
}
/**
 * @return the price
 */
public String getPrice() {
	return price;
}
/**
 * @param price the price to set
 */
public void setPrice(String price) {
	this.price = price;
}
/**
 * @return the prodManufacturer
 */
public String getProdManufacturer() {
	return prodManufacturer;
}
/**
 * @param prodManufacturer the prodManufacturer to set
 */
public void setProdManufacturer(String prodManufacturer) {
	this.prodManufacturer = prodManufacturer;
}
/**
 * @return the prodMfgDate
 */
public String getProdMfgDate() {
	return prodMfgDate;
}
/**
 * @param prodMfgDate the prodMfgDate to set
 */
public void setProdMfgDate(String prodMfgDate) {
	this.prodMfgDate = prodMfgDate;
}
/**
 * @return the vendor
 */
public String getVendor() {
	return vendor;
}
/**
 * @param vendor the vendor to set
 */
public void setVendor(String vendor) {
	this.vendor = vendor;
}
/**
 * @return the version
 */
public String getVersion() {
	return version;
}
/**
 * @param version the version to set
 */
public void setVersion(String version) {
	this.version = version;
}
/**
 * @return the createdBy
 */
public String getCreatedBy() {
	return createdBy;
}
/**
 * @param createdBy the createdBy to set
 */
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
/**
 * @return the createdDate
 */
public String getCreatedDate() {
	return createdDate;
}
/**
 * @param createdDate the createdDate to set
 */
public void setCreatedDate(String createdDate) {
	this.createdDate = createdDate;
}
/**
 * @return the updatedBy
 */
public String getUpdatedBy() {
	return updatedBy;
}
/**
 * @param updatedBy the updatedBy to set
 */
public void setUpdatedBy(String updatedBy) {
	this.updatedBy = updatedBy;
}
/**
 * @return the updatedDate
 */
public String getUpdatedDate() {
	return updatedDate;
}
/**
 * @param updatedDate the updatedDate to set
 */
public void setUpdatedDate(String updatedDate) {
	this.updatedDate = updatedDate;
}
 
}
