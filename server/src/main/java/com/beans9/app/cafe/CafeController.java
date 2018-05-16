package com.beans9.app.cafe;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.LongStream;

import javax.imageio.ImageIO;
import javax.naming.AuthenticationException;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.beans9.app.user.AppUser;
import com.beans9.app.user.LoginUserDetails;

@RequestMapping("/cafe")
@RestController
public class CafeController {
	@Autowired
	CafeRepo cafeRepo;
	
	@Autowired
	PhotoRepo photoRepo;
	
	@Autowired
	TagRepo tagRepo;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@GetMapping
	public Iterable<Cafe> select() {
		return cafeRepo.findAllByIsDelete(false);
	}
	
	@GetMapping("/{id}")
	public Cafe get(@PathVariable long id) throws Exception {
		Optional<Cafe> db = cafeRepo.findById(id);
		if (db.isPresent() == true) {
			return cafeRepo.findById(id).get();
		} else {
			throw new Exception("do not exist id");
		}
	}

	@PostMapping
	public Cafe post(@AuthenticationPrincipal LoginUserDetails userDetails, @ModelAttribute Cafe cafe,
			MultipartFile[] files, int defaultImgIdx,
			String tagList
			) throws Exception {
		AppUser user  = new AppUser(userDetails.getId());
		cafe.setAppUser(user);
		cafe.setCreateDate(LocalDateTime.now());
		cafeRepo.save(cafe);

		for (int i=0; i < files.length; i++) {
			Photo photo = copyFile(files[i], cafe, defaultImgIdx == i);
			photoRepo.save(photo);
		}
		
		String[] tags = tagList.split("__");
		for (int i=0; i < tags.length; i++) {
			Tag tag = new Tag(tags[i], user, cafe);
			tagRepo.save(tag);
		}

		return cafe;
	}

	@PostMapping("/{id}")
	public Cafe patch(@AuthenticationPrincipal LoginUserDetails userDetails, @ModelAttribute Cafe cafe,
			MultipartFile[] files, Integer defaultImgIdx, Integer defaultImgIdxDb, long[] deleteImgIds)
			throws Exception {
		Cafe db = authCheck(cafe.getId(), userDetails.getId());
		db.setName(cafe.getName());
		db.setMemo(cafe.getMemo());
		db.setAddress(cafe.getAddress());
		db.setParking(cafe.isParking());
		db.setWifi(cafe.isWifi());
		db.setConcent(cafe.isConcent());
		db.setReVisit(cafe.isReVisit());
		
		if (deleteImgIds != null) {
			for (Photo photo : db.getPhotos()) {
				if (LongStream.of(deleteImgIds).anyMatch(x -> x == photo.getId())) {
					if (photo.isDefault())
						photo.setDefault(false);
					photo.setDelete(true);
				}
			}
		}

		if (defaultImgIdx != null) {
			for (Photo photo : db.getPhotos()) {
				if (photo.isDefault()) {
					photo.setDefault(false);
					break;
				}
			}
		}
		
		if (defaultImgIdxDb != null) {
			for (Photo photo : db.getPhotos()) {
				photo.setDefault(false);
				if (photo.getId() == defaultImgIdxDb) {
					photo.setDefault(true);
				}
			}
		}

		for (int i = 0; i < files.length; i++) {
			Photo photo = copyFile(files[i], cafe, defaultImgIdx == i);
			photoRepo.save(photo);
		}

		return cafeRepo.save(db);
	}

	@DeleteMapping("/{id}")
	public void delete(@AuthenticationPrincipal LoginUserDetails userDetails, @PathVariable long id) throws Exception {
		Cafe cafe = authCheck(id, userDetails.getId());
		cafe.setDelete(true);
		cafeRepo.save(cafe);
		// cafeRepo.deleteById(id);
	}

	@DeleteMapping("/all")
	public void deleteAll() throws Exception {
		cafeRepo.deleteAll();
	}

	public Photo copyFile(MultipartFile file, Cafe cafe, boolean isDefault) throws Exception {
		String fileRealName = file.getOriginalFilename();
		String fileName = System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileRealName);
		Photo photo = new Photo(fileName, fileRealName, file.getSize(), cafe, isDefault);
		String orgFilePath = uploadPath + fileName;
		String filePath = uploadPath + "\\tumb\\" + fileName;
		File f = new File(orgFilePath);
		file.transferTo(f);
		imageResize(f, uploadPath, fileName, FilenameUtils.getExtension(fileRealName), 500);
		return photo;
	}

	public static void imageResize(File file, String uploadPath, String fileName, String imageType, int size) throws Exception {
		BufferedImage originalImage = ImageIO.read(file);
		int imgwidth = Math.min(originalImage.getHeight(), originalImage.getWidth());
		int imgheight = imgwidth;
		BufferedImage scaledImage = Scalr.crop(originalImage, (originalImage.getWidth() - imgwidth) / 2, (originalImage.getHeight() - imgheight) / 2, imgwidth, imgheight, null);
		
		BufferedImage resizedImage = null;
		// 이미지사이즈가 1200보다 클경우 resize
		if (originalImage.getWidth() >= 1200) {
			
			resizedImage = Scalr.resize(scaledImage, Mode.FIT_TO_WIDTH, 1200, null);
			ImageIO.write(resizedImage, imageType, new File(uploadPath + fileName));
		}
		
		// thumb 이미지 생성
		resizedImage = Scalr.resize(scaledImage, Mode.FIT_TO_WIDTH, 500, null);
		ImageIO.write(resizedImage, imageType, new File(uploadPath + "tumb\\" + fileName));
		
		// 상세화면 리스트용 이미지 생성
		resizedImage = Scalr.resize(scaledImage, Mode.FIT_TO_WIDTH, 100, null);
		ImageIO.write(resizedImage, imageType, new File(uploadPath + "m\\" + fileName));
	}

	public Cafe authCheck(long cafeId, long userId) throws Exception {
		// 사용자가 같지 않으면 exception
		Cafe db = get(cafeId);

		if (db.getAppUser().getId() != userId) {
			throw new AuthenticationException("No Auth");
		}
		return db;
	}
}
