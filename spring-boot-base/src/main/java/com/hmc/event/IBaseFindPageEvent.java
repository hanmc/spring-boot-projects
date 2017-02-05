package com.hmc.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public interface IBaseFindPageEvent {
	
	int getPage();
	
	int getSize();
	
	String[] getSortProperties();
	
	void setSortProperties(String[] properties);
	
	Direction getDirection();
	
	void setDirection(Direction direction);
	
	Pageable getPageable();
	
	void setPageable(Pageable pageable);
	
}
