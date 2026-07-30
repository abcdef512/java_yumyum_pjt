package yumyum_pjt;

import java.util.ArrayList;
import java.util.List;

public class UserManagerImpl implements IUserManager{
	
	
	//싱글톤 생성
	private static final String FILE_PATH = "user.json";
	
	private static final IUserManager instance = new UserManagerImpl();
		
	private List<User> userList = new ArrayList<>();
	
	private UserManagerImpl() {
		loadFile();
		
	}
	
	private void loadFile() {
		// TODO Auto-generated method stub
		
	}

	//싱글톤을 가져다가 만들 수 있음
	public static IUserManager getInstance() {
		return instance;
	}
	//회원 찾기
	public User find(String id) {
		for (User user : userList) {
			if(user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}
	
	//회원 가입
	@Override
	public void join(User user) {
		userList.add(user);
		saveFile();
	}
	
	private void saveFile() {
		// TODO Auto-generated method stub
		
	}

	//회원 정보 수정
	public User fix(User user) {
		return user;
		
	}
	
	//등록된 모든 유저 반환
	@Override
	public User[] getList() {
		User[] users = new User[userList.size()];
		return userList.toArray(users);
	}
	


}
