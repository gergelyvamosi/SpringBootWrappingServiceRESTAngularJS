package org.gvamosi.wrapping.controller;

import org.gvamosi.wrapping.model.Wrapping;
import org.gvamosi.wrapping.service.WrappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WrappingController {

	@Autowired
	WrappingService wrappingService;

	@RequestMapping(value = "/api/LineBreak", method = RequestMethod.POST, headers = "Accept=application/json")
	public Wrapping wrapText(@RequestBody Wrapping wrapping) {
		return wrappingService.wrapText(wrapping);
	}
	
	@RequestMapping(value = "/api/LineBreak/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Wrapping getWrappingByWorkId(@PathVariable int id) {
		return wrappingService.getWrapping(id);
	}
	
}
