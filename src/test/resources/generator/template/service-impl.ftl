package ${basePackage}.service.${packageName}.impl;

import ${basePackage}.dao.${packageName}.${modelNameUpperCamel}Mapper;
import ${basePackage}.model.${packageName}.${modelNameUpperCamel};
import ${basePackage}.service.${packageName}.${modelNameUpperCamel}Service;
import ${basePackage}.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ${author}
 * @date ${date}
 */
@Service
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
