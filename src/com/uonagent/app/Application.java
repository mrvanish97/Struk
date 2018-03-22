package com.uonagent.app;

import com.uonagent.struk.Struk;

public class Application {
  public static void main(String... args) {
    Struk s = Struk.getInstance();
    System.out.println(s.getDecimal("1001", 3));
  }
}
