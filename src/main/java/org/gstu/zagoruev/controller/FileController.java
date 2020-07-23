package org.gstu.zagoruev.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/file")
public class FileController {

	@ResponseBody
    @GetMapping("/{name}.{r}")
    public byte[] getFile(Model model, @PathVariable String name, @PathVariable String r) throws IOException {
    	name=name+"."+r;
    	
    	File file=new File("files/"+name);
    	
    	BufferedImage img = ImageIO.read(file);
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "PNG", baos);
		byte[] bytes = baos.toByteArray();
		return baos.toByteArray();
    }
}
