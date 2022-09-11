package com.company;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Item> list = new ArrayList<>();
        HashMap<BigInteger,Item> facs = new HashMap<>();
        Set<BigInteger> facset = facs.keySet();
        String text = input.nextLine();
        String next = text.replaceAll(" ","");
        String nnext = next.replaceAll("\t","");
        if (!nnext.startsWith("+") && !nnext.startsWith("-")) { nnext = "+" + nnext; }
        Pattern p = Pattern.compile("[+-]{1,2}x?(\\d+)?(\\*x)?(\\*\\*[+-]?\\d+)?");
        Matcher m = p.matcher(nnext);
        while (m.find()) {
            Str str = new Str(m.group());
            Item raw = str.deal();
            list.add(raw.compute());
        }
        for (Item a:list) {
            char nsign = '+';
            BigInteger nnum = BigInteger.ZERO;
            if (facset.contains(a.getIndex())) {
                BigInteger num = facs.get(a.getIndex()).getNum();
                if (a.getSign() == facs.get(a.getIndex()).getSign())
                {
                    nsign = a.getSign();
                    nnum = facs.get(a.getIndex()).getNum().add(a.getNum());
                }
                else {
                    if (a.getNum().compareTo(facs.get(a.getIndex()).getNum()) < 0) {
                        nsign = facs.get(a.getIndex()).getSign();
                        nnum = facs.get(a.getIndex()).getNum().subtract(a.getNum());
                    }
                    else if (a.getNum().compareTo(facs.get(a.getIndex()).getNum()) > 0) {
                        nsign = a.getSign();
                        nnum = a.getNum().subtract(facs.get(a.getIndex()).getNum());
                    }
                }
            }
            else {
                nsign = a.getSign();
                nnum = a.getNum();
            }
            Item com = new Item(nsign,nnum.toString(),a.getIndex().toString());
            facs.put(a.getIndex(),com);
        }
        list.clear();
        for (BigInteger a:facset) {
            list.add(facs.get(a));
        }
        for (int i = 0;i < list.size();i++) {
            list.get(i).print(list,i);
            // System.out.println();
        }
    }
}
