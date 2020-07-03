/**
 * 
 */
package com.ecomm.pricing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * @author rsreenath
 *
 */

@Entity
public class PriceList {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@ElementCollection
	@CollectionTable(name = "sku_price")
	private List<SKUPrice> skuPrice; 
	
	public PriceList() {
		
	}
	
	public PriceList(Long id, String name, List<SKUPrice> skuPrice) {
		this.id = id;
		this.name = name;
		this.skuPrice = new CopyOnWriteArrayList<SKUPrice>();
		((CopyOnWriteArrayList<SKUPrice>) this.skuPrice).addAllAbsent(skuPrice);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SKUPrice> getSkuPrice() {
		return Collections.unmodifiableList(this.skuPrice);
	}

	public boolean addSkuPrice(Long sku, BigDecimal price) {
		return this.skuPrice.add(new SKUPrice(sku, price));
	}
	
	public boolean removeSkuPrice(Long sku) {
		for(SKUPrice sKUPrice : this.skuPrice) {
			if(sKUPrice.getSku().equals(sku)) {
				return this.skuPrice.remove(sKUPrice);
			}
		}
		
		return false;
	}
	
}
