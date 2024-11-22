package base.util;

import base.abstractions.Owned;


public class Helper {

  public static boolean notInstanceofOwned(Object dummy) {
    if (dummy == null) {
      return true;
    }

    return !(dummy instanceof Owned);
  }
}
