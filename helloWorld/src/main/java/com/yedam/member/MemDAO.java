package com.yedam.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemDAO extends DAO {
	// getItemList() - GetItemServlet
	public List<ItemVO> getItemList(ItemVO vo) {
		connect();
		List<ItemVO> Itemlist = new ArrayList<>();
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
				
				Itemlist.add(itemvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return Itemlist;
	}
	
	// ������ ��� �޼ҵ�(�Ű����� title, start, end)
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
				return true; // true�� 1�� �Է�
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return false; // �Էµ��� ������ false ����
	}
	
	// fullCalendar�� ���� ������
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
	
	// �μ��� �ο� ���� �������� �޼ҵ�
	public Map<String, Integer> getMemberByDept() { // Ű : �μ� / Integer : �ο�
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
			rs = stmt.executeQuery(sql); // ������ ����� rs�� ���
			while(rs.next()) {
				map.put(rs.getString(1), rs.getInt(2)); // getStringŸ���� ù ��° Į��, intŸ���� �� ��° Į��
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return map;
	}

	// �������� ��Ż �Ǽ� ��������
	public int getTotalCount() {
		connect();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(1) from member");
			if (rs.next()) {
				int r = rs.getInt(1);
				System.out.println("��ȸ�Ǽ�: " + r);
				return r;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return 0;
	}

	// �������� ������ ��ȸ
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
				mem.setAddress(rs.getString("address")); // address Į���� ��� ������� vo�� ���
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

	// ��ȸ���
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
				&& vo.getGender() != "") { // all�� ������ gender��� ���� �ƿ� �� �ָ� ��
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
				mem.setAddress(rs.getString("address")); // address Į���� ��� ������� vo�� ���
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

	// �������
	public Map<String, Object> updateMember(MemberVO vo) {
		// retCode:OK, retVal: vo
		// retCode:NG, retVal: errMsg
		String sql = "update member";
		sql += "      set    user_name = ?";
		sql += "            ,birth_date = ?";
		sql += "            ,gender = ?";
		sql += "            ,address = ?";
		sql += "            ,phone = ?";
		sql += "      where user_id = ?"; // ������Ʈ ���� ���� ��

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", "NG");
		map.put("retVal", "Error!!");

		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserName()); // ���� �Ķ���Ϳ� �� �߰�
			psmt.setString(2, vo.getBirthDate());
			psmt.setString(3, vo.getGender());
			psmt.setString(4, vo.getAddress());
			psmt.setString(5, vo.getPhone());
			psmt.setString(6, vo.getUserId());
			int r = psmt.executeUpdate(); // ����
			System.out.println(r + "�� ����");
			if (r > 0) { // db�� �����Ǹ� 1�� ��������
				map.put("retCode", "OK");
				map.put("retVal", vo);
				return map;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			map.put("retVal", e.getMessage()); // ���ܰ� ������ �ִ� �����޽���
			return map;

		} finally {
			disconnect();
		}
		return map;
	}

	// �� �� ����
	public boolean deleteMember(String id) {
		String sql = "delete from member where user_id = ?";
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			int r = psmt.executeUpdate();
			System.out.println(r + "�� ����.");
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false; // 0�� ���ϵǸ� false �Ѿ��
	}

	// �� �� ��ȸ�ϴ� ���
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
				vo.setAddress(rs.getString("address")); // address Į���� ��� ������� vo�� ���
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
		return vo; // ���� �߻��ϸ� null��
	}

	// �� �� �Է��ϴ� ���
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
			System.out.println(r + "�� �Էµ�.");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	// ��ü ����Ʈ
	public List<MemberVO> getMemberList() {
		String sql = "select * from member order by 1"; // ������������ ��� ��������
		connect(); // ��ӹ޾Ƽ� ���� �Ŵϱ� �� �� ����
		List<MemberVO> memberList = new ArrayList<>();
		try {
			stmt = conn.createStatement(); // Statement stmt = new Statement();
			rs = stmt.executeQuery(sql); // ���� ������ ��� �����ͼ� rs�� ����ֱ�
			while (rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setAddress(rs.getString("address")); // address Į���� ��� ������� vo�� ���
				vo.setBirthDate(rs.getString("birth_date"));
				vo.setGender(rs.getString("gender"));
				vo.setPhone(rs.getString("phone"));
				vo.setUserId(rs.getString("user_id"));
				vo.setUserName(rs.getString("user_name"));
				if (memberList.size() == 10)
					break;
				memberList.add(vo); // ������ �� ������ �Ǽ���ŭ ���� ���鼭 �� �����
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return memberList;
	}
}
