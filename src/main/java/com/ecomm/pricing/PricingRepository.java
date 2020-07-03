/**
 * 
 */
package com.ecomm.pricing;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rsreenath
 *
 */

@Repository
public interface PricingRepository extends CrudRepository<PriceList, Long> {
	
	PriceList getById(Long id);
}
