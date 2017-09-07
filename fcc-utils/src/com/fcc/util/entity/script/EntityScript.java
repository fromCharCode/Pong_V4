package com.fcc.util.entity.script;

/**
 * Created by dasch on 03.08.2017.
 */
public interface EntityScript<T> {

    void added(T entity);

    void removed(T removed);

    void update(float delta);

    boolean isFinished();

}
