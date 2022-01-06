package org.gvamosi.wrapping.controller;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.context.annotation.SessionScope;

@RestController
@SessionScope
public class WrappingController {

	@Autowired
	WrappingService wrappingService;

	@RequestMapping(value = "/api/LineBreak", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Wrapping> wrapText(@RequestBody Wrapping wrapping, HttpServletRequest request) {
		wrapping.setProcessed(false);
		Wrapping result = wrappingService.wrapText(wrapping, request.getSession(false).getId());
		if (result.isProcessed()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
	}
	
	@RequestMapping(value = "/api/LineBreak/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Wrapping> getWrappingByWorkId(@PathVariable int id, HttpServletRequest request) {
		Wrapping result = wrappingService.getWrapping(id, request.getSession(false).getId());
		if (result == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else if (result.isProcessed()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
	}
	
}
