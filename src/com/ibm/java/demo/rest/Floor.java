package com.ibm.java.demo.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Floor {
  public String book;
  public String title;

  public Floor() {} // JAXB needs this

  public Floor(String book, String title) {
    this.book = book;
    this.title = title;
  }
  
}