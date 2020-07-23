package org.gstu.zagoruev.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.gstu.zagoruev.entity.Brand;
import org.gstu.zagoruev.entity.Category;
import org.gstu.zagoruev.entity.Producer;
import org.gstu.zagoruev.entity.Product;
import org.gstu.zagoruev.service.BrandService;
import org.gstu.zagoruev.service.CategoryService;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.ProducerService;
import org.gstu.zagoruev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService service;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProducerService producerService;
	@Autowired
	BrandService brandService;
	@Value("${upload.path}")
	String path;
	@Autowired
	ListService listService;

	@GetMapping
	public String getproduct(Model model) {
		listService.makeModel(model);
		List<Product> findAll = service.repository.findAll();
		if (!findAll.isEmpty())
			model.addAttribute("products", findAll);

		List<Producer> producer = producerService.repository.findAll();
		if (!producer.isEmpty())
			model.addAttribute("producers", producer);
		return "product";
	}

	@PostMapping
	public String setproduct(Model model, @ModelAttribute Product product, int categoryId, int producerId, int brandId,
			@RequestParam("file") MultipartFile file, RedirectAttributes ra) {
		String response;
		System.out.println("start create product");
		try {
			Category categ = categoryService.read(categoryId);
			Producer producer = producerService.read(producerId);
			Brand brand = brandService.read(brandId);

			if (!file.isEmpty()) {
				File dir = new File(path);
				if (!dir.exists())
					dir.mkdir();

				String name = System.currentTimeMillis() + file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(dir + "/" + name)));
				stream.write(bytes);
				stream.close();
				product.setFilename(name);
			}

			product.setCategory(categ);
			product.setProducer(producer);
			product.setBrand(brand);
			service.create(product);

			categ.getProduct().add(product);
			producer.getProduct().add(product);
			brand.getProduct().add(product);

			categoryService.update(categ);
			producerService.update(producer);
			brandService.update(brand);

			response = "Success created";
			ra.addFlashAttribute("response1", "Product was added");
		} catch (Exception e) {
			e.printStackTrace();
			response = "creating failed";
			ra.addFlashAttribute("response1", "Adding product failed");
		}
		System.out.println("create product: " + response);

		List<Product> findAll = service.repository.findAll();
		if (!findAll.isEmpty())
			model.addAttribute("products", findAll);
		return "redirect:/product";
	}

	@PostMapping("/edit")
	public String setproduct(Model model, @ModelAttribute Product product, int categoryId, int producerId, int brandId,
			@RequestParam("file") MultipartFile file, @Param("idd") int idd, RedirectAttributes ra) {
		try {
			Product read = service.read(idd);
			read.setName(product.getName());
			read.setDescription(product.getDescription());
			read.setPrice(product.getPrice());
			read.setQuantity(product.getQuantity());

			Category categ = categoryService.read(categoryId);
			Producer producer = producerService.read(producerId);
			Brand brand = brandService.read(brandId);

			if (!file.isEmpty()) {
				File dir = new File(path);
				if (!dir.exists())
					dir.mkdir();

				String name = System.currentTimeMillis() + file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(dir + "/" + name)));
				stream.write(bytes);
				stream.close();
				read.setFilename(name);
			}

			read.setCategory(categ);
			read.setProducer(producer);
			read.setBrand(brand);

			service.update(read);
			ra.addFlashAttribute("response1", "Product was edited");
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("response1", "Editing product dailed");
		}
		return "redirect:/product";
	}

	@GetMapping("/remove/{id}")
	public String removeproduct(Model model, @PathVariable Long id, RedirectAttributes ra) {
		try {
			System.out.println(id);
			service.repository.delete(service.read(id));
			ra.addFlashAttribute("response1", "Product was deleted");
		} catch (Exception e) {
			ra.addFlashAttribute("response1", "Deleting product failed");
		}
		return "redirect:/product";
	}

	public void makeProducers(Model model) {
		List<Producer> producer = producerService.repository.findAll();
		if (!producer.isEmpty())
			model.addAttribute("producers", producer);

	}

	@PostMapping("/findByName")
	public String findByName(Model model, @Param("name") String name) {
		listService.makeModel(model);
		makeProducers(model);
		try {
			List<Product> list = service.repository.findByNameContaining(name);
			if (!list.isEmpty())
				model.addAttribute("products", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product";
	}

	@PostMapping("/findByPrice")
	public String findByPrice(Model model, @RequestParam(name = "price", defaultValue = "0") float price) {
		listService.makeModel(model);
		makeProducers(model);
		try {
			List<Product> list = service.repository.findByPrice(price);
			if (!list.isEmpty())
				model.addAttribute("products", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product";
	}

	@PostMapping("/findByQuantity")
	public String findByquantity(Model model, @RequestParam(name = "quantity", defaultValue = "0") int quantity) {
		listService.makeModel(model);
		makeProducers(model);
		try {
			List<Product> list = service.repository.findByQuantity(quantity);
			if (!list.isEmpty())
				model.addAttribute("products", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product";
	}

	@PostMapping("/findByDescription")
	public String findByDescription(Model model, @RequestParam(name = "description") String description) {
		listService.makeModel(model);
		makeProducers(model);
		try {
			List<Product> list = service.repository.findByDescriptionContaining(description);
			if (!list.isEmpty())
				model.addAttribute("products", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product";
	}

	@PostMapping("/findByCategory")
	public String findByCategory(Model model, @RequestParam(name = "categoryId") int categoryId) {
		listService.makeModel(model);
		makeProducers(model);
		try {
			List<Product> list = service.repository.findByCategory(categoryService.read(categoryId));
			if (!list.isEmpty())
				model.addAttribute("products", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product";
	}

	@PostMapping("/findByProducer")
	public String findByProducer(Model model, @RequestParam(name = "producerId") int producerId) {
		listService.makeModel(model);
		makeProducers(model);
		try {
			List<Product> list = service.repository.findByProducer(producerService.read(producerId));
			if (!list.isEmpty())
				model.addAttribute("products", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product";
	}

	@PostMapping("/findByBrand")
	public String findByBrand(Model model, @RequestParam(name = "brandId") int brandId) {
		listService.makeModel(model);
		makeProducers(model);
		try {
			List<Product> list = service.repository.findByBrand(brandService.read(brandId));
			if (!list.isEmpty())
				model.addAttribute("products", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product";
	}
}
