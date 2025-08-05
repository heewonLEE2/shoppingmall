package BOproject.service.impl;

import java.sql.SQLException;


import java.util.List;

import BOproject.dao.ProductDao;
import BOproject.dao.impl.ProductDaoImpl;
import BOproject.dao.impl.UserDaoImpl;
import BOproject.model.ProductVO;
import BOproject.model.UserVO;
import BOproject.service.ProductService;

public class ProductServiceImpl implements ProductService{
	
	ProductDao productDao;
	
	public ProductServiceImpl() {
		productDao = new ProductDaoImpl();
	}
	
	@Override
	public List<ProductVO> listProduct() throws SQLException {
		return productDao.listProduct();
	}
	
	@Override
	public ProductVO getProduct(int pid) throws SQLException {
		return productDao.getProduct(pid);
	}
	@Override
	public int registProduct(ProductVO product) throws SQLException {
		return productDao.registProduct(product);
	}
	
	@Override
	public int modifyProduct(ProductVO product) throws SQLException {
		return productDao.modifyProduct(product);
	}
	
	@Override
	public int removeProduct(int pid) throws SQLException {
		return productDao.removeProduct(pid);
	}
	
	@Override
	public List<ProductVO> betweenListProduct(String cid, int price1, int price2) throws SQLException {
		return productDao.betweenListProduct(cid, price1, price2);
	}
	
	@Override
	public List<ProductVO> ListProductLowPrice(String cid) throws SQLException {
		return productDao.ListProductLowPrice(cid);
	}
	
	@Override
	public List<ProductVO> ListProductHighPrice(String cid) throws SQLException {
		return productDao.ListProductHighPrice(cid);
	}
	
}












