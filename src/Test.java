import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Test {

	public void main(String[] args) {
		System.out.println("자바 데이터베이스 연동!");
		String url = "jdbc:mysql://192.168.3.115:3306/test";
		String user = "root";
		String password = "1234";
		
		//Connection 만들기 전에 라이브러리가 있는지 확인해야함.
		try 
		{
			Class.forName("org.mariadb.jdbc.Driver");	// Jdbc 라브러리 class 존재여부 확인
			Connection conn = DriverManager.getConnection(url,user,password);	// Connection정보 완성 => 만든후 담아야한다.
			Statement cmd = conn.createStatement();
			ResultSet rs = cmd.executeQuery("select * from Member");
			
			ArrayList list = new ArrayList();
			while(rs.next()) {
				//명시적인 사용방법
				//int mNo = rs.getInt("mNo"); 
				//String mId = rs.getString("mId");
				//String mPass = rs.getString("mPass");
				//String mName = rs.getString("mName");
				//System.out.println("mNo : " + mNo + ",mId : " + mId + ",mPass : " + mPass + ", mName" + mName);
				
				ResultSetMetaData rsmd = rs.getMetaData();
				HashMap<String, Object>map = new HashMap<String,Object>();
				for(int i = 1; i<=rsmd.getColumnCount(); i++) {	//DB에서 index 값은 1부터 시작하므로
					//System.out.print(rsmd.getColumnName(i) + " : " + rs.getObject(i));
					map.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
				//System.out.println();
			}
			
			//배열에 담고 꺼내기
			for(int i = 0; i<list.size(); i++) {
				//HashMap = HashTable 순서가없는것이 동일하다.
				HashMap<String,Object>map = (HashMap<String,Object>)list.get(i);	
				Set set = map.keySet();
				Iterator it = set.iterator();
				//while 문은 boolean이 와야하므로 return 값이 boolean 인 hasNext() 사용
				while(it.hasNext()) {
					String key = it.next().toString();
					map.get(key); // = value 를 의미 					
					System.out.print(key + " : " + map.get(key));
				}
				System.out.println("");
			}
			
			System.out.println("접속 성공!");
			
		} 
		catch (Exception e) 
		{
			System.out.println("접속 오류!");
			e.printStackTrace(); //오류발생시 콘솔창에 출력
		}
	}

}
