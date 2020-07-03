/**
 * 
 */
package com.ecomm.pricing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rsreenath
 *
 */

@RestController
public class PricingController {
	
	@Autowired
	private PricingService pricingService;

	@RequestMapping("/api/pricing/pricelist/{pricelistid}")
	public List<SKUPrice> getSkuPrice(@PathVariable(value = "pricelistid") Long priceList) {
		return pricingService.getById(priceList);
	}

}
