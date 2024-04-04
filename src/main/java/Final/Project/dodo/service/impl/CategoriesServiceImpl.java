package Final.Project.dodo.service.impl;


import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.CategoriesRep;
import Final.Project.dodo.model.dto.CategoriesDto;
import Final.Project.dodo.model.entities.Categories;
import Final.Project.dodo.model.mapper.CategoriesMapper;
import Final.Project.dodo.model.request.create.CategoriesCreateRequest;
import Final.Project.dodo.model.request.update.CategoriesUpdateRequest;
import Final.Project.dodo.service.CategoriesService;
import Final.Project.dodo.utils.ResourceBundelLanguage;
import Final.Project.dodo.utils.language;
import org.springframework.stereotype.Service;

@Service
public class CategoriesServiceImpl extends BaseServiceImpl<Categories, CategoriesRep, CategoriesDto, CategoriesMapper> implements CategoriesService {


    public CategoriesServiceImpl(CategoriesRep rep, CategoriesMapper mapper) {
        super(rep, mapper);
    }

    @Override
    public String create(CategoriesCreateRequest request,Integer lang) {
        CategoriesDto dto = new CategoriesDto();
        dto.setName(request.getName());
        dto.setDefinition(request.getDefinition());
         save(dto);
        return ResourceBundelLanguage.periodMessage(language.getLanguage(lang),"createSuccessful");
    }


    @Override
    public CategoriesDto update(CategoriesUpdateRequest request) {
        CategoriesDto dto = new CategoriesDto();
        dto.setId(request.getId());
        dto.setName(request.getName());
        dto.setDefinition(request.getDefinition());
        return update(dto);
    }

    @Override
    public Boolean delete(Long id) {
        return delete(findById(id));
    }
}
