package com.dmitrykolesnikovich.synesisTask;

import java.util.ArrayList;
import java.util.List;

public class Solver {

  private static final int NO_CROSSES = -1;
  private static final int TWO_CROSSES = -2;
  private static final int THE_SAME = -3;

  /**
   * @return string representation of algorithm result to output it on console
   */
  public String solve(List<String> rows) {
    List<List<Integer>> chains = new ArrayList<>();
    for (String row : rows) {
      row = row.replaceAll(" ", "");
      List<Integer> chain = new ArrayList<>();
      for (String element : row.split(",")) {
        chain.add(Integer.valueOf(element));
      }
      chains.add(chain);
    }
    return performSolve(chains);
  }

  private String performSolve(List<List<Integer>> chains) {
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < chains.size(); i++) {
      for (int j = i + 1; j < chains.size(); j++) {
        List<Integer> chain1 = chains.get(i);
        List<Integer> chain2 = chains.get(j);
        int crossIndex = findCrossIndex(chain1, chain2);
        String chain1AndChain2 = chain1.toString() + " and " + chain2.toString();
        if (crossIndex == NO_CROSSES) {
          result.append(chain1AndChain2 + ": нет пересечения\n");
        } else if (crossIndex == TWO_CROSSES) {
          result.append(chain1AndChain2 + ": две развилки\n");
        } else if (crossIndex == THE_SAME) {
          result.append(chain1AndChain2 + ": нет развилок\n");
        } else {
          int value = chain1.get(crossIndex);
          result.append(chain1AndChain2 + ": index = " + crossIndex + ", value = " + value + "\n");
        }
      }
    }
    return result.toString();
  }

  /*
  * @return index of last common element between chains relatively to chain1
  */
  private int findCrossIndex(List<Integer> chain1, List<Integer> chain2) {
    if (chain1.containsAll(chain2) || chain2.containsAll(chain1)) {
      return THE_SAME;
    }

    // common = intersection of chain1 and chain2
    List<Integer> common = new ArrayList<>(chain1);
    common.retainAll(chain2);

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
