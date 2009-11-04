package ubc.eece419.pod1.controller;

import java.io.IOException;

import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.dao.ImageRepository;
import ubc.eece419.pod1.entity.Image;

public class ImageController extends CRUDController<Image> {

	@Autowired
	ImageRepository imageRepository;

	@InitBinder
	public void addByteArrayBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@Override
	public GenericRepository<Image> getRepository() {
		return imageRepository;
	}

	@RequestMapping("/**/view")
	public void view(@RequestParam(value = "id") Long id, ServletResponse response) throws IOException {
		Image image = imageRepository.findById(id);
		response.setContentType("image/png");
		response.getOutputStream().write(image.getData());
	}

}
