package com.yedam.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemDAO extends DAO {
	
	// 상품 업로드
	public ItemVO uploadProduct(ItemVO vo) {
		connect();
		String sql = "insert into item(prod_id, prod_item, prod_desc, like_it, origin_price, sale_price, prod_image)"
				+ "values(?, ?, ?, ?, ?, ?, ?)";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(prod_id) + 1 from item");
			int nextId = -1;
			if(rs.next()) {
				nextId = rs.getInt(1); // 첫 번째 칼럼.
				vo.setProdId(nextId); // 매개값의 참조변수의 값을 변경
			}
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, nextId);
			psmt.setString(2, vo.getProdItem());
			psmt.setString(3,  vo.getProdDesc());
			psmt.setDouble(4, vo.getLikeIt());
			psmt.setInt(5, vo.getOriginPrice());
			psmt.setInt(6, vo.getSalePrice());
			psmt.setString(7, vo.getProdImage());
			int r = psmt.executeUpdate(); // 실제 쿼리 실행
			System.out.println(r + "건 입력"); // 처리 후 메시지 출력
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		return vo;
	}
	
	
	
	// 占쏙옙占쏙옙占쏙옙 占쏙옙占� 占쌨소듸옙(占신곤옙占쏙옙占쏙옙 title, start, end)
	public boolean addSchedule(String title, String start, String end) {
		connect();
		String sql = "insert into schedule values(?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, start);
			psmt.setString(3, end);
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true; // true占쏙옙 1占쏙옙 占쌉뤄옙
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return false; // 占쌉력듸옙占쏙옙 占쏙옙占쏙옙占쏙옙 false 占쏙옙占쏙옙
	}
	
	// fullCalendar占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙
	// List<Map<String, String>> 
	public List<Map<String, String>> getSchedule() {
		List<Map<String, String>> schedules = new ArrayList<Map<String, String>>();
		connect();
		String sql = "select * from schedule";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", rs.getString("title"));
				map.put("start", rs.getString("start_date"));
				map.put("end", rs.getString("end_date"));
				schedules.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return schedules;
	}
	
	// 占싸쇽옙占쏙옙 占싸울옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쌨소듸옙
	public Map<String, Integer> getMemberByDept() { // 키 : 占싸쇽옙 / Integer : 占싸울옙
		connect();
		String sql = "select 'Administration', 1 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Accounting', 2 from dual\r\n"
				+ "union all\r\n"
				+ "select 'IT', 3 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Executive', 3 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Shipping', 5 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Sales', 3 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Marketing', 2 from dual";
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占� rs占쏙옙 占쏙옙占�
			while(rs.next()) {
				map.put(rs.getString(1), rs.getInt(2)); // getString타占쏙옙占쏙옙 첫 占쏙옙째 칼占쏙옙, int타占쏙옙占쏙옙 占쏙옙 占쏙옙째 칼占쏙옙
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return map;
	}

	// 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙탈 占실쇽옙 占쏙옙占쏙옙占쏙옙占쏙옙
	public int getTotalCount() {
		connect();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(1) from member");
			if (rs.next()) {
				int r = rs.getInt(1);
				System.out.println("占쏙옙회占실쇽옙: " + r);
				return r;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return 0;
	}

	// 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙회
	public List<MemberVO> getMemberByPage(String page) {
		connect();
		List<MemberVO> list = new ArrayList<>();
		String sql = "select b.* from\r\n" //
				+ "(select rownum as num,  a.* from\r\n"//
				+ "(select * from member order by 1) a) b\r\n"//
				+ "where b.num >= ?\r\n"//
				+ "and    b.num <= ?";
		int start = (Integer.parseInt(page) - 1) * 10 + 1;
		int end = Integer.parseInt(page) * 10;

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);

			rs = psmt.executeQuery();
			while (rs.next()) {
				MemberVO mem = new MemberVO();
				mem.setAddress(rs.getString("address")); // address 칼占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙占쏙옙占� vo占쏙옙 占쏙옙占�
				mem.setBirthDate(rs.getString("birth_date"));
				mem.setGender(rs.getString("gender"));
				mem.setPhone(rs.getString("phone"));
				mem.setUserId(rs.getString("user_id"));
				mem.setUserName(rs.getString("user_name"));
				list.add(mem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 占쏙옙회占쏙옙占�
	public List<MemberVO> searchMemberList(MemberVO vo) {
		connect();
		List<MemberVO> list = new ArrayList<>();
		String sql = "select * from member m\r\n" //
				+ "where user_id = nvl(?, user_id)\r\n"//
				+ "and    nvl(user_name, '1') like '%'||?||'%'\r\n" //
				+ "and    nvl(address, '1') like '%'||?||'%'\r\n" //
				+ "and    nvl(phone, '1') like '%'||?||'%'";
		if (vo.getGender() != null //
				&& !vo.getGender().equals("all") //
				&& vo.getGender() != "") { // all占쏙옙 占쏙옙占쏙옙占쏙옙 gender占쏙옙占� 占쏙옙占쏙옙 占싣울옙 占쏙옙 占쌍몌옙 占쏙옙
			sql += "and   gender = ?";
		}
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserId());
			psmt.setString(2, vo.getUserName());
			psmt.setString(3, vo.getAddress());
			psmt.setString(4, vo.getPhone());
			if (vo.getGender() != null //
					&& !vo.getGender().equals("all") //
					&& vo.getGender() != "") {
				psmt.setString(5, vo.getGender());
			}
			rs = psmt.executeQuery();
			while (rs.next()) {
				MemberVO mem = new MemberVO();
				mem.setAddress(rs.getString("address")); // address 칼占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙占쏙옙占� vo占쏙옙 占쏙옙占�
				mem.setBirthDate(rs.getString("birth_date"));
				mem.setGender(rs.getString("gender"));
				mem.setPhone(rs.getString("phone"));
				mem.setUserId(rs.getString("user_id"));
				mem.setUserName(rs.getString("user_name"));
				list.add(mem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 占쏙옙占쏙옙占쏙옙占�
	public Map<String, Object> updateMember(MemberVO vo) {
		// retCode:OK, retVal: vo
		// retCode:NG, retVal: errMsg
		String sql = "update member";
		sql += "      set    user_name = ?";
		sql += "            ,birth_date = ?";
		sql += "            ,gender = ?";
		sql += "            ,address = ?";
		sql += "            ,phone = ?";
		sql += "      where user_id = ?"; // 占쏙옙占쏙옙占쏙옙트 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", "NG");
		map.put("retVal", "Error!!");

		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserName()); // 占쏙옙占쏙옙 占식띰옙占쏙옙沽占� 占쏙옙 占쌩곤옙
			psmt.setString(2, vo.getBirthDate());
			psmt.setString(3, vo.getGender());
			psmt.setString(4, vo.getAddress());
			psmt.setString(5, vo.getPhone());
			psmt.setString(6, vo.getUserId());
			int r = psmt.executeUpdate(); // 占쏙옙占쏙옙
			System.out.println(r + "占쏙옙 占쏙옙占쏙옙");
			if (r > 0) { // db占쏙옙 占쏙옙占쏙옙占실몌옙 1占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
				map.put("retCode", "OK");
				map.put("retVal", vo);
				return map;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			map.put("retVal", e.getMessage()); // 占쏙옙占쌤곤옙 占쏙옙占쏙옙占쏙옙 占쌍댐옙 占쏙옙占쏙옙占쌨쏙옙占쏙옙
			return map;

		} finally {
			disconnect();
		}
		return map;
	}

	// 占쏙옙 占쏙옙 占쏙옙占쏙옙
	public boolean deleteMember(String id) {
		String sql = "delete from member where user_id = ?";
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			int r = psmt.executeUpdate();
			System.out.println(r + "占쏙옙 占쏙옙占쏙옙.");
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false; // 0占쏙옙 占쏙옙占싹되몌옙 false 占싼억옙占�
	}

	// 占쏙옙 占쏙옙 占쏙옙회占싹댐옙 占쏙옙占�
	public MemberVO getMember(String id) {
		String sql = "select * from member where user_id = ?";
		connect();
		MemberVO vo = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if (rs.next()) {
				vo = new MemberVO();
				vo.setAddress(rs.getString("address")); // address 칼占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙占쏙옙占� vo占쏙옙 占쏙옙占�
				vo.setBirthDate(rs.getString("birth_date"));
				vo.setGender(rs.getString("gender"));
				vo.setPhone(rs.getString("phone"));
				vo.setUserId(rs.getString("user_id"));
				vo.setUserName(rs.getString("user_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnect();
		}
		return vo; // 占쏙옙占쏙옙 占쌩삼옙占싹몌옙 null占쏙옙
	}

	// 占쏙옙 占쏙옙 占쌉뤄옙占싹댐옙 占쏙옙占�
	public boolean insertMember(MemberVO vo) {
		String sql = "insert into member (user_id,user_name,address,phone,birth_date,gender)" + " values(?,?,?,?,?,?)";
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserId());
			psmt.setString(2, vo.getUserName());
			psmt.setString(3, vo.getAddress());
			psmt.setString(4, vo.getPhone());
			psmt.setString(5, vo.getBirthDate());
			psmt.setString(6, vo.getGender());

			int r = psmt.executeUpdate();
			System.out.println(r + "占쏙옙 占쌉력듸옙.");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	// 占쏙옙체 占쏙옙占쏙옙트
	public List<MemberVO> getMemberList() {
		String sql = "select * from member order by 1"; // 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙占쏙옙占쏙옙
		connect(); // 占쏙옙達騁티占� 占쏙옙占쏙옙 占신니깍옙 占쏙옙 占쏙옙 占쏙옙占쏙옙
		List<MemberVO> memberList = new ArrayList<>();
		try {
			stmt = conn.createStatement(); // Statement stmt = new Statement();
			rs = stmt.executeQuery(sql); // 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙占싶쇽옙 rs占쏙옙 占쏙옙占쏙옙殮占�
			while (rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setAddress(rs.getString("address")); // address 칼占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙占쏙옙占� vo占쏙옙 占쏙옙占�
				vo.setBirthDate(rs.getString("birth_date"));
				vo.setGender(rs.getString("gender"));
				vo.setPhone(rs.getString("phone"));
				vo.setUserId(rs.getString("user_id"));
				vo.setUserName(rs.getString("user_name"));
				if (memberList.size() == 10)
					break;
				memberList.add(vo); // 占쏙옙占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙占쏙옙 占실쇽옙占쏙옙큼 占쏙옙占쏙옙 占쏙옙占썽서 占쏙옙 占쏙옙占쏙옙占�
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return memberList;
	}

	public List<ItemVO> getItemList() {
		connect();
		List<ItemVO> itemList = new ArrayList<>();
		String sql = "select * from item";
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ItemVO itemvo = new ItemVO();
				itemvo.setProdId(rs.getInt("prod_id"));
				itemvo.setProdItem(rs.getString("prod_item"));
				itemvo.setProdDesc(rs.getString("prod_desc"));
				itemvo.setLikeIt(rs.getDouble("like_it"));
				itemvo.setOriginPrice(rs.getInt("origin_price"));
				itemvo.setSalePrice(rs.getInt("sale_price"));
				itemvo.setProdImage(rs.getString("prod_image"));
				
				itemList.add(itemvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return itemList;
	}
}
