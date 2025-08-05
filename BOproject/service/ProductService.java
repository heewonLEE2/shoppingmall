package BOproject.service;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.ProductVO;

public interface ProductService {

	public abstract List<ProductVO> listProduct() throws SQLException;
	
	public abstract ProductVO getProduct(int pid) throws SQLException;
	
	public abstract int registProduct(ProductVO product) throws SQLException;
	
	public abstract int modifyProduct(ProductVO product) throws SQLException;
	
	public abstract int removeProduct(int pid) throws SQLException;
	
	public abstract List<ProductVO> betweenListProduct(String cid, int price1, int price2) throws SQLException;
	
	public abstract List<ProductVO> ListProductLowPrice(String cid) throws SQLException;
	
	public abstract List<ProductVO> ListProductHighPrice(String cid) throws SQLException;

}
