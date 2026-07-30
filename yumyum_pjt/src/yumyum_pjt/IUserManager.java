package yumyum_pjt;

public interface IUserManager {
	
	//회원 찾기
	User find(String id);
	
	//회원 가입
	void join(User user);
	
	//회원 정보 수정
	User fix(User user);
	
	//회원 정보 삭제
	User del(User user);
	
	//등록된 모든 유저 반환
	User[] getList();
	
	

}
