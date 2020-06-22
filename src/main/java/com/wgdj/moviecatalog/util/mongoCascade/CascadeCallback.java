package com.wgdj.moviecatalog.util.mongoCascade;

import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

import com.wgdj.moviecatalog.model.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CascadeCallback implements ReflectionUtils.FieldCallback {

	private Object source;
	private ReactiveMongoOperations mongoOperations;

	CascadeCallback(final Object source, final ReactiveMongoOperations mongoOperations) {
		this.source = source;
		this.setMongoOperations(mongoOperations);
	}

	@Override
	public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
			final Object fieldValue = field.get(getSource());

			if (fieldValue != null) {
				final FieldCallback callback = new FieldCallback();

				ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
				
				if(fieldValue instanceof Collection<?>) {
					Collection<Entity> items = (Collection<Entity>)fieldValue;
					items.forEach(item -> {
						if(item.getId() == null) {
							getMongoOperations().save(item).block();
						}
					});
				} else {
					if(((Entity) fieldValue).getId() == null) {
						getMongoOperations().save(fieldValue).block();
					}
				}

			}
		}

	}

}