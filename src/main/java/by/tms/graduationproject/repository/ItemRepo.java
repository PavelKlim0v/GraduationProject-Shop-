package by.tms.graduationproject.repository;

import by.tms.graduationproject.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepo extends CrudRepository<Item, Long> {
}
