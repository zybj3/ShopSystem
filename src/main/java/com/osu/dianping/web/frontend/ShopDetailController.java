package com.osu.dianping.web.frontend;


import com.osu.dianping.dto.ProductExecution;
import com.osu.dianping.entity.Product;
import com.osu.dianping.entity.ProductCategory;
import com.osu.dianping.entity.Shop;
import com.osu.dianping.service.ProductCategoryService;
import com.osu.dianping.service.ProductService;
import com.osu.dianping.service.ShopService;
import com.osu.dianping.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/shopdetail")
    private String shopDetail(){
        return "frontend/shopdetail";
    }

    @RequestMapping(value = "/listshopdetailinfo", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> listShopPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopID = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = new ArrayList<>();
        if (shopID != -1) {
            shop = shopService.getByShopId(shopID);
            productCategoryList = productCategoryService.queryProductCategoryListByShopId(shopID);

            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "no shop selected");
        }

        return modelMap;
    }

    @RequestMapping(value = "/listproductsbyshopid", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> listProductsByShopId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = (int) HttpServletRequestUtil.getLong(request, "pageIndex");
        int pageSize = (int) HttpServletRequestUtil.getLong(request, "pageSize");

        long shopId = HttpServletRequestUtil.getLong(request, "shopId");

        String enableStatus = request.getParameter("enableStatus");
//        System.out.println(enableStatus);
        if (pageIndex > -1 && pageSize > -1 && shopId > -1) {
            Product productCondition = new Product();
            long productCategoryId = HttpServletRequestUtil.getKey(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Shop shop = new Shop();
            shop.setShopId(shopId);
            productCondition.setShop(shop);

            if (enableStatus != null) {
                productCondition.setEnableStatus(Integer.parseInt(enableStatus));
            }

            if (productCategoryId>-1){
                ProductCategory productCategory = new ProductCategory();
                productCategory.setProductCategoryId(productCategoryId);
                productCondition.setProductCategory(productCategory);
            }

            if (productName!=null){
                productCondition.setProductName(productName);
            }

            ProductExecution productExecution = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", productExecution.getProductList());
            modelMap.put("count", productExecution.getCount());
            modelMap.put("success", true);
            return modelMap;
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Missing pageIndex, pageSize or ShopId");
            return modelMap;
        }
    }
}
