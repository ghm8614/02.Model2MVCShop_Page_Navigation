package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {

	// C
	public ProductDAO() {

	}

	public Product findProduct(int prodNo) throws Exception {

		System.out.println("======================================");
		System.out.println("findProduct()");
		System.out.println("prodNo: " + prodNo);

		Connection con = DBUtil.getConnection();
		System.out.println("1. RDBMS 드라이버 연결 성공 및 계정 접속 성공");

		String sql = "SELECT * FROM product WHERE prod_no = ?";

		System.out.println("2. 쿼리객체 생성");
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, prodNo);

		System.out.println("3. 쿼리 전송 및 data 가져오기");
		ResultSet rs = pstmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			System.out.println("rs.next = true");

			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));

			System.out.println("4. data 가져오기 성공");
		}
		System.out.println(product);
		System.out.println("======================================");

		con.close();

		return product;
	}

	public Map<String, Object> getProductList(Search search) throws Exception {

		System.out.println("======================================");
		System.out.println("getProductList(search search)");
		System.out.println("dao에서의 " + search);

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();
		System.out.println("1. RDBMS 드라이버 연결 성공 및 계정 접속 성공");

		String sql = "SELECT P.prod_no, P.prod_name, P.prod_detail, P.manufacture_day, P.price, P.image_file, P.reg_date, T.tran_status_code FROM product P, transaction T WHERE P.prod_no = T.prod_no(+) ";

		// 검색 버튼 클릭시, listProduct.do
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += "AND P.prod_no = '" + search.getSearchKeyword() + "'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += "AND P.prod_name LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += "AND P.price = '" + search.getSearchKeyword() + "'";
			}
		}
		System.out.println("sql : "+sql);

		// 검색창 null 이면, 단순 모든 삼품 목록 조회
		sql += " ORDER BY P.prod_no";

		// db에서 꺼내온 data의 전체 recode 수   
		int totalCount = this.getTotalCount(sql); // 18개

		//
		sql = makeCurrentPageSql(sql, search);

		System.out.println("2. 쿼리객체 생성");
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		// 쿼리를 db에 질의하고 값을 ResultSet 객체로 전달받음
		System.out.println("3. 쿼리 전송 및 data 가져오기");
		ResultSet rs = pstmt.executeQuery();

		List<Product> list = new ArrayList<Product>();

		while (rs.next()) {
			Product product = new Product();

			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			product.setProTranCode(rs.getString("tran_status_code")); // null : 판매중, 1 : 구매완료
			
			list.add(product);
			System.out.println("4. data 가져오기 성공");
		}
		// totalCount
		map.put("totalCount", new Integer(totalCount));

		map.put("list", list);
		
		System.out.println("======================================");

		rs.close();
		pstmt.close();
		con.close();

		return map;
	}

	
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) FROM (" + sql + ")";

		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		rs.close();
		pstmt.close();
		con.close();

		return totalCount;
	}

	private String makeCurrentPageSql(String sql, Search search) {

		sql = 	"SELECT * "+ 
				"FROM (		SELECT vt. * ,  ROWNUM AS row_seq " +
				" 	FROM (	"+sql+" ) vt "+
				"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}

	public void insertProduct(Product product) throws Exception {

		System.out.println("======================================");

		System.out.println(product);

		Connection con = DBUtil.getConnection();
		System.out.println("1. RDBMS 드라이버 연결 성공 및 계정 접속 성공");

		String sql = "INSERT INTO product VALUES(seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";

		System.out.println("2. 쿼리객체 생성");
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, product.getProdName());
		pstmt.setString(2, product.getProdDetail());
		pstmt.setString(3, product.getManuDate().replace("-", ""));
		pstmt.setInt(4, product.getPrice());
		pstmt.setString(5, product.getFileName());

		System.out.println("3. 쿼리 전송");
		int result = pstmt.executeUpdate();

		if (result == 1) {
			System.out.println("4. 쿼리 전송 성공");
		}

		System.out.println("======================================");

		pstmt.close();
		con.close();
	}

	public void updateProduct(Product product) throws Exception {

		System.out.println("======================================");
		System.out.println("updateProduct()");
		System.out.println(product);

		Connection con = DBUtil.getConnection();
		System.out.println("1. RDBMS 드라이버 연결 성공 및 계정 접속 성공");

		System.out.println("2. 쿼리객체 생성");
		String sql = "UPDATE product SET PROD_NAME = ?, PROD_DETAIL=?, MANUFACTURE_DAY=?, PRICE=?, IMAGE_FILE=? WHERE PROD_NO = ?";

		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, product.getProdName());
		pstmt.setString(2, product.getProdDetail());
		pstmt.setString(3, product.getManuDate());
		pstmt.setInt(4, product.getPrice());
		pstmt.setString(5, product.getFileName());
		pstmt.setInt(6, product.getProdNo());

		System.out.println("3. 쿼리 전송");
		int result = pstmt.executeUpdate();

		if (result == 1) {
			System.out.println("4. 쿼리 전송 성공");
		}
		
		pstmt.close();
		con.close();
	}

}
