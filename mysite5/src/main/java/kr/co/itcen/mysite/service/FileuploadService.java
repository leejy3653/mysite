package kr.co.itcen.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.mysite.exception.FileuploadException;


@Service
public class FileuploadService {
	
	@Autowired
	private Environment env;
	
	private static final String SAVE_PATH = "/uploads"; // D 드라이브에 uploads 폴더를 생성해준다.
	private static final String URL_PREFIX = "/images";

	public String retore(MultipartFile multipartFile) {
		String url = "";

		if (multipartFile == null) {
			return url;
		}

		try {

			String originalFilename = multipartFile.getOriginalFilename();

			String saveFileName = generateSaveFilename(
					originalFilename.substring(originalFilename.lastIndexOf(".") + 1));

			long fileSize = multipartFile.getSize();

			System.out.println("##################################" + originalFilename);
			System.out.println("##################################" + saveFileName);
			System.out.println("##################################" + fileSize);

			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			url = URL_PREFIX + "/" + saveFileName;

		} catch (IOException e) {
			throw new FileuploadException();
		}

		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		return filename;
	}

}
