package board;

import java.sql.*;
import java.util.*;

// git test
public class BoardDAO {
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private BoardDAO() {}
	
	public static Connection getConnection() throws Exception{
		Connection con = null;
		
		try {
			String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbId = "scott";
			String dbPass = "1111";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	public void insertArticle(BoardDTO article, String boardid) throws Exception{
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//1
		int num = article.getNum();
		int ref = article.getRef();
		int re_step = article.getRe_step();
		int re_level = article.getRe_level();
		
		//1 ��۾���
		int number = 0;
		String sql = "";
		
		try {
			pstmt = conn.prepareStatement("select boardser.nextval from dual");
			rs = pstmt.executeQuery();
			
			if(rs.next())
				number = rs.getInt(1) + 1;
			else
				number = 1;
			
			//2 
			if(num != 0) {	// ��۾��� =============
				sql = "update board set re_step = re_step + 1 "
						+ "where ref = ? and re_step> ? and boardid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, re_step);
				pstmt.setString(3, boardid);
				pstmt.executeUpdate();
				re_step = re_step + 1;
				re_level = re_level + 1;
			} else { // 2 : ��۾��� ==================
				ref = number; // �� ��
				re_step = 0;
				re_level = 0;
			} // ============
			
			sql = "insert into board (num, writer, email, subject, passwd, reg_date,";
			sql += "ref, re_step, re_level, content, ip, boardid, filename, filesize)"
					+ "values (?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, article.getWriter());
			pstmt.setString(3, article.getEmail());
			pstmt.setString(4, article.getSubject());
			pstmt.setString(5, article.getPasswd());
			pstmt.setInt(6, ref);
			pstmt.setInt(7, re_step);
			pstmt.setInt(8, re_level);
			pstmt.setString(9, article.getContent());
			pstmt.setString(10, article.getIp());
			pstmt.setString(11, boardid);
			pstmt.setString(12, article.getFilename());
			pstmt.setInt(13, article.getFilesize());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(conn, pstmt, rs);
		}
	}
	
	
	public int getArticleCount(String boardid, String category, String sentence) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		int x = 0;
		String sql = "";
		
		try {
			if(category == null || category.equals("")) { //get list
				sql = "select nvl(count(*),0) from board where boardid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, boardid);
			} else {	// <form post category �Է�
				sql = "select nvl(count(*),0) from board where boardid= ? and" + category + "like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,  boardid);
				pstmt.setString(2, "%" + sentence + "%");
				}
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(conn, pstmt, rs);
		}
		return x;
	}
	
	
	public List getArticles(int start, int end, String boardid, String category, String sentence) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List articleList = null;
		String query = "";
		
		try {
			conn = getConnection();
			if(category == null || category.equals("")) { 		//get list
				query = " select * from " + " ( select rownum rnum , a.* "
			            + " from ( select * from board where boardid = ? order by ref desc , re_step)" 
	                     + "a) where rnum between ? and ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,  boardid);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			} else {		// <form post category ��
				 query = " select * from " + "( select rownum rnum , a.* "
				            + " from (select * from board where boardid = ? and " + category 
				            + " like ? order by ref desc , re_step) " + " a)"
				            + " where rnum between ? and ? ";

				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, boardid);
				pstmt.setString(2, "%" + sentence + "%");
				pstmt.setInt(3, start);
				pstmt.setInt(4, end);
			}
			rs = pstmt.executeQuery();
				
			if(rs.next()) {
				articleList = new ArrayList(end);
				
				do {
					BoardDTO article = new BoardDTO();
					article.setNum(rs.getInt("num"));
	                  article.setWriter(rs.getString("writer"));
	                  article.setEmail(rs.getString("email"));
	                  article.setSubject(rs.getString("subject"));
	                  article.setPasswd(rs.getString("passwd"));
	                  article.setReg_date(rs.getTimestamp("reg_date"));
	                  article.setReadcount(rs.getInt("readcount"));
	                  article.setRef(rs.getInt("ref"));
	                  article.setRe_step(rs.getInt("re_step"));
	                  article.setRe_level(rs.getInt("re_level"));
	                  article.setContent(rs.getString("content"));
	                  article.setIp(rs.getString("ip"));
	                  article.setFilename(rs.getString("filename"));
	                 
					
	                  articleList.add(article);
				} while(rs.next());
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(conn, pstmt, rs);
		}
		return articleList;
	}
	
	
	
	
	
	public BoardDTO getArticle(int num, String boardid, boolean readcount) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO article = null;
		
		try {
			conn = getConnection();
			if(readcount) {  //false
			pstmt = conn.prepareStatement("update board set readcount=readcount+1 "
					+ "where num = ? and boardid =?");
			pstmt.setInt(1, num);;
			pstmt.setString(2, boardid);
			pstmt.executeUpdate();
			}else {
			pstmt = conn.prepareStatement("select * from board where num = ? and boardid =?");
			pstmt.setInt(1, num);
			pstmt.setString(2, boardid);
			rs = pstmt.executeQuery();
			}
			
			if(rs.next()) {
				article = new BoardDTO();
				article.setNum(rs.getInt("num"));
                article.setWriter(rs.getString("writer"));
                article.setEmail(rs.getString("email"));
                article.setSubject(rs.getString("subject"));
                article.setPasswd(rs.getString("passwd"));
                article.setReg_date(rs.getTimestamp("reg_date"));
                article.setReadcount(rs.getInt("readcount"));
                article.setRef(rs.getInt("ref"));
                article.setRe_step(rs.getInt("re_step"));
                article.setRe_level(rs.getInt("re_level"));
                article.setContent(rs.getString("content"));
                article.setIp(rs.getString("ip"));
                article.setFilename(rs.getString("filename"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(conn, pstmt, rs);
		}
		return article;
	}
	
	
	public int updateArticle(BoardDTO article, String boardid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpasswd = "";
		String sql = "";
		int x = -1;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select passwd from board where num = ?");
			pstmt.setInt(1, article.getNum());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpasswd = rs.getString("passwd");
				if(dbpasswd.equals(article.getPasswd())) {
					sql = "update board set writer = ? , email = ? , subject = ? , passwd = ?";
					sql += ", content = ? , filename = ? , filesize = ? where num = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, article.getWriter());
					pstmt.setString(2, article.getEmail());
					pstmt.setString(3, article.getSubject());
					pstmt.setString(4, article.getPasswd());
					pstmt.setString(5, article.getContent());
					pstmt.setString(6, article.getFilename());
					pstmt.setInt(7, article.getFilesize());
					pstmt.setInt(8, article.getNum());
					
					pstmt.executeUpdate();
					x = 1;
				} else {
					x = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(conn, pstmt, rs);
		}
		return x;
	}
	
	
	public int deleteArticle(int num, String passwd, String boardid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpasswd = "";
		String sql = "";
		int x = -1;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select passwd from board where num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpasswd = rs.getString("passwd");
				if(dbpasswd.equals(passwd)) {
					sql = "delete from board where num = ?";
					// sql = "delete from board where ref = ?";   // ��۱��� �Բ� ����
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num);
					
					pstmt.executeUpdate();
					
					x = 1;
				} else {
					x = 0;		//��й�ȣ Ʋ��
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(conn, pstmt, rs);
		}
		return x;
	}
}
