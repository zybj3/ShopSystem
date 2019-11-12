package com.osu.dianping.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osu.dianping.dto.ImageHolder;
import com.osu.dianping.dto.ShopExecution;
import com.osu.dianping.entity.Area;
import com.osu.dianping.entity.PersonInfo;
import com.osu.dianping.entity.Shop;
import com.osu.dianping.entity.ShopCategory;
import com.osu.dianping.enums.ShopStateEnum;
import com.osu.dianping.service.AreaService;
import com.osu.dianping.service.ProductCategoryService;
import com.osu.dianping.service.ShopCategoryService;
import com.osu.dianping.service.ShopService;
import com.osu.dianping.util.CodeUtil;
import com.osu.dianping.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    ShopService shopService;

    @Autowired
    ShopCategoryService shopCategoryService;

    @Autowired
    AreaService areaService;

    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
//        System.out.println(request.getParameter("shopId"));
        long shopId = Long.parseLong(request.getParameter("shopId"));
        System.out.println(shopId);
        if (shopId<=0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj==null){
                modelMap.put("redirect", true);
                modelMap.put("url", "/dianping/shopadmin/shoplist");
            }else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }

        return modelMap;
    }

    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("currentUser");


        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        return modelMap;
    }




    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "wrong verify code!");
            return modelMap;
        }
        //1接收参数，包括店铺及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");

        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
            //System.out.println(shop.getShopCategory().getShopCategoryId());
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "cannot Read Value");
            return modelMap;
        }

        //System.out.println(request.getParameter("shopImg"));

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
            System.out.println(shopImg.getOriginalFilename());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "img cannot be null");
            return modelMap;
        }
        //2注册店铺
        if (shop != null && shopImg != null) {

            System.out.println(request.getSession().getId());
            PersonInfo owner = (PersonInfo)request.getSession().getAttribute("currentUser");
            shop.setOwner(owner);


            ShopExecution se = null;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                se = shopService.addShop(shop, imageHolder);
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "cannot addshop");
            }
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if (shopList == null || shopList.size() == 0) {
                    shopList = new ArrayList<>();
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                } else {
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }

            return modelMap;

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "cannot insert null value");
            return modelMap;
        }
    }

    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        System.out.println(shopId);
        if (shopId > -1) {
            Shop shop = shopService.getByShopId(shopId);
            List<Area> areaList = areaService.getAreaList();
            modelMap.put("shop", shop);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        }

        return modelMap;
    }


    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "wrong verify code!");
            return modelMap;
        }
        //1接收参数，包括店铺及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        System.out.println(shopStr);

        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
            //System.out.println(shop.getShopCategory().getShopCategoryId());
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "cannot Read Value");
            return modelMap;
        }

        //System.out.println(request.getParameter("shopImg"));

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
            System.out.println(shopImg.getOriginalFilename());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "img cannot be null");
            return modelMap;
        }


        //2修改店铺
        if (shop != null && shop.getShopId() != null) {


            ShopExecution se = null;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                se = shopService.modifyShop(shop, imageHolder);
                System.out.println("modify!");
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "cannot addshop");
            }
            if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }

            return modelMap;

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "please insert shopId");
            return modelMap;
        }
    }

    @RequestMapping(value = "/deleteshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> deleteShopByShopID(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        String shopIdstr = request.getParameter("shopId");
        long shopId = Long.parseLong(shopIdstr);
        ShopExecution se = shopService.deleteShopByShopID(shopId);

        if (se.getStateInfo().equals("操作成功")){
            modelMap.put("success", true);
        }else {
            modelMap.put("success", false);
        }

        return modelMap;
    }


}
