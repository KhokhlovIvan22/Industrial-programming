package lab1;

import java.text.*;
import java.io.*;
import java.text.NumberFormat;

public class Taylor {

	public static double calc (double x, int k) {
		double eps = Math.pow(10, -k);
		double sum = 0.0;
		int n=1;
		double a_n=x;
		while (Math.abs(a_n)>=eps)
		{
			sum+=a_n;
			a_n = a_n * x * x * (2*n - 1) * (2*n - 1) / ( (2*n) * (2*n + 1)); 
			//рекурсивная формула слагаемого - a(n+1)=a(n)*x^2*(2n-1)^2/(2n(2n+1)) 
			n++;
		}
		return sum;		
	}
	
	public static void main(String[] args) {
	    System.out.println("Вычисление значения функции arcsin x");
	    InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader buf = new BufferedReader(isr);
	    try {
	    	double x;
	    	while (true) {
	    	    System.out.print("Введите значение аргумента x в диапазоне (-1,1): ");
	    	    String line1 = buf.readLine();
	    	    try {
	    	        x = Double.parseDouble(line1);
	    	    } catch (NumberFormatException e) {
	    	        System.out.println("Ошибка! Введите число");
	    	        continue; // повторить ввод
	    	    }
	    	    if (x <= -1 || x >= 1) {
	    	        System.out.println("Ошибка! x должен обязательно принадлежать промежутку (-1, 1)");
	    	    } 
	    	    else {
	    	        break;
	    	    }
	    	}
	        System.out.print("Введите целочисленное значение k точности вычисления: ");
	        String line2 = buf.readLine();
	        int k = Integer.parseInt(line2);
	        // Форматирование вывода
	        NumberFormat formatter = NumberFormat.getNumberInstance();
	        formatter.setMaximumFractionDigits(5);
	        System.out.print("Значение функции через разложение в ряд Тейлора: ");
	        System.out.println(formatter.format(calc(x, k)));
	        System.out.print("Значение выражения через вызов стандартной функции: ");
	        System.out.println(formatter.format(Math.asin(x)));
	    } catch (NumberFormatException e) {
	        System.out.println("Ошибка! Введены данные недопустимого формата");
	    } catch (IOException e) {
	        System.out.println("Ошибка чтения с клавиатуры");
	    }
	}
}
