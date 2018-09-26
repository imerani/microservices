/**
 * 
 */
package com.nacho.microservices.microservicestest.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nacho
 *
 */
@RestController
public class PersonVersioningController {

	@GetMapping("/v1/person")
	public PersonV1 personV1() {
		return new PersonV1("Cacho Perez");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 personV2() {
		return new PersonV2(new Name("Cacho", "Perez"));
	}
}
