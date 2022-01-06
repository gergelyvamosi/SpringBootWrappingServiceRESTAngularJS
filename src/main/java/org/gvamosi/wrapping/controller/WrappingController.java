package org.gvamosi.wrapping.controller;

import org.gvamosi.wrapping.model.Wrapping;
import org.gvamosi.wrapping.service.WrappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Wrapping> wrapText(@RequestBody Wrapping wrapping) {
		wrapping.setProcessed(false);
		Wrapping result = wrappingService.wrapText(wrapping);
		if (result.isProcessed()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
	}
	
	@RequestMapping(value = "/api/LineBreak/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Wrapping> getWrappingByWorkId(@PathVariable int id) {
		Wrapping result = wrappingService.getWrapping(id);
		if (result == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else if (result.isProcessed()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
	}
	
}
