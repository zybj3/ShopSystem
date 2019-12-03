package com.osu.dianping.web.frontend;

import com.osu.dianping.entity.Product;
import com.osu.dianping.service.ProductService;
import com.osu.dianping.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 你爹
 * @version V1.0
 * @program: dianping
 * @Package com.osu.dianping.web.frontend
 * @Description: TODO
 * @date 2019-11-28 14:04
 */

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/productdetail")
    private String productDetailPage(){
        return "frontend/productdetail";
    }

    @RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductDetailInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        Product product = null;
        if (productId != -1L){
            product = productService.getProductById(productId);
            modelMap.put("product", product);
            modelMap.put("success", true);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "productId is null");
        }

        return modelMap;
    }
}
