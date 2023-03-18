package com.ams.api.admin.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ams.api.admin.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	
	List<Menu> findAll(Sort sort);
	
	List<Menu> findByParentOrderByDisplayOrder(Parent parent);
	
	Optional<Menu> findByKey(String menuKey);
	
	/** Incase any issue occurred with current method below can be replaced with 
	 select distinct c.* from menu a
	inner join role_privilege b on a.id = b.menu_id
	inner join menu c on c.id = a.id  or c.id = a.parent
	inner join user_details ud on ud.user_role_id = b.user_role_id  
	where ud.user_Id='OpsUser'
	order by c.displaY_order
	 */
	List<Menu> findDistinctBylinkedRolesUserRoleUsersUserIdOrParentOrderByDisplayOrder(String userId, Parent parent);
	
	@Query("select m from Menu m where m.name= :name")
	Optional<Menu> findByName(@Param("name") String name);
	
}
