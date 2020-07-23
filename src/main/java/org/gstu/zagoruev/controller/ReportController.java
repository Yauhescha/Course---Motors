package org.gstu.zagoruev.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.gstu.zagoruev.entity.Orderr;
import org.gstu.zagoruev.entity.OrdersProduct;
import org.gstu.zagoruev.entity.Product;
import org.gstu.zagoruev.entity.ReportHelper;
import org.gstu.zagoruev.entity.Request;
import org.gstu.zagoruev.entity.Warhouse;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.OrderrService;
import org.gstu.zagoruev.service.ProductService;
import org.gstu.zagoruev.service.RequestService;
import org.gstu.zagoruev.service.WarhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	WarhouseService warhouseService;
	@Autowired
	OrderrService orderService;
	@Autowired
	ProductService productService;
	@Autowired
	RequestService requestService;
	@Autowired
    ListService listService;
	@GetMapping
	public String getReports(Model model) {
		listService.makeModel(model);
		List<Warhouse> warhouse = warhouseService.repository.findByCountGreaterThan(0);
		for(Warhouse wh:warhouse)
			wh.setProductName(productService.read(wh.getProductId()).getName());
		model.addAttribute("warhouses", warhouse);
		return "reports";
	}

	public List<ReportHelper> make(Model model, List<Orderr> list) {
		// productId,count
		float price = 0;
		List<ReportHelper> help = new ArrayList<ReportHelper>();
		for (Orderr order : list)
			for (OrdersProduct op : order.getProducts()) {
				Product product = productService.read(op.getProductId());
				help.add(new ReportHelper(product.getName(),product.getPrice(),op.getQuantity(), order.getDate(),product.getProducer().getTitle()));
			}
		
		for(ReportHelper rh:help) {
			price+=rh.getProductPrice()*rh.getCount();
		}
		model.addAttribute("price", price);
		return help;
	}
	public List<ReportHelper> makeMinus(Model model, List<Request> list) {
		// productId,count
		float price = 0;
		List<ReportHelper> help = new ArrayList<ReportHelper>();
		for (Request req : list) {
			Product product = productService.read(req.getProductId());
			help.add(new ReportHelper(product.getName(),product.getPrice(),req.getCount(), req.getDate(),product.getProducer().getTitle()));
		}
		for(ReportHelper rh:help) {
			price+=rh.getProductPrice()*rh.getCount();
		}
		model.addAttribute("price", price);
		return help;
	}

	@GetMapping("/findP")
	public String getIncome(Model model, @Param("from") Date from, @Param("to") Date to) {
		
		List<Orderr> between = orderService.repository
				.findByIspolnenAndDateLessThanEqualAndDateGreaterThanEqual(Boolean.TRUE, to, from);
		model.addAttribute("help", make(model,between));
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("income", "1");
		return "print";
	}
	
	@GetMapping("/findM")
	public String getExpenses(Model model, @Param("from") Date from, @Param("to") Date to) {
		
		List<Request> between = requestService.repository.findByAproveAndDateLessThanEqualAndDateGreaterThanEqual(Boolean.TRUE, to,from);

		model.addAttribute("help", makeMinus(model,between));
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "print";
	}
	

}
