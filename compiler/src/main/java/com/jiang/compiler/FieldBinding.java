package com.jiang.compiler;

import javax.lang.model.type.TypeMirror;

/**
 * Created by knowing on 2017/11/9.
 */

public class FieldBinding {
    String fieldName;
    TypeMirror typeMirror;
    int id;

    public FieldBinding(String fieldName, TypeMirror typeMirror, int id) {
        this.fieldName = fieldName;
        this.typeMirror = typeMirror;
        this.id = id;
    }
}
