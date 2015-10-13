package com.dmitrykolesnikovich.synesisTask;

import java.util.ArrayList;
import java.util.List;

// See more https://www.dropbox.com/s/iazj2w79v2co67y/result.png

public class IndexFinder {

  public static final int RESULT_NOTHING_IN_COMMON = -1;
  public static final int RESULT_TWO_FORKS = -2;
  public static final int RESULT_NO_FORKS = -3;

  /*
  * @return index of last common element between chains relatively to first one
  */
  public static int findIndex(List<Integer> chain1, List<Integer> chain2) {
    if (chain1.containsAll(chain2) || chain2.containsAll(chain1)) {
      return RESULT_NO_FORKS;
    }

    List<Integer> common = new ArrayList<>(chain1);
    common.retainAll(chain2); // intersection. See more https://en.wikipedia.org/wiki/Intersection_(set_theory)

    if (common.isEmpty()) {
      return RESULT_NOTHING_IN_COMMON;
    }

    int first1 = chain1.get(0);
    int last1 = chain1.get(chain1.size() - 1);
    int first2 = chain2.get(0);
    int last2 = chain2.get(chain2.size() - 1);

    if (first1 != first2 && first1 != last2 && last1 != first2 && last1 != last2) {
      return RESULT_TWO_FORKS;
    }

    if (first1 == first2) {
      return common.size() - 1;
    } else {
      return chain1.size() - common.size();
    }

  }
}
