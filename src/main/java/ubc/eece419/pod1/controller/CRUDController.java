package ubc.eece419.pod1.controller;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.GenericRepository;

@Transactional
@Controller
public abstract class CRUDController<T> {

    private final Logger log = Logger.getLogger(RoomController.class.getName());

    protected abstract Class<T> getClazz();

    protected abstract T getNewEntity();

    protected abstract GenericRepository<T> getRepository();

    protected String getEntityName() {
        return getClazz().getSimpleName().toLowerCase();
    }

    @ModelAttribute("user")
    public Object exposeCurrentUser() {
        // this will be a UserDetails _unless_ we are still anonymous (?)
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping("/**/")
    public ModelAndView index() {
        return new ModelAndView("redirect:list");
    }

    @RequestMapping("/**/list")
    public ModelAndView list() {
        log.info("list "+getEntityName());
        List<T> models = getRepository().findAll();
        String modelName = getEntityName() + "s";
        String viewName = getEntityName() + "/list";
        return new ModelAndView(viewName, modelName, models);
    }

    @RequestMapping("/**/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Long id) {
        log.info("edit "+getEntityName());
        T entity;
        if (id == null) {
            entity = getNewEntity();
        } else {
            entity = getRepository().findById(id);
        }
        String modelName = getEntityName();
        String viewName = getEntityName() + "/edit";
        return new ModelAndView(viewName, modelName, entity);
    }

    @RequestMapping("/**/save")
    public ModelAndView save(T bound) {
        log.info("save "+getEntityName());
        getRepository().save(bound);
        return new ModelAndView("redirect:list");
    }

    @RequestMapping("/**/delete")
    public ModelAndView delete(@RequestParam(value = "id") Long id) {
        log.info("delete "+getEntityName());
        getRepository().delete(getRepository().findById(id));
        return new ModelAndView("redirect:list");
    }
}
