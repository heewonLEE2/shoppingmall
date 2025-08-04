package BOproject.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BOproject.dao.ProductDao;
import BOproject.model.ProductVO;
import BOproject.model.UserVO;
import BOproject.util.ConnectionUtil;

public class ProductDaoImpl implements ProductDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ProductDaoImpl() {
	}

	@Override
	public List<ProductVO> listProduct() throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " SELECT pid, pname, pprice, pcontent, pimgurl, plikecount, cid FROM bo.PRODUCT ";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<ProductVO> productList = new ArrayList<ProductVO>();
		if (rs != null) {
			while (rs.next()) {
				ProductVO product = new ProductVO();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setPprice(rs.getInt("pprice"));
				product.setPcontent(rs.getString("pcontent"));
				product.setPimgUrl(rs.getString("pimgurl"));
				product.setPlikeCount(rs.getInt("plikecount"));
				product.setCid(rs.getString("cid"));
				productList.add(product);
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return productList;
	}

	@Override
	public ProductVO getProduct(int pid) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " SELECT pid, pname, pprice, pcontent, pimgurl, plikecount, cid FROM bo.PRODUCT where pid=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pid);
		rs = pstmt.executeQuery();
		ProductVO product = new ProductVO();
		if (rs != null) {
			if (rs.next()) {
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setPprice(rs.getInt("pprice"));
				product.setPcontent(rs.getString("pcontent"));
				product.setPimgUrl(rs.getString("pimgurl"));
				product.setPlikeCount(rs.getInt("plikecount"));
				product.setCid(rs.getString("cid"));
			}
		}
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return product;
	}

	@Override
	public int registProduct(ProductVO product) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " insert into bo.PRODUCT values(SEQ_PRODUCT.NEXTVAL, ?, ?, ?, ?, 0, ?) ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, product.getPname());
		pstmt.setInt(2, product.getPprice());
		pstmt.setString(3, product.getPcontent());
		pstmt.setString(4, product.getPimgUrl());
		pstmt.setString(5, product.getCid());
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public int modifyProduct(ProductVO product) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " update bo.PRODUCT set pname=?, pprice=?, pcontent=?, PLIKECOUNT=? where pid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, product.getPname());
		pstmt.setInt(2, product.getPprice());
		pstmt.setString(3, product.getPcontent());
		pstmt.setInt(4, product.getPlikeCount());
		pstmt.setInt(5, product.getPid());
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}

	@Override
	public int removeProduct(int pid) throws SQLException {
		conn = ConnectionUtil.getConnectionUtil().getConnection();
		String sql = " delete bo.PRODUCT where pid=? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pid);
		int num = pstmt.executeUpdate();
		ConnectionUtil.getConnectionUtil().closeAll(rs, pstmt, conn);
		return num;
	}
}
