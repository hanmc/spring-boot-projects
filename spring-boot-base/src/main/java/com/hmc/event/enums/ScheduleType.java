package com.hmc.event.enums;


public enum ScheduleType {

	CYCLE("cycle","固定周期"),
	FIX("fix","固定时间"),
	;
	
	private String code;
	private String desc;

	public static ScheduleType searchByCode(String code){
		ScheduleType[] operateTypes = ScheduleType.values();
		for (ScheduleType operateType : operateTypes) {
			if(code.equals(operateType.getCode())){
				return operateType;
			}
		}
		return null;
	}
	
	public static ScheduleType searchByString(String code){
		ScheduleType[] operateTypes = ScheduleType.values();
		for (ScheduleType operateType : operateTypes) {
			if(code.equals(operateType.toString())){
				return operateType;
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	ScheduleType(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
}
