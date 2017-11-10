package com.jiang.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

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


    public ClassName getRawType() {
        TypeName type = TypeName.get(typeMirror);
        if (type instanceof ParameterizedTypeName) {
            return ((ParameterizedTypeName) type).rawType;
        }
        return (ClassName) type;
    }
}
