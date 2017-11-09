package com.jiang.compiler;

import android.support.annotation.UiThread;

import com.jiang.annotation.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ButterForkProcessor extends AbstractProcessor {

    /**
     * 元素实用工具
     */
    private Elements elementUtils;
    /**
     * 用来创建新源、类或辅助文件的 Filer。
     */
    private Filer filer;

    static final String VIEW_TYPE = "android.view.View";
    static final String ACTIVITY_TYPE = "android.app.Activity";

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        filer = env.getFiler();
    }

    /**
     * 规定指定注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());

        return types;
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //  @UiThread
//  public MainActivity_ViewBinding(MainActivity target) {
//    this(target, target.getWindow().getDecorView());
//  }
        Map<TypeElement, List<FieldBinding>> map = new HashMap<>();


        // for each javax.lang.model.element.Element annotated with the CustomAnnotation
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            String objectType = element.getSimpleName().toString();

            TypeElement activityElement = (TypeElement) element.getEnclosingElement();
            System.out.print(" activityElement:" + activityElement.toString());
            List<FieldBinding> fieldBindings = map.get(activityElement);

            if (fieldBindings == null) {
                fieldBindings = new ArrayList<>();
                map.put(activityElement, fieldBindings);
            }

            fieldBindings.add(new FieldBinding(element.getSimpleName().toString(), element.asType(), element.getAnnotation(BindView.class).value()));

        }


        for (Map.Entry<TypeElement, List<FieldBinding>> entry : map.entrySet()) {

            String packageName = elementUtils.getPackageOf(entry.getKey()).getQualifiedName().toString();

            ClassName activityClassName = ClassName.bestGuess(entry.getKey().getSimpleName().toString());

            ClassName viewClassName = ClassName.bestGuess(VIEW_TYPE);
            FieldSpec target = FieldSpec.builder(activityClassName, "target")
                    .addModifiers(Modifier.PRIVATE)
                    .build();

            MethodSpec constructor1 = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(activityClassName, "target")
                    .addStatement("this(target,target.getWindow().getDecorView())")
                    .addAnnotation(UiThread.class)
                    .build();

            MethodSpec constructor2 = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(activityClassName, "target")
                    .addParameter(viewClassName, "source")
                    .addStatement("this.target = target")
                    .addStatement("target.button = source.findViewById($L)",entry.getValue().get(0).id)
                    .addAnnotation(UiThread.class)
                    .build();


            TypeSpec result = TypeSpec.classBuilder(activityClassName.toString() + "_ViewBinding")
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(constructor1)
                    .addMethod(constructor2)
                    .addField(target)
                    .build();

            try { // write the file

                JavaFile.builder(packageName, result).build().writeTo(filer);

            } catch (IOException e) {
                // Note: calling e.printStackTrace() will print IO errors
                // that occur from the file already existing after its first run, this is normal
            }
        }


        return true;
    }


}
