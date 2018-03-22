package com.uonagent.struk;

import java.util.regex.Pattern;

public class Struk {
  private static final int P_LOWER_BOUND = 2;
  private static final int P_UPPER_BOUND = 16;

  private static String[] regexes = new String[P_UPPER_BOUND - P_LOWER_BOUND + 1];

  private static Struk ourInstance = new Struk();

  public static Struk getInstance() {
    return ourInstance;
  }

  private Struk() {
  }

  public int getDecimal(String number, int p) {
    checkP(p);
    number = number.replaceFirst("^0+(?!$)", "");
    checkString(number, p);
    long res = 0;
    char c;
    for (int i = number.length() - 1, j = 0; i >= 0; --i, ++j) {
      c = number.charAt(i);
      if (Character.isDigit(c)) {
        res += Character.getNumericValue(c) * pow(p, j);
        if (res > Integer.MAX_VALUE) {
          throw new RuntimeException("Жопа");
        }
      }
    }
    return (int) res;
  }

  private long pow(long a, int b) {
    if (b == 0) {
      return 1;
    }
    if (b == 1) {
      return a;
    }
    if (b % 2 == 0) {
      return pow(a * a, b / 2);
    } else {
      return a * pow(a * a, b / 2);
    }
  }

  private void checkP(int p) {
    if (p < P_LOWER_BOUND || p > P_UPPER_BOUND) {
      throw new IllegalArgumentException("Даун такое p пихать?");
    }
  }

  private void checkString(String number, int p) {
    int regexIndex = p - P_LOWER_BOUND;
    if (regexes[regexIndex] == null) {
      makeRegex(p);
    }
    if (!Pattern.compile(regexes[regexIndex], Pattern.CASE_INSENSITIVE).matcher(number).matches()) {
      throw new IllegalArgumentException("Неверное число");
    }
  }

  private void makeRegex(int p) {
    int regexIndex = p - P_LOWER_BOUND;
    StringBuilder s = new StringBuilder("^[0-");
    if (p <= 10) {
      s.append(p - 1);
      s.append("]+$");
    } else {
      s.append("9");
      if (p == 11) {
        s.append("A]+$");
      } else {
        s.append("A-");
        s.append((char) ('A' - 9 + p));
        s.append("]+$");
      }
    }
    regexes[regexIndex] = s.toString();
  }
}
