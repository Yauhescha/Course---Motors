package org.gstu.zagoruev.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.gstu.zagoruev.entity.Orderr;
import org.gstu.zagoruev.entity.OrdersProduct;
import org.gstu.zagoruev.entity.Returning;
import org.gstu.zagoruev.entity.Warhouse;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.MyUserService;
import org.gstu.zagoruev.service.OrderrService;
import org.gstu.zagoruev.service.OrdersProductService;
import org.gstu.zagoruev.service.ProductService;
import org.gstu.zagoruev.service.ReturningService;
import org.gstu.zagoruev.service.WarhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ordersModer")
public class OrderModerController {
	@Autowired
	ProductService productService;
	@Autowired
	MyUserService userService;
	@Autowired
	OrdersProductService orderProductService;
	@Autowired
	OrderrService orderService;
	@Autowired
	WarhouseService warhouseService;
	@Autowired
    ListService listService;

	@Autowired
	ReturningService returningService;
	@GetMapping
	public String getOrderModer(Model model) {
		listService.makeModel(model);
		List<Orderr> list = orderService.repository.findAll();

		List<Orderr> current = new ArrayList<Orderr>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSakonshen() && !list.get(i).getIspolnen())
				current.add(list.get(i));
		}
		if (!current.isEmpty())
			for (int j = 0; j < current.size(); j++) {
				float price = 0;
				for (int i = 0; i < current.get(j).getProducts().size(); i++) {
					price += current.get(j).getProducts().get(i).getQuantity()
							* productService.read(current.get(j).getProducts().get(i).getProductId()).getPrice();
					current.get(j).setPrice(price);
					try {
						orderService.update(current.get(j));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		model.addAttribute("current", current);
		
		List<Returning> findByApprove = returningService.repository.findByApprove(false);
		List<OrdersProduct> orderProduct = new ArrayList<OrdersProduct>();
		for(Returning rt:findByApprove)
			orderProduct.add(orderProductService.read(rt.getOrderProductId()));
		model.addAttribute("returning", orderProduct);
		
		return "ordersModer";
	}

	@GetMapping("/aprove/{id}")
	public String aproveOrderModer(Model model, @PathVariable Long id) {
		Orderr order = orderService.read(id);
		try {
			List<Warhouse> warhouses = new ArrayList<Warhouse>();

			for (int i = 0; i < order.getProducts().size(); i++) {
				OrdersProduct product = order.getProducts().get(i);
				Warhouse warhouse = warhouseService.repository.findByProductId(product.getProductId());
				warhouse.setCount(warhouse.getCount() - product.getQuantity());
				warhouses.add(warhouse);
			}
			for (Warhouse wh : warhouses)
				if (wh.getCount() < 0)
					throw new Exception();
			for (Warhouse wh : warhouses)
				warhouseService.update(wh);
			order.setIspolnen(true);
			order.setDate(new Date(System.currentTimeMillis()));
			orderService.update(order);
		} catch (Exception ex) {
		}
		return "redirect:/ordersModer";
	}
	@GetMapping("/return/{id}")
	public String returnOrderModer(Model model, @PathVariable Long id, RedirectAttributes ra) {
		try {		
			Returning returning = returningService.repository.findByOrderProductId(id);
			OrdersProduct ordersProduct = orderProductService.read(id);
			
			ordersProduct.getOrderr().getProducts().remove(ordersProduct);
			orderService.update(ordersProduct.getOrderr());
			
			Warhouse warhouse = warhouseService.repository.findByProductId(ordersProduct.getProductId());
			warhouse.setCount(warhouse.getCount()+ordersProduct.getQuantity());
			warhouseService.update(warhouse);
			
			orderProductService.delete(ordersProduct.getId());
			returning.setApprove(true);
			returningService.update(returning);
			
			ra.addFlashAttribute("response1","Product was returned");
		} catch (Exception ex) {
			ra.addFlashAttribute("response1","Returning product failed");
		}
		return "redirect:/ordersModer";
	}
}
