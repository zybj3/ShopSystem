package com.osu.dianping.web.shopadmin;

import com.osu.dianping.dto.ProductCategoryExecution;
import com.osu.dianping.entity.ProductCategory;
import com.osu.dianping.entity.Shop;
import com.osu.dianping.enums.ProductCategoryStateEnum;
import com.osu.dianping.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;


    @RequestMapping(value = "/queryproductcategorylistbyshopid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> queryProductCategoryByShopId(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        Shop shop = (Shop) request.getSession().getAttribute("currentShop");
        long shopId = shop.getShopId();
        try {
            List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryListByShopId(shopId);
            modelMap.put("success", true);
            modelMap.put("productCategoryList", productCategoryList);

        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        return modelMap;

    }


    @RequestMapping(value = "/addproductcategories", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCategories(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory productCategory: productCategoryList){
            productCategory.setShopId(currentShop.getShopId());
        }

        if (productCategoryList!=null && productCategoryList.size()>0){
            try{
                ProductCategoryExecution pe = productCategoryService.batchInsertProductCategory(productCategoryList);
                if (pe.getState()== ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }catch (RuntimeException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Please at least enter one Product Category");
        }

        return modelMap;
    }

    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please at least select a category");
        }

        return modelMap;
    }

}
