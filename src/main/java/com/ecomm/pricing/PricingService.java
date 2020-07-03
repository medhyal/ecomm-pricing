/**
 *
 */
package com.ecomm.pricing;

import java.math.BigDecimal;
import java.util.List;

import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author rsreenath
 *
 */
@Service
public class PricingService {

        @Autowired
        private PricingRepository pricingRepo;

        private static final Logger logger = LoggerFactory.getLogger(PricingService.class);

        public List<SKUPrice> getById(Long priceList) {
                return pricingRepo.getById(priceList).getSkuPrice();
        }

        @KafkaListener(id = "ecommListener", topicPattern = ".*sku",
                autoStartup = "true", concurrency = "3")
		 public void listen(String message) throws JsonMappingException, JsonProcessingException, ParseException {
                ObjectMapper mapper = new ObjectMapper();
            JsonNode rootnode = mapper.readTree(message);
            //JsonNode n1 = rootnode.path("payload").path("table");
            //JsonNode n2 = rootnode.path("payload").path("op");
            //if(!"sku".equalsIgnoreCase(n1.asText())) {
            //  return;
            //}

            //if(!"c".equalsIgnoreCase(n2.asText())) {
            //  return;
            //}
            JsonNode n3 = rootnode.path("payload").path("after").path("id");
            logger.info(n3.asText());
            PriceList priceList = pricingRepo.findById(100L).get();
            priceList.addSkuPrice(new Long(n3.asText()), new BigDecimal(1.0D));

            pricingRepo.save(priceList);
        }

        public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
                String message = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"SCN\"},{\"type\":\"string\",\"optional\":false,\"field\":\"SEG_OWNER\"},{\"type\":\"string\",\"optional\":false,\"field\":\"TABLE_NAME\"},{\"type\":\"int64\",\"optional\":false,\"name\":\"org.apache.kafka.connect.data.Timestamp\",\"version\":1,\"field\":\"TIMESTAMP\"},{\"type\":\"string\",\"optional\":false,\"field\":\"SQL_REDO\"},{\"type\":\"string\",\"optional\":false,\"field\":\"OPERATION\"},{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"ID\"}],\"optional\":true,\"name\":\"value\",\"field\":\"data\"},{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"ID\"}],\"optional\":true,\"name\":\"value\",\"field\":\"before\"}],\"optional\":false,\"name\":\"ecomm.ecommerce.sku.row\"},\"payload\":{\"SCN\":80022556,\"SEG_OWNER\":\"ECOMMERCE\",\"TABLE_NAME\":\"SKU\",\"TIMESTAMP\":1580416215000,\"SQL_REDO\":\"insert into \\\"ECOMMERCE\\\".\\\"SKU\\\"(\\\"ID\\\") values (73107)\",\"OPERATION\":\"INSERT\",\"data\":{\"ID\":73107},\"before\":null}}";
                ObjectMapper mapper = new ObjectMapper();
            JsonNode rootnode = mapper.readTree(message);
            JsonNode n1 = rootnode.path("payload").path("TABLE_NAME");
            JsonNode n2 = rootnode.path("payload").path("OPERATION");
            JsonNode n3 = rootnode.path("payload").path("data").path("ID");
            String id= "";
            if(n1.asText().equals("SKU") && n2.asText().equals("INSERT")) {
                id=n3.asText();
            }
            ObjectMapper xmlMapper = new XmlMapper();
            String xmlStr = xmlMapper.writeValueAsString(rootnode);
                logger.info(id);
        }

}

