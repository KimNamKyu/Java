import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class Mybatis {

	public static void main(String[] args) {
		System.out.println("Mybatis Start!");
		
		try 
		{
			String resource = "mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			SqlSession session = sqlSessionFactory.openSession(); //connection정보 Open 과 같다.
			List<HashMap<String,Object>> list = null;
			try {
			  list = session.selectList("TestMapper.select");
			} finally {
			  session.close();
			}
			
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
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

}
