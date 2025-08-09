package BOproject.dao.test;

import java.sql.SQLException;

import BOproject.service.ProductService;
import BOproject.service.impl.ProductServiceImpl;
import BOproject.util.ConnectionUtil;

public class ProductTestMain {

	public static void main(String[] args) {

		ProductService productService = new ProductServiceImpl();

		try {
			// productService.listProduct().stream().forEach(System.out::println);
			System.out.println(productService.getProduct(25));
			// System.out.println(productService.getProduct(25));
//			productService.registProduct(new ProductVO(0,"쿠로미옷",
//					59000,
//					"귀여움을 강조하는 옷",
//					"https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA1MTdfMjQ0%2FMDAxNjg0MzI5MDIxNTI0.vGj9IocD-yh6SNgkzD3deTEoK60TI1qMgGGJY7kfinEg._Hzy_iAlYS5BIySovr5VL_Cm0cH0kuAA_bMt667xsoog.PNG.broken7137%2Fimage.png&type=sc960_832",
//					0,
//					"0101,0202,0302"));
			// productService.ListProductLowPrice("101,201").forEach(System.out::println);
			productService.ListProductHighPrice("101,202").forEach(System.out::println);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

	}
}
