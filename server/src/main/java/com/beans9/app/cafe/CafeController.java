package com.beans9.app.cafe;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		return cafeRepo.findAll();
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
			MultipartFile file = files[i];
			String fileRealName = file.getOriginalFilename();
			String fileName = System.currentTimeMillis() + "." +FilenameUtils.getExtension(fileRealName);
			Photo photo = new Photo(fileName, fileRealName, file.getSize(), cafe, defaultImgIdx==i);
			File f = new File("C:\\project\\codingcafe\\client\\src\\assets\\images\\" + fileName);
			file.transferTo(f);
			photoRepo.save(photo);
		}
		return cafe;
	}
	
	@PatchMapping("/{id}")
	public Cafe patch(@AuthenticationPrincipal LoginUserDetails userDetails, @PathVariable long id, @RequestBody Cafe cafe) throws Exception {
		Cafe db = authCheck(id, userDetails.getId());
		db.setName(cafe.getName());
		db.setMemo(cafe.getMemo());
		return cafeRepo.save(db);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@AuthenticationPrincipal LoginUserDetails userDetails, @PathVariable long id) throws Exception {
		authCheck(id, userDetails.getId());		
		cafeRepo.deleteById(id);
	}
	
	@DeleteMapping("/all")
	public void deleteAll() throws Exception {
		cafeRepo.deleteAll();
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
