package com.company;

import java.math.BigInteger;
import java.util.ArrayList;

public class Item {
    private char sign;
    private BigInteger num;
    private BigInteger index;

    public Item(char sign,String num,String index) {
        this.sign = sign;
        this.num = new BigInteger(num);
        this.index = new BigInteger(index);
    }

    public Item compute() {
        BigInteger fone = new BigInteger("01");
        BigInteger nnum = num.multiply(index);
        BigInteger nindex = index.subtract(fone);
        Item com = new Item(sign,nnum.toString(),nindex.toString());
        return com;
    }

    public void print(ArrayList<Item> list,int i) {
        if (num.compareTo(BigInteger.ZERO) != 0) {
            if (i != 0) {
                if (num.compareTo(BigInteger.ZERO) < 0 && sign == '+') {
                    System.out.print(num);
                } else if (num.compareTo(BigInteger.ZERO) > 0 && ((sign == '+') || (sign == '-'))) {
                    System.out.print(sign);
                    System.out.print(num);
                } else if (num.compareTo(BigInteger.ZERO) < 0 && sign == '-') {
                    System.out.print("+");
                    System.out.print(num.abs());
                }
            } else if (i == 0) {
                if (num.compareTo(BigInteger.ZERO) < 0 && sign == '+') {
                    System.out.print(num);
                } else if (num.compareTo(BigInteger.ZERO) > 0 && (sign == '+')) {
                    System.out.print(num);
                } else if (num.compareTo(BigInteger.ZERO) < 0 && sign == '-') {
                    System.out.print(num.abs());
                } else if (num.compareTo(BigInteger.ZERO) > 0 && sign == '-') {
                    System.out.print("-");
                    System.out.print(num);
                }
            }


            if (index.compareTo(BigInteger.ZERO) != 0) {
                if (index.compareTo(BigInteger.ONE) != 0) {
                    System.out.print("*x**");
                    System.out.print(index);
                }
                if (index.compareTo(BigInteger.ONE) == 0) {
                    System.out.print("*x");
                }
            }
        }
        if (num.compareTo(BigInteger.ZERO) == 0 && list.size() == 1) {
            System.out.print(num);
        }
    }

    public char getSign() {
        return sign;
    }

    public BigInteger getNum() {
        return num;
    }

    public BigInteger getIndex() {
        return index;
    }
}




