
package com.tfs.order.mgmt.order.model.dto;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This Entity represents all the Address attribute
 * 
 * @author AmlanSaha
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "addressLine1",
    "addressLine2",
    "city",
    "state",
    "zipCode",
    "country"
})
public class Address implements Serializable
{
	private final static long serialVersionUID = -3930009017282385831L;

    @JsonProperty("addressLine1")
    public String addressLine1;
    @JsonProperty("addressLine2")
    public String addressLine2;
    @JsonProperty("city")
    public String city;
    @JsonProperty("state")
    public String state;
    @JsonProperty("zipCode")
    public long zipCode;
    @JsonProperty("country")
    public String country;
}
