package ubc.eece419.pod1.controller;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.Databasable;

public abstract class CRUDControllerTest<T extends Databasable<?>> {

	Mockery context = new Mockery();

	protected BindingResult bindingResult;

    protected CRUDController<T> controller;
    protected GenericRepository<T> repository;

    abstract T getEntity();

    @Before
    public abstract void setUp();

	@SuppressWarnings("unchecked")
	@Test
    public void testList() {
        final List<T> entities = new ArrayList<T>();
        entities.add(getEntity());
        entities.add(getEntity());

        context.checking(new Expectations() {{
        	one(repository).findAll(null);
        	will(returnValue(entities));
        }});

        ModelAndView mav = controller.list(null);

        List<T> model = (List<T>) mav.getModel().get(getEntity().getEntityName() + "s");
        assertEquals(entities.size(), model.size());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testEdit() {
        final T entity = getEntity();

        context.checking(new Expectations() {{
        	one(repository).findById(1);
        	will(returnValue(entity));
        }});

        ModelAndView mav = controller.edit(null);
        T model = (T) mav.getModel().get(getEntity().getEntityName());
        assertNotNull(model);

        mav = controller.edit(1L);

        model = (T) mav.getModel().get(getEntity().getEntityName());
        assertEquals(entity, model);
    }

    @Test
    public void testSave() {
        final T entity = getEntity();

        bindingResult = new BeanPropertyBindingResult(entity, entity.getEntityName());

        context.checking(new Expectations() {{
        	one(repository).save(entity);
        	will(returnValue(entity));

        	one(repository).findAll();
        	will(returnValue(Collections.emptyList()));
        }});

        ModelAndView mav = controller.save(entity, bindingResult,null);

        assertEquals("redirect:/room/list", mav.getViewName());
    }

    @Test
    public void testDelete() {
        final T entity = getEntity();

        context.checking(new Expectations() {{
        	one(repository).findById(entity.getId());
        	will(returnValue(entity));

        	one(repository).delete(entity);
        }});

        ModelAndView mav = controller.delete(entity.getId());
        assertEquals("redirect:/room/list", mav.getViewName());
    }
}
