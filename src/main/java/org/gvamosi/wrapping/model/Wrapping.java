
package org.gvamosi.wrapping.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Model class.
 */
public class Wrapping{

	private long workId;

	private int wrapLength = 10;
	
	private String textToWrap = "";
	
	private List<String> wrappedText = new ArrayList<String>();
	
	private boolean processed = false;

	public Wrapping() {
	}
	public Wrapping(int wrapLength, String textToWrap) {
		this.wrapLength = wrapLength;
		this.textToWrap = textToWrap;
	}
	public long getWorkId() {
		return workId;
	}
	public void setWorkId(long workId) {
		this.workId = workId;
	}
	public int getWrapLength() {
		return wrapLength;
	}
	public void setWrapLength(int wrapLength) {
		this.wrapLength = wrapLength;
	}
	public String getTextToWrap() {
		return textToWrap;
	}
	public void setTextToWrap(String textToWrap) {
		this.textToWrap = textToWrap;
	}
	public List<String> getWrappedText() {
		return wrappedText;
	}
	public void setWrappedText(List<String> wrappedText) {
		this.wrappedText = wrappedText;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
}
