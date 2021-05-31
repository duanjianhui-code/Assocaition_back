package com.assocaition.management.module.authority.controler;


import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.authority.entity.AuthSysMenu;
import com.assocaition.management.module.authority.service.IAuthService;
import com.assocaition.management.module.authority.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")

public class MenuController {
    @Autowired
    private IAuthService authService;
    @Autowired
    private IMenuService menuService;
    @GetMapping("/search/tree")
    public ResponseResult searchMenuTree(){
        ResponseResult result = new ResponseResult();
        List<AuthSysMenu> menuList = this.authService.getMenus();
        result.setData(menuList);
        return result;
    }
    @PostMapping("/create")
    public ResponseResult addMenu(@RequestBody AuthSysMenu menu){
        ResponseResult result = new ResponseResult();
        this.menuService.addMenu(menu);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteMenu(@PathVariable("id") int id){
        ResponseResult result = new ResponseResult();
        this.menuService.deleteMenu(id);
        return result;
    }

    @PutMapping("/update")
    public ResponseResult editMenu(@RequestBody AuthSysMenu menu){
        ResponseResult result = new ResponseResult();
        this.menuService.editMenu(menu);
        return result;
    }
}
