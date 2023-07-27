package spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ObjectFactoryDemo {

    @Autowired
    private ObjectFactory<SpringDemoScope> springDemoScopeObjectFactory;
}
