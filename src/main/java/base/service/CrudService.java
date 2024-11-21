package base.service;


/**
 * Сервис со всеми CRUD-операциями
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
public interface CrudService<D, E, I> extends CreateService<D, E, I>, ReadService<D, E, I>, UpdateService<D, E, I>, DeleteService<D, E, I> {

}
