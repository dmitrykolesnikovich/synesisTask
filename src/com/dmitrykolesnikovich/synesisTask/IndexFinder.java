package com.dmitrykolesnikovich.synesisTask;

import java.util.ArrayList;
import java.util.List;

public class IndexFinder {

  public static final int NOTHING_IN_COMMON = -1;
  public static final int TWO_CROSSES = -2;
  public static final int NO_CROSSES = -3;

  /*
  * @return index of last common element between chains relatively to first one
  */
  public static int findIndex(List<Integer> chain1, List<Integer> chain2) {
    if (chain1.containsAll(chain2) || chain2.containsAll(chain1)) {
      return NO_CROSSES;
    }

    List<Integer> common = new ArrayList<>(chain1);
    common.retainAll(chain2); // intersection. See more https://en.wikipedia.org/wiki/Intersection_(set_theory)

    if (common.isEmpty()) {
      return NOTHING_IN_COMMON;
    }

    int first1 = chain1.get(0);
    int last1 = chain1.get(chain1.size() - 1);
    int first2 = chain2.get(0);
    int last2 = chain2.get(chain2.size() - 1);

    if (first1 != first2 && first1 != last2 && last1 != first2 && last1 != last2) {
      return TWO_CROSSES;
    }

    if (first1 == first2) {
      return common.size() - 1;
    } else {
      return chain1.size() - common.size();
    }

  }
}
