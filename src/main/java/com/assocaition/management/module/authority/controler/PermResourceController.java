package com.assocaition.management.module.authority.controler;


import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.authority.entity.AuthSysResources;
import com.assocaition.management.module.authority.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class PermResourceController {
    @Autowired
    private IResourceService resourceService;
    @GetMapping("/search")
    public ResponseResult searchResources(){
        ResponseResult result = new ResponseResult();
        List<AuthSysResources> resourcesList = this.resourceService.searchAllResources();
        result.setData(resourcesList);
        return result;
    }

    @GetMapping("/search/{id}")
    public ResponseResult searchResource(@PathVariable("id") int id){
        ResponseResult result = new ResponseResult();
        AuthSysResources resources = this.resourceService.searchResource(id);
        result.setData(resources);
        return result;
    }

    @PostMapping("/create")
    public ResponseResult addResource(@RequestBody AuthSysResources resources){
        ResponseResult result = new ResponseResult();
        this.resourceService.addResource(resources);
        return result;
    }

    @PutMapping("/update")
    public ResponseResult updateResource(@RequestBody AuthSysResources resources){
        ResponseResult result = new ResponseResult();
        this.resourceService.updateResource(resources);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteResource(@PathVariable("id") int id){
        ResponseResult result = new ResponseResult();
        this.resourceService.deleteResource(id);
        return result;
    }
}
