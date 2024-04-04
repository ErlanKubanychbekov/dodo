package Final.Project.dodo.dao;

import Final.Project.dodo.base.BaseRep;
import Final.Project.dodo.dao.projection.OrderProductProjection;
import Final.Project.dodo.model.dto.OrderProductDto;
import Final.Project.dodo.model.entities.OrderProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRep extends BaseRep<OrderProduct> {
    @Query(value = "select * from tb_order_product o where order_id =:id",nativeQuery = true)
    List<OrderProduct> findAllByOrderId(Long id);
}
