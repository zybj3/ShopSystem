package com.osu.dianping.web.shopadmin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.osu.dianping.dto.ImageHolder;
import com.osu.dianping.dto.ProductExecution;
import com.osu.dianping.entity.Product;
import com.osu.dianping.entity.ProductCategory;
import com.osu.dianping.entity.Shop;
import com.osu.dianping.enums.ProductStateEnum;
import com.osu.dianping.service.ProductCategoryService;
import com.osu.dianping.service.ProductService;
import com.osu.dianping.util.CodeUtil;
import com.osu.dianping.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    private static final int IMAGECOUNT = 6;

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "wrong verification code");
            return modelMap;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (multipartResolver.isMultipart(request)) {
                multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());

                for (int i = 0; i < IMAGECOUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (productImgFile != null) {
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        productImgList.add(productImg);
                    } else {
                        break;
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "you must upload an image!");
            }

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }


        try {
            product = objectMapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
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
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please enter product information");
        }

        return modelMap;
    }

    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductByID(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();

        if (productId > -1) {
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryListByShopId(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }

        return modelMap;
    }

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        boolean StatusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");

        System.out.println(StatusChange);
        if (!StatusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("sucess", false);
            modelMap.put("errMst", "wrong verification code");
            return modelMap;
        }


        if (StatusChange){
            Product product = null;
            ObjectMapper objectMapper = new ObjectMapper();

            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            try {
                product = objectMapper.readValue(productStr, Product.class);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }


            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
            }catch (RuntimeException e){
                modelMap.put("errMsg", e.toString());
            }


            System.out.println("enable status"  + product.getEnableStatus());


//            if (product.getEnableStatus()==1){
//                product.setEnableStatus(0);
//            }else {
//                product.setEnableStatus(1);
//            }


            ProductExecution pe = productService.modifyProduct(product,null, null);
            if (pe.getState()==ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "unknown error");
            }
        }
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = null;
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            MultipartHttpServletRequest multipartHttpServletRequest = null;
            ImageHolder thumbnail = null;
            List<ImageHolder> productImgList = new ArrayList<>();
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

            try {
                if (multipartResolver.isMultipart(request)) {
                    multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                    CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());

                    for (int i = 0; i < IMAGECOUNT; i++) {
                        CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                        if (productImgFile != null) {
                            ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                            productImgList.add(productImg);
                        } else {
                            break;
                        }
                    }
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "you must upload an image!");
                }

            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }


            try {
                product = objectMapper.readValue(productStr, Product.class);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

            if (product != null && thumbnail != null && productImgList.size() > 0) {
                try {
                    Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                    Shop shop = new Shop();
                    shop.setShopId(currentShop.getShopId());
                    product.setShop(shop);

                    ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                    if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
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
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "please enter product information");
            }
        }
        return modelMap;
    }


    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");

            Product productCondition = new Product();
            productCondition.setShop(currentShop);

            if (productName != null) {
                productCondition.setProductName(productName);
            }
            if (productCategoryId != -1L) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setProductCategoryId(productCategoryId);
                productCondition.setProductCategory(productCategory);
            }

            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMst", "empty pageindex or page account");
        }

        return modelMap;
    }
}
