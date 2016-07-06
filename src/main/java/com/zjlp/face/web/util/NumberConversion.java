package com.zjlp.face.web.util;

import java.util.Stack;

public class NumberConversion {

	private static char[] charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();    
		
		public static String getShopNo (String str) {
			String no = "HZJZ";
			String str6 = str.substring(str.length()-6); //取后六位
			String str1 = str.substring(0,str.length()-6); //取前N位
			return no + base62ToDecimal(str1) + str6;
		}
	
		public static String setShopNo (String str) {
			String no = str.substring(4); //去掉前四位
			String str10 = no.substring(0, 10); //取前十位
			String str6 = no.substring(10); //取最后六位
			return NumberConversion.decimalTo62(Integer.valueOf(str10), 5) + str6;
		}
		
	
		/**
		 * 将10进制转化为62进制 
		 * @param number 
		 * @param length 转化成的62进制长度，不足length长度的话高位补0，否则不改变什么
		 * @return
		 */
		public static String decimalTo62(long number, int length){
	         Long rest=number;
	         Stack<Character> stack=new Stack<Character>();
	         StringBuilder result=new StringBuilder(0);
	         while(rest!=0){
	             stack.add(charSet[new Long((rest-(rest/62)*62)).intValue()]);
	             rest=rest/62;
	         }
	         for(;!stack.isEmpty();){
	             result.append(stack.pop());
	         }
	         int result_length = result.length();
	         StringBuilder temp0 = new StringBuilder();
	         for(int i = 0; i < length - result_length; i++){
	        	 temp0.append('0');
	         }
	         
	         return temp0.toString() + result.toString();

		}
		
		
		/**
		 * 将62进制转换成10进制数
		 * 
		 * @param ident62
		 * @return
		 */
		private static String base62ToDecimal( String ident62 ) {
			int decimal = 0;
			int base = 62;
			int keisu = 0;
			int cnt = 0;

			byte ident[] = ident62.getBytes();
			for ( int i = ident.length - 1; i >= 0; i-- ) {
				int num = 0;
				if ( ident[i] > 48 && ident[i] <= 57 ) {
					num = ident[i] - 48;
				}
				else if ( ident[i] >= 65 && ident[i] <= 90 ) {
					num = ident[i] - 65 + 10;
				}
				else if ( ident[i] >= 97 && ident[i] <= 122 ) {
					num = ident[i] - 97 + 10 + 26;
				}
				keisu = (int) java.lang.Math.pow( (double) base, (double) cnt );
				decimal += num * keisu;
				cnt++;
			}
			return String.format( "%08d", decimal );
		}
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			System.out.println("62System=" +decimalTo62(Integer.parseInt("1507171613"), 5));
			System.out.println("10System=" +base62ToDecimal("1dzwBZ"));
			System.out.println(setShopNo("HZJZ15071716136RcXr1"));
			System.out.println(getShopNo("1dzwBZ6RcXr1"));
		}
		

}
