package com.open.capacity.rest;

import org.flowable.idm.api.Group;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.ui.common.model.GroupRepresentation;
import org.flowable.ui.common.model.RemoteGroup;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.flowable.ui.common.service.idm.RemoteIdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pm 1280415703@qq.com
 * @date 2019/10/26 12:10
 */
@RestController
@RequestMapping("/app")
public class EditorGroupsResource {

    @Autowired
    protected RemoteIdmService remoteIdmService;

    @GetMapping(value = "/rest/editor-groups")
    public ResultListDataRepresentation getGroups(@RequestParam(required = false, value = "filter") String filter) {
        List<GroupRepresentation> result = new ArrayList<>();
        List<RemoteGroup> groups = remoteIdmService.findGroupsByNameFilter(filter);
        for (RemoteGroup group : groups) {
            result.add(new GroupRepresentation(group));
        }
        return new ResultListDataRepresentation(result);
    }

}
