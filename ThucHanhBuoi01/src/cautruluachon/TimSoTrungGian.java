package cautruluachon;

import java.util.ArrayList;
import java.util.Scanner;

public class TimSoTrungGian {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Float> array = new ArrayList<>();
		System.out.println("Tim So Trung gian");
		System.out.println("Nhap a: ");
		float bienA = sc.nextFloat();
		System.out.println("Nhap b: ");
		float bienB = sc.nextFloat();
		System.out.println("Nhap c: ");
		float bienC = sc.nextFloat();
		array.add(bienA);
		array.add(bienB);
		array.add(bienC);
		TimSoTrungGian timso = new TimSoTrungGian();
		timso.timso(bienA, bienB, bienC);
	}
	
	private void timso(float number1, float number2, float number3) {
		
		float result = number1;
		
		if(((number1>number2)&&(number1<number3))||((number1<number2)&&(number1>number3))){
			System.out.println("So trung gian: "+number1);
		}
		if(((number2>number1)&&(number2<number3))||((number2<number1)&&(number2>number3))){
			System.out.println("So trung gian: "+number2);
		}
		if(((number3>number2)&&(number3<number1))||((number3<number2)&&(number3>number1))){
			System.out.println("So trung gian: "+number3);
		}

	}
}
