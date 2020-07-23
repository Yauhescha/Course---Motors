package org.gstu.zagoruev.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gstu.zagoruev.entity.ChartHelper;
import org.gstu.zagoruev.entity.Orderr;
import org.gstu.zagoruev.entity.OrdersProduct;
import org.gstu.zagoruev.entity.Product;
import org.gstu.zagoruev.entity.ReportHelper;
import org.gstu.zagoruev.entity.Request;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.OrderrService;
import org.gstu.zagoruev.service.ProductService;
import org.gstu.zagoruev.service.RequestService;
import org.gstu.zagoruev.service.WarhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/chart")
public class ChartController {
	@Autowired
	OrderrService orderService;
	@Autowired
	ProductService productService;
	@Autowired
	RequestService requestService;
	@Autowired
	ListService listService;
	@Autowired
	WarhouseService warhouseService;

	@GetMapping
	public String getchart(Model model) throws JsonProcessingException {
		listService.makeModel(model);
		ObjectMapper mapper = new ObjectMapper();

		//получение закупок по дням
		List<Request> findAll = requestService.repository.findAll();
		List<ReportHelper> weBuy = new ArrayList<ReportHelper>();
		if (!findAll.isEmpty())
			for (int i = 0; i < findAll.size(); i++)
				if (findAll.get(i).getAprove()) {
					Request rq =findAll.get(i); 
					weBuy.add(new ReportHelper("",0,rq.getCount(),rq.getDate(),"",rq.getProductId()));
				}
		Map<Integer, Integer> mapWeBuy = new HashMap<>();
		for (ReportHelper rh : weBuy) {
			int day = rh.getDate().toLocalDate().getDayOfMonth();
			if (mapWeBuy.containsKey(day))
				mapWeBuy.put(day, mapWeBuy.get(day) + rh.getCount());
			else
				mapWeBuy.put(day, rh.getCount());

		}
		model.addAttribute("mapWeBuy", mapper.writeValueAsString(mapWeBuy));
		
		
		//получение продаж по дням
		Date to = new Date(System.currentTimeMillis());
		LocalDate date = LocalDate.now().minusMonths(1);
		Date from = Date.valueOf(date);

		List<Orderr> between = orderService.repository
				.findByIspolnenAndDateLessThanEqualAndDateGreaterThanEqual(Boolean.TRUE, to, from);

		List<ReportHelper> help = new ArrayList<ReportHelper>();
		for (Orderr order : between)
			for (OrdersProduct op : order.getProducts()) {
				Product product = productService.read(op.getProductId());
				help.add(new ReportHelper(product.getName(), product.getPrice(), op.getQuantity(), order.getDate(),
						product.getProducer().getTitle(),product.getId()));
				
			}
		Map<Integer, Integer> map = new HashMap<>();
		for (ReportHelper rh : help) {
			int day = rh.getDate().toLocalDate().getDayOfMonth();
			if (map.containsKey(day))
				map.put(day, map.get(day) + rh.getCount());
			else
				map.put(day, rh.getCount());

		}
		model.addAttribute("map", mapper.writeValueAsString(map));
		
		//получение продаж каждого продукта
		List<ChartHelper> chartHelper = new ArrayList<ChartHelper>();
		Map<Long, Integer> mapProdanogoProduct = new HashMap<>();
		for (ReportHelper rh : help) {
			Long prodId = rh.getProductId();
			if (mapProdanogoProduct.containsKey(prodId))
				mapProdanogoProduct.put(prodId, mapProdanogoProduct.get(prodId) + rh.getCount());
			else
				mapProdanogoProduct.put(prodId, rh.getCount());
		}
		
		for(Long key:mapProdanogoProduct.keySet()) {
			chartHelper.add(new ChartHelper(key, mapProdanogoProduct.get(key), warhouseService.repository.findByProductId(key).getCount()));
		}
		model.addAttribute("mapProdanogoProduct",  mapper.writeValueAsString(chartHelper));
		
		
		//получение закупок каждого товара
		List<ChartHelper> chartHelper2 = new ArrayList<ChartHelper>();
		Map<Long, Integer> mapRequestProduct = new HashMap<>();
		for (ReportHelper rh : weBuy) {
			Long prodId = rh.getProductId();
			if (mapRequestProduct.containsKey(prodId))
				mapRequestProduct.put(prodId, mapRequestProduct.get(prodId) + rh.getCount());
			else
				mapRequestProduct.put(prodId, rh.getCount());
		}
		
		for(Long key:mapRequestProduct.keySet()) {
			System.out.println(key);
			chartHelper2.add(new ChartHelper(key, mapRequestProduct.get(key), warhouseService.repository.findByProductId(key).getCount()));
		}
		model.addAttribute("mapRequestProduct",  mapper.writeValueAsString(chartHelper2));
		
		//поулчение списка продуктов для кругового графика
		List<Product> allProducts = productService.repository.findAll();
		model.addAttribute("products", allProducts);
		
		
		
		return "chart";
	}
}
