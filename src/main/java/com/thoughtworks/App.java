package com.thoughtworks;

import java.util.Arrays;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    String[] itemId = getItemIds();
    String[] name = getItemNames();
    double[] price1 = getItemPrices();

    //price1转为int数组，直接用（int)会使18.00转化为0？
    int[] price = new int[price1.length];
    for (int i = 0; i < price.length; i++) {
      String s1 = String.valueOf(price1[i]);
      String s2 = s1.substring(0, s1.indexOf("."));
      price[i] = Integer.parseInt(s2);
    }

    //识别selecteditem里面的菜品编号和数字
    String[] strArr = selectedItems.split(",");

    // 此时strArr[1]=ITEM0001 x 1,
    // 把每个菜都拆分成item*数量
    // 获取被下单的菜品ID

    String[] ID = new String[strArr.length];
    for (int i = 0; i < strArr.length; i++) {
      ID[i] = strArr[i].substring(0, 8);
    }

    // 获取点菜的数量
    // 并赋值每个菜品的数量

    String[] dishes = new String[strArr.length];
    for (int i = 0; i < strArr.length; i++) {
      dishes[i] = strArr[i].substring(11, strArr[i].length());
    }

    int[] num = new int[dishes.length];
    for (int i = 0; i < dishes.length; i++) {
      num[i] = Integer.parseInt(dishes[i]);
    }

    int[] numOfDishes = new int[]{0, 0, 0, 0};
    for (int i = 0; i < ID.length; i++) {
      for (int j = 0; j < itemId.length; j++) {
        if (itemId[j].equals(ID[i])) {
          numOfDishes[j] = num[i];
        }
      }
    }


    //三种方法算出的总价

    int promotion1 = 0;

    //计算半价菜品的总和
    int add = 0;
    for (int i = 0; i < numOfDishes.length; i++) {
      if (i == 0 | i == 2) {
        add = numOfDishes[i] * price[i] / 2;
      } else {
        add = numOfDishes[i] * price[i];
      }
      promotion1 += add;
    }

    //满30减6算出的优惠
    int promotion2 = 0;
    for (int i = 0; i < numOfDishes.length; i++) {
      promotion2 += numOfDishes[i] * price[i];
    }

    if (promotion2 >= 30) {
      promotion2 -= promotion2 / 30 * 6;
    }

    //原价
    int originalPrice = 0;
    for (int i = 0; i < numOfDishes.length; i++) {
      originalPrice += numOfDishes[i] * price[i];
    }

    //每个菜品的总价

    int[] priceOfDish = new int[price.length];
    for (int i = 0; i < priceOfDish.length; i++) {
      priceOfDish[i] = price[i] * numOfDishes[i];
    }

    int saveMoney1 = originalPrice - promotion1;
    int saveMoney2 = originalPrice - promotion2;

    String summary = "============= 订餐明细 =============\n";

    for (int i = 0; i < numOfDishes.length; i++) {
      if (numOfDishes[i] != 0) {
        summary += String.format(name[i] + " x " + numOfDishes[i] + " = " + priceOfDish[i] + "元\n");
      }
    }



    if (promotion1 == originalPrice & promotion2 == originalPrice) {
      summary += "-----------------------------------\n"
              + String.format("总计：" + originalPrice + "元\n")
              + "===================================";
    } else if (promotion1 <= promotion2) {
      summary +="-----------------------------------\n"
              + "使用优惠:\n"
              + String.format("指定菜品半价(" + name[0] + "，" + name[2] + ")，" + "省" + saveMoney1 + "元\n")
              + "-----------------------------------\n"
              + String.format("总计：%d元\n", promotion1)
              + "===================================";
    } else {
      summary += "-----------------------------------\n"
              + "使用优惠:\n"
              + String.format("满30减6元，省%d元\n", saveMoney2)
              + "-----------------------------------\n"
              + String.format("总计：%d元\n", promotion2)
              + "===================================";
      ;
    }

    return summary;

  }


  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }


  /**
   * 获取每个菜品依次的名称
   */


  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }
}
