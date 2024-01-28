package com.sb.customer.utility;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageResponse<T> {
	
	private List<T> dataList;
	private int totalPageSize;
	private Long totalNuberOfRecors;
	private String message;
	

}
