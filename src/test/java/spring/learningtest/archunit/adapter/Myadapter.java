package spring.learningtest.archunit.adapter;

import spring.learningtest.archunit.application.MyService;

public class Myadapter {

  MyService myService;

  void run() {
    myService = new MyService();
    System.out.println(myService);
  }

}
