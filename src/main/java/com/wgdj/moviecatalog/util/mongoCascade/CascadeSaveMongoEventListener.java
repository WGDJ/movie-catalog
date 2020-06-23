package com.wgdj.moviecatalog.util.mongoCascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    ReactiveMongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Object> event) {
        final Object source = event.getSource();
//        ReflectionUtils.doWithFields(source.getClass(), new CascadeCallback(source, mongoOperations));
    }
}