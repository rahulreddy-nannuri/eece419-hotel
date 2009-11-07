package ubc.eece419.pod1.controller;

import java.io.IOException;

import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.dao.ImageRepository;
import ubc.eece419.pod1.entity.Image;

public class ImageController extends CRUDController<Image> {

	public static boolean isPNG(byte[] data) {
		byte[] magic = { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };
		for (int i = 0; i < magic.length; i++) {
			if (data[i] != magic[i]) {
				return false;
			}
		}
		return true;
	}

	public static boolean isGIF(byte[] data) {
		byte[] magic = "GIF8".getBytes(); // GIF89a or GIF87a
		for (int i = 0; i < magic.length; i++) {
			if (data[i] != magic[i]) {
				return false;
			}
		}
		return true;
	}

	public static boolean isJPEG(byte[] data) {
		return (data[0] == (byte) 0xFF) && (data[1] == (byte) 0xD8)
				&& (data[data.length - 2] == (byte) 0xFF)
				&& (data[data.length - 1] == (byte) 0xD9);
	}

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
		if (isPNG(image.getData())) {
			response.setContentType("image/png");
		} else if (isGIF(image.getData())) {
			response.setContentType("image/gif");
		} else {
			// oh well. the browser will typically do its own checking anyway
			response.setContentType("image/jpeg");
		}
		response.getOutputStream().write(image.getData());
	}

	@Override
	public ModelAndView save(Image bound, BindingResult errors, String view) {
		if (bound.getData() == null || bound.getData().length < 1) {
			if (bound.isNewEntity()) {
				errors.rejectValue("data", "image.isnull");
			} else {
				Image old = imageRepository.findById(bound.getId());
				bound.setData(old.getData());
			}
		}
		return super.save(bound, errors, view);
	}

}
