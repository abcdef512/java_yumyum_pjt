package yumyum_pjt;

import java.util.InputMismatchException;
import java.util.Scanner;

public class yumyum {
	public static void main(String[] args) {

		IUserManager manager = UserManagerImpl.getInstance();
		Scanner sc = new Scanner(System.in);

		System.out.println("아이디 입력: ");
		String id = sc.nextLine();

		User user = manager.find(id);

		if (user != null) {

			System.out.println("로그인 성공");

		} else {
			System.out.println("등록되지 않은 아이디입니다 <회원가입 진행>");

			System.out.println("비밀번호: ");
			String pwd = sc.nextLine();

			System.out.println("이름: ");
			String name = sc.nextLine();

			System.out.println("이메일: ");
			String email = sc.nextLine();

			int age;
			while (true) {
				try {
					System.out.println("나이: ");
					age = sc.nextInt();
					break;
				} catch (InputMismatchException e) {
					System.out.println("숫자를 입력해주세요");
					sc.nextLine();
				}
			}
			User newUser = new User(id, pwd, name, email, age);

			manager.join(newUser);

			System.out.println("회원가입 완료");

		}
		sc.close();

		/*
		 * System.out.println("아이디 입력"); Scanner sc1 = new Scanner(System.in); String
		 * scid = sc1.nextLine(); sc1.nextLine();
		 * 
		 * System.out.println("비밀번호 숫자 4자리 입력"); Scanner sc2 = new Scanner(System.in);
		 * int scpwd = sc2.nextInt(); sc2.nextLine();
		 * 
		 * System.out.println("이름 입력"); Scanner sc3 = new Scanner(System.in); String
		 * scname = sc3.nextLine(); sc3.nextLine();
		 * 
		 * System.out.println("이메일 입력"); Scanner sc4 = new Scanner(System.in); String
		 * scemail = sc4.nextLine(); sc4.nextLine();
		 * 
		 * System.out.println("나이 입력"); Scanner sc5 = new Scanner(System.in); int scage
		 * = sc5.nextInt(); sc5.nextLine();
		 */

	}
}
