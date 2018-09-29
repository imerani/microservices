package com.tracfone.microservices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> urivariables = new HashMap<>();
		urivariables.put("from", from);
		urivariables.put("to", to);

		ResponseEntity<CurrencyConversionBean> response = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				urivariables);

		CurrencyConversionBean bean = response.getBody();
		logger.info("{}", bean);
		return new CurrencyConversionBean(bean.getId(), from, to, bean.getConversionMultiple(), quantity,
				bean.getConversionMultiple().multiply(quantity), bean.getPort());
	}

	@GetMapping("currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean bean = proxy.retrieveExchangeValue(from, to);

		return new CurrencyConversionBean(bean.getId(), from, to, bean.getConversionMultiple(), quantity,
				bean.getConversionMultiple().multiply(quantity), bean.getPort());
	}
}
