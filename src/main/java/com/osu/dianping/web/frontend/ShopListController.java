package com.osu.dianping.web.frontend;

import com.osu.dianping.dto.ShopExecution;
import com.osu.dianping.entity.Area;
import com.osu.dianping.entity.Shop;
import com.osu.dianping.entity.ShopCategory;
import com.osu.dianping.service.AreaService;
import com.osu.dianping.service.ShopCategoryService;
import com.osu.dianping.service.ShopService;
import com.osu.dianping.util.HttpServletRequestUtil;
import com.osu.dianping.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ShopService shopService;

    @RequestMapping("/shoplist")
    private String shopList(){
        return "frontend/shoplist";
    }


    @RequestMapping(value = "/listshoppageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = null;
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        if (parentId != -1) {
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }

        modelMap.put("shopCategoryList", shopCategoryList);

        List<Area> areaList = null;

        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

    }

    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));

        if (pageIndex!=null && pageSize!=null){
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            int areaId = (int)HttpServletRequestUtil.getLong(request, "areaId");
            String shopName = HttpServletRequestUtil.getString(request, "shopName");

            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            ShopExecution se2 = shopService.getShopList(shopCondition, 1, Integer.MAX_VALUE);

            int pageCount = PageCalculator.calculateTotalPage(se2.getCount(), pageSize);
            modelMap.put("success", true);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("pageCount", pageCount);
            return modelMap;
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg", "empty pageIndex or pageSize");
        }
        return modelMap;

    }


    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }

        shopCondition.setEnableStatus(1);
        return shopCondition;
    }

}
