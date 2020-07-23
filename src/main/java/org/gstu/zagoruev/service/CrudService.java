package org.gstu.zagoruev.service;



public interface CrudService<Entity> {

    void create(Entity entity) throws Exception;

    Entity read(long id);

    void update(Entity entity) throws Exception;

    void delete(long id) throws Exception;
}