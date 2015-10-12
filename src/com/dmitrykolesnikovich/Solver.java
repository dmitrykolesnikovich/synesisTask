package com.dmitrykolesnikovich;

import java.util.ArrayList;
import java.util.List;

public class Solver {

  private static final int NO_RESULT = -1;
  private static final int CROSSES = -2;

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
    return solve1(chains);
  }

  private String solve1(List<List<Integer>> chains) {
    String result = new String("UTF-8");
    for (int i = 0; i < chains.size(); i++) {
      for (int j = i + 1; j < chains.size(); j++) {
        List<Integer> chain1 = chains.get(i);
        List<Integer> chain2 = chains.get(j);
        int index = performSolve(chain1, chain2);
        String chain1AndChain2 = chain1.toString() + " and " + chain2.toString();
        if (index == NO_RESULT) {
          result += chain1AndChain2 + ": нет пересечения" + "\n";
        } else if (index == CROSSES) {
          result += chain1AndChain2 + ": две развилки" + "\n";
        } else {
          int value = chain1.get(index);
          result += chain1AndChain2 + ": index = " + index + ", value = " + value + "\n";
        }
      }
    }
    return result;
  }

  /**
   * @return common element index relatively to chain1 or -1 if nothing in common
   */
  private int performSolve(List<Integer> chain1, List<Integer> chain2) {
    List<Integer> common = new ArrayList<>(chain1);
    common.retainAll(chain2);
    if (common.isEmpty()) {
      return NO_RESULT;
    } else {
      Integer first = chain1.get(0);
      Integer last = chain1.get(chain1.size() - 1);
      Integer commonFirst = common.get(0);
      Integer commonLast = common.get(common.size() - 1);
      if (commonFirst != first && commonFirst != last && commonLast != first && commonLast != last) {
        return CROSSES;
      } else {
        if (commonFirst == chain1.get(0)) {
          return common.size() - 1;
        } else {
          return chain1.size() - common.size();
        }
      }
    }

  }
}
