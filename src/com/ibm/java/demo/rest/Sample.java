package com.ibm.java.demo.rest;

import javax.xml.bind.annotation.XmlRootElement;

// Test Connection
public class Sample {
	
  public String test;
  public String result;

  public Sample() {} // JAXB needs this

  public Sample(String test, String result) {
    this.test = test;
    this.result = result;
  }
  
}