package org.yws.cattle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yws.cattle.models.CommonResponse;
import org.yws.cattle.models.FileEntity;
import org.yws.cattle.service.FileService;

import java.util.List;

/**
 * Created by ywszjut on 15/7/25.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "get")
    @ResponseBody
    public FileEntity get(Long id) {
        return fileService.findOne(id);
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public List<FileEntity> list(Long parent, String[] properties, String[] directions) {
        if (properties != null && directions != null && properties.length == directions.length) {
            String[][] orderProperties = new String[properties.length][2];
            for (int i = 0; i < properties.length; i++)
                orderProperties[i] = new String[]{properties[i], directions[i]};
            return fileService.listFilesByParent(parent, orderProperties);
        } else {
            return fileService.listFilesByParent(parent);
        }
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public CommonResponse save(FileEntity fileEntity) {
        Long id = null;
        try {
            id = fileService.save(fileEntity);
        } catch (Exception e) {
            return new CommonResponse(0, "保存失败", e.getMessage());
        }
        return new CommonResponse(1, "保存成功", id);
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public CommonResponse delete(Long id) {
        try {
            fileService.delete(id);
        } catch (Exception e) {
            return new CommonResponse(0, "删除失败", e.getMessage());
        }
        return new CommonResponse(1, "删除成功");
    }
}
