package ubc.eece419.pod1.controller;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.*;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import ubc.eece419.pod1.dao.GenericRepository;
import ubc.eece419.pod1.entity.Databasable;

public abstract class CRUDControllerTest<T extends Databasable<?>> {

    protected CRUDController<T> controller;
    protected GenericRepository<T> repository;

    abstract T getEntity();

    @Before
    public abstract void setUp();

    @SuppressWarnings("unchecked")
	@Test
    public void testList() {
        List<T> entities = new ArrayList<T>();
        entities.add(getEntity());
        entities.add(getEntity());

        EasyMock.expect(repository.findAll()).andReturn(entities);
        EasyMock.replay(repository);

        ModelAndView mav = controller.list();
        EasyMock.verify(repository);

        List<T> model = (List<T>) mav.getModel().get(getEntity().getName() + "s");
        assertEquals(entities.size(), model.size());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testEdit() {
        T entity = getEntity();

        EasyMock.expect(repository.findById(1)).andReturn(entity);
        EasyMock.replay(repository);

        ModelAndView mav = controller.edit(null);
        T model = (T) mav.getModel().get(getEntity().getName());
        assertNotNull(model);

        mav = controller.edit(1L);
        EasyMock.verify(repository);

        model = (T) mav.getModel().get(getEntity().getName());
        assertEquals(entity, model);
    }

    @Test
    public void testSave() {
        T entity = getEntity();

        EasyMock.expect(repository.save(entity)).andReturn(entity);
        EasyMock.replay(repository);

        ModelAndView mav = controller.save(entity);
        EasyMock.verify(repository);

        assertEquals("redirect:list", mav.getViewName());
    }

    @Test
    public void testDelete() {
        T entity = getEntity();

        EasyMock.expect(repository.findById(entity.getId())).andReturn(entity);
        repository.delete(entity);
        EasyMock.replay(repository);

        ModelAndView mav = controller.delete(entity.getId());
        EasyMock.verify(repository);
        assertEquals("redirect:list", mav.getViewName());

    }
}
