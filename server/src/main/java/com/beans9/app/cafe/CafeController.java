package com.beans9.app.cafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cafe")
@RestController
public class CafeController {
	@Autowired
	CafeRepo cafeRepo;
	
	@GetMapping
	public Iterable<Cafe> select() {
		return cafeRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Cafe get(@PathVariable long id) {
		return cafeRepo.findById(id).get();
	}
	
	@PostMapping
	public Cafe post(@RequestBody Cafe cafe) {
		return cafeRepo.save(cafe);
	}
	
	@PatchMapping("/{id}")
	public Cafe patch(@PathVariable long id, @RequestBody Cafe cafe) {
		Cafe db = get(id);
		db.setName(cafe.getName());
		db.setMemo(cafe.getMemo());
		return cafeRepo.save(db);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		cafeRepo.deleteById(id);
	}
}
