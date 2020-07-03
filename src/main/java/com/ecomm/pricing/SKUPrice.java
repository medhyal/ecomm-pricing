/**
 * 
 */
package com.ecomm.pricing;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

/**
 * @author rsreenath
 *
 */

@Embeddable
public class SKUPrice {
	
	private Long sku;
	
	private BigDecimal price;
	
	public SKUPrice() {
	}
	
	public SKUPrice(Long sku, BigDecimal price) {
		this.sku = sku;
		this.price = price;
	}

	public Long getSku() {
		return sku;
	}

	public BigDecimal getPrice() {
		return this.price;
	}
	

}
