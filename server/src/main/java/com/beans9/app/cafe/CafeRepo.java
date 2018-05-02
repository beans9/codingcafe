package com.beans9.app.cafe;

import org.springframework.data.repository.CrudRepository;

public interface CafeRepo extends CrudRepository<Cafe, Long>{
	public Iterable<Cafe> findAllByIsDelete(boolean isDelete);
}
