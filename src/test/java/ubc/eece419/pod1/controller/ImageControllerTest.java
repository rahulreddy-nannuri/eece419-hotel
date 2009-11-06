package ubc.eece419.pod1.controller;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageControllerTest {

	@Test
	public void testPNGDetection() {
		String base64 = "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAABGdBTUEAALGP"
				+ "C/xhBQAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB9YGARc5KB0XV+IA"
				+ "AAAddEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIFRoZSBHSU1Q72QlbgAAAF1J"
				+ "REFUGNO9zL0NglAAxPEfdLTs4BZM4DIO4C7OwQg2JoQ9LE1exdlYvBBeZ7jq"
				+ "ch9//q1uH4TLzw4d6+ErXMMcXuHWxId3KOETnnXXV6MJpcq2MLaI97CER3N0"
				+ "vr4MkhoXe0rZigAAAABJRU5ErkJggg==";

		byte[] data = Base64.decodeBase64(base64.getBytes());

		assertTrue(ImageController.isPNG(data));
	}

	@Test
	public void testGIFDetection() {
		String base64 = "R0lGODlhEAAOALMAAOazToeHh0tLS/7LZv/0jvb29t/f3//Ub/"
				+ "/ge8WSLf/rhf/3kdbW1mxsbP//mf///yH5BAAAAAAALAAAAAAQAA4AAARe8L1Ekyky67QZ1hLnjM5UUde0ECwLJoExKcpp"
				+ "V0aCcGCmTIHEIUEqjgaORCMxIC6e0CcguWw6aFjsVMkkIr7g77ZKPJjPZqIyd7sJAgVGoEGv2xsBxqNgYPj/gAwXEQA7";

		byte[] data = Base64.decodeBase64(base64.getBytes());

		assertTrue(ImageController.isGIF(data));
	}

	@Test
	public void testJPEGDetection() {
		String base64 = "/9j/4AAQSkZJRgABAQEASABIAAD/"
			+ "2wBDAAYEBAQFBAYFBQYJBgUGCQsIBgYICwwKCgsKCgwQDAwMDAwMEAwODxAPDgwTExQUExM"
			+ "cGxsbHCAgICAgICAgICD/2wBDAQcHBw0MDRgQEBgaFREVGiAgICAgICAgICAgICAgICA"
			+ "gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICD/wAARCAFKABsDAREAAhEBAx"
			+ "EB/8QAGQABAQEBAQEAAAAAAAAAAAAAAAECBAMI/8QAFxABAQEBAAAAAAAAAAAAAAAA"
			+ "ABESE//EABQBAQAAAAAAAAAAAAAAAAAAAAD/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/"
			+ "9oADAMBAAIRAxEAPwD6pAAAAAAABAAAASgUCgUEAAABAAAASgUCgUAAAAGQAAAKBQ"
			+ "KBQQAAAEAAABAAAASgUCgUEAAABAAAASgUCgUEAAABAAAAQAAAEoFAoFBAAAAQAAA"
			+ "EoFAoFAAAABkAAACgUCgUEAAABAAAAQAAAEoFAoFBAAAAQAAAEoFAoFBAAAAQAAAEAA"
			+ "AB47A2BsDYOboB0A6AdAc3QDoB0A6A5tgbA2BsGQAAAf/Z";

		byte[] data = Base64.decodeBase64(base64.getBytes());

		assertTrue(ImageController.isJPEG(data));
	}
}
