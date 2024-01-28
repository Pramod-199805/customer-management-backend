package com.sb.customer.utility;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
	private Object data;
	private LocalDateTime timeStamp;
	
	public ResponseObject(Object obj) {
		this.data = obj;
		this.timeStamp = LocalDateTime.now();
	}
}
