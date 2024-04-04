package Final.Project.dodo.service;

import Final.Project.dodo.base.BaseService;
import Final.Project.dodo.dao.projection.AddressResponse;
import Final.Project.dodo.model.dto.AddressDto;
import Final.Project.dodo.model.request.create.AddressCreateRequest;
import Final.Project.dodo.model.request.update.AddressUpdateRequest;

import java.util.List;

public interface AddressService extends BaseService<AddressDto> {
    String create(String token, AddressCreateRequest request, Integer lang);

    AddressDto update(AddressUpdateRequest request);

    Boolean delete(Long id);
    List<AddressResponse> findAllByUserId(String token);
}
