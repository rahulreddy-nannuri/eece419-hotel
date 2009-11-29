package ubc.eece419.pod1.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.AbstractEntity;
import ubc.eece419.pod1.entity.Databasable;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.reflection.ReflectionUtils;
import ubc.eece419.pod1.security.SecurityUtils;

@Transactional
@Controller
public abstract class CRUDController<T extends Databasable<?>> {

	protected final Logger log = Logger.getLogger(CRUDController.class.getName());
	private List<Validator> validators = new ArrayList<Validator>();
	protected final Class<T> entityClass;
	protected final String basePath;

	@SuppressWarnings("unchecked")
	public CRUDController() {
		Type dt = ReflectionUtils.getGenericParameter(getClass(), CRUDController.class);
		if (!(dt instanceof Class<?>)) {
			throw new IllegalStateException("Subclassing CRUDController without specifiying Databaseable type");
		}
		this.entityClass = (Class<T>) dt;
		this.basePath = "/" + getEntityName().toLowerCase();
	}

	protected T getNewEntity() {
		try {
			return entityClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public abstract GenericRepository<T> getRepository();

	public Class<T> getEntityClass() {
		return entityClass;
	}

	// spring will bind errors to the className, not classname
	protected String getEntityName() {
		return AbstractEntity.entityName(entityClass);
	}

	@ModelAttribute("currentuser")
	public User exposeCurrentUser() {
		return SecurityUtils.getCurrentUserOrNull();
	}

	@RequestMapping({ "/**/", "/**/list" })
	public ModelAndView list(@RequestParam(value = "filter", required = false) String filter) {
		log.info("list " + getEntityName());
		// filters are ignored by default
		List<T> models = getRepository().findAll(filter);
		String modelName = getEntityName() + "s";
		String viewName = basePath + "/list";

		Map<String, Object> model = new HashMap<String, Object>();
		model.put(modelName, models);
		model.put("filter", filter);
		return new ModelAndView(viewName, model);
	}

	@RequestMapping("/**/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Long id) {
		log.info("edit " + getEntityName());
		T entity;
		if (id == null) {
			entity = getNewEntity();
		} else {
			entity = getRepository().findById(id);
		}
		return editView(entity);
	}

	protected ModelAndView editView(T entity) {
		String modelName = getEntityName();
		String viewName = basePath + "/edit";
		return new ModelAndView(viewName, modelName, entity);
	}

	protected ModelAndView redirectToListView() {
		return new ModelAndView("redirect:" + basePath + "/list");
	}

	protected ModelAndView redirectToMainPage() {
		return new ModelAndView("redirect:/");
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}

	public List<Validator> getValidators() {
		return validators;
	}

	public void addValidator(Validator validator) {
		validators.add(validator);
	}

	@RequestMapping("/**/save")
	public ModelAndView save(T bound, BindingResult errors,
			@RequestParam(value = "view", required = false) String view) {
		log.info("save " + getEntityName());

		if (hasError(bound, errors)) {
			log.info(errors.toString());
			return editView(bound);
		}
		getRepository().save(bound);

		// provide custom view support
		if(view!=null && view.length()>0){
			return new ModelAndView("redirect:"+view);
		}

		return redirectToListView();
	}

	@RequestMapping("/**/delete")
	public ModelAndView delete(@RequestParam(value = "id") Long id) {
		log.info("delete " + getEntityName());
		getRepository().delete(getRepository().findById(id));
		return redirectToListView();
	}

	protected boolean hasError(T bound, BindingResult errors) {
		for (Validator v : getValidators()) {
			ValidationUtils.invokeValidator(v, bound, errors);
		}
		if (errors.hasErrors()) {
			return true;
		}
		return false;
	}
}
