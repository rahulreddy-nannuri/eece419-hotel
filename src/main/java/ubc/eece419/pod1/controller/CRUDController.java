package ubc.eece419.pod1.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.Databasable;
import ubc.eece419.pod1.entity.User;
import ubc.eece419.pod1.reflection.ReflectionUtils;
import ubc.eece419.pod1.security.SecurityUtils;

@Transactional
@Controller
public abstract class CRUDController<T extends Databasable<?>> {

	private final Logger log = Logger.getLogger(CRUDController.class.getName());

	protected final Class<T> entityClass;
	protected final String basePath;

	@SuppressWarnings("unchecked")
	public CRUDController() {
		Type dt = ReflectionUtils.getGenericParameter(getClass(), CRUDController.class);
		if (!(dt instanceof Class<?>)) {
			throw new IllegalStateException("Subclassing CRUDController without specifiying Databaseable type");
		}
		this.entityClass = (Class<T>) dt;
		this.basePath = "/" + entityClass.getSimpleName().toLowerCase();
	}

	protected abstract T getNewEntity();

	protected abstract GenericRepository<T> getRepository();

	protected String getEntityName() {
		return entityClass.getSimpleName().toLowerCase();
	}

	@ModelAttribute("currentuser")
	public User exposeCurrentUser() {
		return SecurityUtils.getCurrentUserOrNull();
	}

	@RequestMapping("/**/")
	public ModelAndView index() {
		return redirectToListView();
	}

	@RequestMapping("/**/list")
	public ModelAndView list() {
		log.info("list " + getEntityName());
		List<T> models = getRepository().findAll();
		String modelName = getEntityName() + "s";
		String viewName = basePath + "/list";
		return new ModelAndView(viewName, modelName, models);
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

	@RequestMapping("/**/save")
	public ModelAndView save(T bound) {
		log.info("save " + getEntityName());
		getRepository().save(bound);
		return redirectToListView();
	}

	@RequestMapping("/**/delete")
	public ModelAndView delete(@RequestParam(value = "id") Long id) {
		log.info("delete " + getEntityName());
		getRepository().delete(getRepository().findById(id));
		return redirectToListView();
	}
}
