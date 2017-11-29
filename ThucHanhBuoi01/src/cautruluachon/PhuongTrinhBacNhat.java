package cautruluachon;

import java.util.Scanner;

public class PhuongTrinhBacNhat {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Giai pt bac nhat co dang: aX + b = 0");
		System.out.println("Nhap a: ");
		float bienA = sc.nextFloat();
		System.out.println("Nhap b: ");
		float bienB = sc.nextFloat();
		PhuongTrinhBacNhat pt = new PhuongTrinhBacNhat();
		pt.tinhPhuongTrinh(bienA, bienB);

	}
	public PhuongTrinhBacNhat() {
		
		
	}
	
	public void tinhPhuongTrinh(float bienA, float bienB) {
		if (bienA == 0) {
			if (bienB == 0) {
				System.out.println("PT vo so nghiem");
			}
			else {
				System.out.println("PT vo nghiem");
			}
		}
		else if (bienB ==0 ) {
			System.out.println("PT co nghiem X: 0");
		}
		else{
			System.out.println("PT co nghiem X: "+(-bienB/bienA));
		}

	}

}
