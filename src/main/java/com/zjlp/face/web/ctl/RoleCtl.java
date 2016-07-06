package com.zjlp.face.web.ctl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zjlp.face.web.server.user.security.business.PermissionBusiness;
import com.zjlp.face.web.server.user.security.domain.Permission;
import com.zjlp.face.web.server.user.security.domain.Role;
import com.zjlp.face.web.server.user.security.domain.RolePermissionRelation;

@Controller
@RequestMapping("/role/")
public class RoleCtl extends BaseCtl{
	
	@Autowired
	private PermissionBusiness permissionBussiness;
	
   @RequestMapping(value = "/list")
	public String RoleList(Model model){
		
		List<Permission> permissionList = permissionBussiness.findResource();
		List<Role> roleList = permissionBussiness.findAllRole();
		
		
		
		model.addAttribute("roleList", roleList);
		model.addAttribute("permissionList", permissionList);
		return "/roleManager/list";
	}
	
    
    @RequestMapping(value = "/save")
	public String save(RolePermissionRelation rpr){
		System.out.println(rpr);
		permissionBussiness.addRolePermissionRelation(rpr);
		
		return "null";
	}
	

}
