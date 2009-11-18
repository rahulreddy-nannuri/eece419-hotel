package ubc.eece419.pod1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import ubc.eece419.pod1.dao.ItemTypeRepository;
import ubc.eece419.pod1.entity.ItemType;
import ubc.eece419.pod1.validator.ReflectionEntityValidator;


@Transactional
@Controller
public class ItemTypeController extends CRUDController<ItemType> {

	@Autowired
	ItemTypeRepository ItemTypeRepository;

	public ItemTypeController() {
		addValidator(new ReflectionEntityValidator<ItemType>(this));
	}

    @Override
    public ItemTypeRepository getRepository() {
        return ItemTypeRepository;
    }

}
