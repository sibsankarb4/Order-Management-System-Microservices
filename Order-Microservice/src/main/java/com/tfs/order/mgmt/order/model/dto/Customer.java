package com.tfs.order.mgmt.order.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This Entity represents all the Customer attribute
 * 
 * @author AmlanSaha
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "id",
    "firstName",
    "lastName",
    "phoneNumber",
    "email",
    "address",
    "creditLimit",
    "version",
    "createdBy",
    "createdDate",
    "updatedBy",
    "updatedDate"
})
public class Customer implements Serializable
{

	private final static long serialVersionUID = 6938865787322676118L;
	
	@Id
    @JsonProperty("id")
    public String id;
    @JsonProperty("firstName")
    public String firstName;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("phoneNumber")
    public long phoneNumber;
    @JsonProperty("email")
    public String email;
    @JsonProperty("address")
    public Address address;
    @JsonProperty("creditLimit")
    public Double creditLimit;
    @Version
    @JsonProperty("version")
    public long version;
    @CreatedBy
    @JsonProperty("createdBy")
    public String createdBy;
    @CreatedDate
    @JsonProperty("createdDate")
    public Date createdDate;
    @LastModifiedBy
    @JsonProperty("updatedBy")
    public String updatedBy;
    @LastModifiedDate
    @JsonProperty("updatedDate")
    public Date updatedDate;
}
