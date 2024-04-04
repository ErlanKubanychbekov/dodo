package Final.Project.dodo.service.impl;

import Final.Project.dodo.dao.projection.AddressResponse;
import Final.Project.dodo.utils.JwtProvider;
import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.AddressRep;
import Final.Project.dodo.model.dto.AddressDto;
import Final.Project.dodo.model.entities.Address;
import Final.Project.dodo.model.mapper.AddressMapper;
import Final.Project.dodo.model.request.create.AddressCreateRequest;
import Final.Project.dodo.model.request.update.AddressUpdateRequest;
import Final.Project.dodo.service.AddressService;
import Final.Project.dodo.service.UserService;
import Final.Project.dodo.utils.ResourceBundelLanguage;
import Final.Project.dodo.utils.language;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, AddressRep, AddressDto, AddressMapper> implements AddressService {


    public AddressServiceImpl(AddressRep rep, AddressMapper mapper, UserService userService, JwtProvider provider) {
        super(rep, mapper);
        this.userService = userService;
        this.provider = provider;
    }
    private final UserService userService;
    private  final JwtProvider provider;

    @Override
    public String create(String token, AddressCreateRequest request, Integer lang) {
        Long userId = provider.validateToken(token,lang);
        AddressDto dto = new AddressDto();
        dto.setStreet(request.getStreet());
        dto.setNum(request.getNum());
        dto.setComment(request.getComment());
        dto.setUser(userService.findById(userId));
        save(dto);

        return ResourceBundelLanguage.periodMessage(language.getLanguage(lang),"createSuccessful");
    }


    @Override
    public AddressDto update(AddressUpdateRequest request) {
        AddressDto dto = new AddressDto();
        dto.setId(request.getId());
        dto.setStreet(request.getStreet());
        dto.setNum(request.getNum());
        dto.setComment(request.getComment());
        dto.setUpdateDate(LocalDateTime.now());
        dto.setUser(userService.findById(request.getUserId()));
        return update(dto);



    }

    @Override
    public Boolean delete(Long id) {
        AddressDto dto = findById(id);
        return delete(dto);
    }
    @Override
    public List<AddressResponse> findAllByUserId(String token) {
        Integer languageOrdinal = null;
        Long userId = provider.validateToken(token, languageOrdinal);
        return rep.findAllByUserId(userId);
    }
}
