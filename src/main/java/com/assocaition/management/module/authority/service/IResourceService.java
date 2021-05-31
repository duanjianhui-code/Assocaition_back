package com.assocaition.management.module.authority.service;


import com.assocaition.management.module.authority.entity.AuthSysResources;

import java.util.List;

public interface IResourceService {
    List<AuthSysResources> searchAllResources();

    AuthSysResources searchResource(int id);

    void addResource(AuthSysResources resources);

    void updateResource(AuthSysResources resources);

    void deleteResource(int id);
}
