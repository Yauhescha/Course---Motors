package org.gstu.zagoruev.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.gstu.zagoruev.entity.MyUser;
import org.gstu.zagoruev.entity.Orderr;
import org.gstu.zagoruev.entity.OrdersProduct;
import org.gstu.zagoruev.entity.Product;
import org.gstu.zagoruev.entity.Returning;
import org.gstu.zagoruev.service.BrandService;
import org.gstu.zagoruev.service.CategoryService;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.MyUserService;
import org.gstu.zagoruev.service.OrderrService;
import org.gstu.zagoruev.service.OrdersProductService;
import org.gstu.zagoruev.service.ProductService;
import org.gstu.zagoruev.service.ReturningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	ProductService productService;
	@Autowired
	MyUserService userService;
	@Autowired
	OrdersProductService orderProductService;
	@Autowired
	OrderrService orderService;
	@Autowired
	BrandService brandService;
	@Autowired
	CategoryService categoryService;
	@Autowired
    ListService listService;
	
	@Autowired
	ReturningService returningService;
	
	@GetMapping
	public String getOrder(Model model, Principal user) {
		listService.makeModel(model);

		MyUser usr = userService.repository.findByUsername(user.getName());

		List<Orderr> list = usr.getOrderr();
		List<Orderr> current = new ArrayList<Orderr>();
		List<Orderr> wait = new ArrayList<Orderr>();
		List<Orderr> ended = new ArrayList<Orderr>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIspolnen())
				ended.add(list.get(i));
			else if (!list.get(i).getIspolnen() && list.get(i).getSakonshen())
				wait.add(list.get(i));
			else
				current.add(list.get(i));
		}
		try {
			if (!current.isEmpty()) {
				float price=0;
				for (int i = 0; i < current.get(0).getProducts().size(); i++)
					price+=	current.get(0).getProducts().get(i).getQuantity() 
							* productService.read(current.get(0).getProducts().get(i).getProductId()).getPrice();
				current.get(0).setPrice(price);
				orderService.update(current.get(0));
			}
		} catch (Exception e) {
		}
		model.addAttribute("current", current);
		model.addAttribute("wait", wait);
		model.addAttribute("ended", ended);
		return "order";
	}

	@PostMapping()
	public String postNewProduct(Model model, @RequestParam Long productId, @RequestParam int count, Principal user, RedirectAttributes ra)
			 {
		try {
		MyUser usr = userService.repository.findByUsername(user.getName());
		Product product = productService.read(productId);

		boolean ok = false;
		List<Orderr> allOrders = usr.getOrderr();
		for (int i = 0; i < allOrders.size(); i++) {
			System.out.println("i: " + i);
			Orderr order = allOrders.get(i);
			if (!order.getSakonshen()) {
				ok = true;
				boolean contains = false;
				List<OrdersProduct> products = order.getProducts();
				for (int j = 0; j < products.size(); j++) {
					if (products.get(j).getProductId().equals(productId)) {
						products.get(j).setQuantity(products.get(j).getQuantity() + count);
						products.get(j).setPrice(product.getPrice());
//						products.get(j).setFilename(product.getFilename());
						contains = true;
						orderProductService.update(products.get(j));
						break;
					}
				}
				if (!contains) {
					OrdersProduct op = new OrdersProduct();
					op.setQuantity(count);
					op.setProductId(productId);
					op.setProductName(product.getName());
					op.setFilename(product.getFilename());
					op.setPrice(product.getPrice());
					orderProductService.create(op);
					op.setOrderr(order);
					orderProductService.update(op);
				}
				break;
			}
		}
		if (!ok) {
			Orderr order = new Orderr();
			orderService.create(order);

			OrdersProduct op = new OrdersProduct();
			op.setQuantity(count);
			op.setPrice(product.getPrice());
			op.setProductId(productId);
			op.setProductName(product.getName());
			op.setFilename(product.getFilename());
			orderProductService.create(op);

			order.setUser(usr);
			order.getProducts().add(op);
			orderService.update(order);

			usr.getOrderr().add(order);
			userService.update(usr);

			op.setOrderr(order);
			orderProductService.update(op);
		}

		model.addAttribute("count", count);
		ra.addFlashAttribute("response1", "New product added in order");
		}catch(Exception e) {
			ra.addFlashAttribute("response1", "Adding new product in orer failed");
		}
		return "redirect:/order";
	}

	@GetMapping("/remove/{id}")
	public String removeProductFromOrder(Principal user, @PathVariable Long id, RedirectAttributes ra) {
		try {
		MyUser myUser = userService.repository.findByUsername(user.getName());
		OrdersProduct read = orderProductService.read(id);

		for (int i = 0; i < myUser.getOrderr().size(); i++) {
			if (!myUser.getOrderr().get(i).getSakonshen()) {
				Orderr orderr = myUser.getOrderr().get(i);
				for (int j = 0; j < orderr.getProducts().size(); j++) {
					if (orderr.getProducts().get(j).getId().equals(id)) {
						orderr.getProducts().get(j).setOrderr(null);
						orderr.getProducts().remove(j);
						if (orderr.getProducts().size() == 0) {

							orderService.update(orderr);

							myUser.getOrderr().remove(myUser.getOrderr().indexOf(orderr));
							orderr.setUser(null);
							orderService.delete(orderr.getId());
							userService.update(myUser);
							
						}
					}
				}

				//break;
			}
		}
		userService.update(myUser);
		orderProductService.update(read);
		
		ra.addFlashAttribute("response1", "Succes removed product from order");
		}catch(Exception e) {
			ra.addFlashAttribute("response1", "Removing product from order failed");
		}
		return "redirect:/order";
	}

	@GetMapping("/buy/{id}")
	public String orderBuy(Principal user, @PathVariable Long id, RedirectAttributes ra) {
		try {
		MyUser myUser = userService.repository.findByUsername(user.getName());
		Orderr order = orderService.read(id);
		order.setSakonshen(true);
		orderService.update(order);
		ra.addFlashAttribute("response1", "Succes approved order");
		}catch(Exception e) {
			ra.addFlashAttribute("response1", "Aproving order failed");
		}
		return "redirect:/order";
	}
	@PostMapping("/return")
	public String orderReturn(Principal user, @Param("orderProductId") Long orderProductId, RedirectAttributes ra) {
		try {
			MyUser myUser = userService.repository.findByUsername(user.getName());
			OrdersProduct orderProduct = orderProductService.read(orderProductId);
			Returning returning = new Returning();
			returning.setDate(new Date(System.currentTimeMillis()));
			returning.setOrderProductId(orderProductId);
			returning.setUserId(myUser.getId());
			returningService.create(returning);
			
			ra.addFlashAttribute("response1", "Product was post to returning");
		}catch(Exception e) {
			System.out.println(e);
			ra.addFlashAttribute("response1", "Returning product failed");
		}
		return "redirect:/order";
	}
}
