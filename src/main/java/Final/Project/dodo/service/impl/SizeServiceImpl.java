package Final.Project.dodo.service.impl;

import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.SizeRep;
import Final.Project.dodo.model.dto.SizeDto;
import Final.Project.dodo.model.entities.Size;
import Final.Project.dodo.model.mapper.SizeMapper;
import Final.Project.dodo.model.request.create.SizeCreateRequest;
import Final.Project.dodo.model.request.update.SizeUpdateRequest;
import Final.Project.dodo.service.SizeService;
import Final.Project.dodo.utils.ResourceBundelLanguage;
import Final.Project.dodo.utils.language;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl extends BaseServiceImpl<Size, SizeRep, SizeDto, SizeMapper> implements SizeService {
    public SizeServiceImpl(SizeRep rep, SizeMapper mapper) {
        super(rep, mapper);
    }

    @Override
    public String create(SizeCreateRequest request,Integer lang) {
        SizeDto dto = new SizeDto();
        dto.setName(request.getName());
        save(dto);
        return ResourceBundelLanguage.periodMessage(language.getLanguage(lang),"createSuccessful");
    }

    @Override
    public SizeDto update(SizeUpdateRequest request) {
        SizeDto dto = new SizeDto();
        dto.setId(request.getId());
        dto.setName(request.getName());
        return update(dto);
    }

    @Override
    public Boolean delete(Long id) {
        return delete(findById(id));
    }
}
