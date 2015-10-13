package com.dmitrykolesnikovich.synesisTask;

import java.util.ArrayList;
import java.util.List;

public class Solver {

  private static final int NO_CROSSES = -1;
  private static final int TWO_CROSSES = -2;
  private static final int THE_SAME = -3;

  /**
   * @param result string representation of algorithm result.
   *               See more https://www.dropbox.com/s/iazj2w79v2co67y/result.png
   */
  public void solve(List<List<Integer>> chains, StringBuffer result) {
    for (int i = 0; i < chains.size(); i++) {
      for (int j = i + 1; j < chains.size(); j++) {
        List<Integer> chain1 = chains.get(i);
        List<Integer> chain2 = chains.get(j);
        int index = findIndex(chain1, chain2);
        String chain1AndChain2 = chain1.toString() + " and " + chain2.toString();
        if (index == NO_CROSSES) {
          result.append(chain1AndChain2 + ": нигде не пересекаются\n");
        } else if (index == TWO_CROSSES) {
          result.append(chain1AndChain2 + ": две развилки\n");
        } else if (index == THE_SAME) {
          result.append(chain1AndChain2 + ": нет развилок\n");
        } else {
          int value = chain1.get(index);
          result.append(chain1AndChain2 + ": index = " + index + ", value = " + value + "\n");
        }
      }
    }
  }

  /*
  * @return index of last common element between chains relatively to first one
  */
  private int findIndex(List<Integer> chain1, List<Integer> chain2) {
    if (chain1.containsAll(chain2) || chain2.containsAll(chain1)) {
      return THE_SAME;
    }

    List<Integer> common = new ArrayList<>(chain1);
    common.retainAll(chain2); // intersection. See more https://en.wikipedia.org/wiki/Intersection_(set_theory)

    if (common.isEmpty()) {
      return NO_CROSSES;
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
