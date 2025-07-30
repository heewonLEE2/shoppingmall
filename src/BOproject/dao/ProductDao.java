package BOproject.dao;

import java.sql.SQLException;
import java.util.List;

import BOproject.model.ProductVO;

public interface ProductDao {

	public abstract List<ProductVO> listProduct() throws SQLException;
	
	public abstract ProductVO getProduct(int pid) throws SQLException;
	
	public abstract int registProduct(ProductVO product) throws SQLException;
	
	public abstract int modifyProduct(ProductVO product) throws SQLException;
	
	public abstract int removeProduct(int pid) throws SQLException;
}
