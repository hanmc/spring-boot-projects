package com.hmc.test.note;

import org.junit.Test;

public class ClassLoaderTest {

	@Test
	public void test1(){
		System.out.println(getClass().getResource("").getPath());
	}
	
	@Test
	public void test2(){
		System.out.println(getClass().getClassLoader().getResource("/").getPath());
	}
	
	@Test
	public void test3(){
		System.out.println(Thread.currentThread().getClass().getClassLoader().getResource("/").getPath());
	}
}
