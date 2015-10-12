package com.dmitrykolesnikovich;

import java.util.*;

public class Main {

  private static final int NO_RESULT = -1;

  public static void main(String[] args) {
    List<List<Integer>> lists = new ArrayList<>();
    String arraysParam = args[0];
    arraysParam = arraysParam.replaceAll("\\{", "").replaceAll(" ", "");
    String[] tokens = arraysParam.split("}");
    for (String token : tokens) {
      String[] stringElements = token.split(",");
      List<Integer> intList = new ArrayList<>();
      for (String stringElement : stringElements) {
        intList.add(Integer.valueOf(stringElement));
      }
      lists.add(intList);
    }
    new Main().solve(lists);
  }

  private void solve(List<List<Integer>> chains) {
    for (int i = 0; i < chains.size(); i++) {
      for (int j = i + 1; j < chains.size(); j++) {
        List<Integer> chain1 = chains.get(i);
        List<Integer> chain2 = chains.get(j);
        int index = performSolve(chain1, chain2);
        String chain1AndChain2 = chain1.toString() + " and " + chain2.toString();
        if (index != -1) {
          int value = chain1.get(index);
          System.out.println(chain1AndChain2 + ": index = " + index + ", value = " + value);
        } else {
          System.out.println(chain1AndChain2 + ": nothing in common");
        }

      }
    }
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
      if (common.get(0) == chain1.get(0)) {
        return common.size() - 1;
      } else {
        return chain1.size() - common.size();
      }
    }

  }

}
