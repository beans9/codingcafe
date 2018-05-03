package com.beans9.app.cafe;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.LongStream;

import javax.naming.AuthenticationException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired CafeRepo cafeRepo;
	@Autowired PhotoRepo photoRepo;
	
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
			MultipartFile[] files, int defaultImgIdx) throws IllegalStateException, IOException {
		cafe.setAppUser(new AppUser(userDetails.getId()));
		cafeRepo.save(cafe);
		
		for(int i=0; i<files.length; i++) {
			Photo photo = copyFile(files[i], cafe, defaultImgIdx==i);
			photoRepo.save(photo);
		}
		
		return cafe;
	}
	
	@PostMapping("/{id}")
	public Cafe patch(@AuthenticationPrincipal LoginUserDetails userDetails, 
			@ModelAttribute Cafe cafe,
			MultipartFile[] files, 
			Integer defaultImgIdx,
			Integer defaultImgIdxDb,
			long[] deleteImgIds
			) throws Exception {
		Cafe db = authCheck(cafe.getId(), userDetails.getId());
		db.setName(cafe.getName());
		db.setMemo(cafe.getMemo());
		db.setAddress(cafe.getAddress());
		db.setParking(cafe.isParking());
		db.setWifi(cafe.isWifi());
		db.setConcent(cafe.isConcent());
		db.setReVisit(cafe.isReVisit());
		
		if (deleteImgIds != null ) {
			for (Photo photo: db.getPhotos()) {
				if (LongStream.of(deleteImgIds).anyMatch(x->x == photo.getId())) {
					if(photo.isDefault()) photo.setDefault(false);
					photo.setDelete(true);
				}
			}
		}
		
		if (defaultImgIdx != null) {
			for (Photo photo: db.getPhotos()) {
				if (photo.isDefault()) {
					photo.setDefault(false);
					break;
				}
			}
		}
		
		for(int i=0; i<files.length; i++) {
			Photo photo = copyFile(files[i], cafe, defaultImgIdx==i);
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
	
	public Photo copyFile(MultipartFile file, Cafe cafe, boolean isDefault) throws IllegalStateException, IOException {
		String fileRealName = file.getOriginalFilename();
		String fileName = System.currentTimeMillis() + "." +FilenameUtils.getExtension(fileRealName);
		Photo photo = new Photo(fileName, fileRealName, file.getSize(), cafe, isDefault);
		File f = new File("C:\\project\\codingcafe\\client\\src\\assets\\images\\" + fileName);
		file.transferTo(f);
		return photo;
	}
	
	public Cafe authCheck(long cafeId, long userId) throws Exception {
		// 사용자가 같지 않으면 exception
		Cafe db = get(cafeId);
		
		if(db.getAppUser().getId() != userId) {
			throw new AuthenticationException("No Auth");
		}
		return db;
	}
}
