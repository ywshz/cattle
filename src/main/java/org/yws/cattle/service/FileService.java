package org.yws.cattle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.yws.cattle.models.FileEntity;
import org.yws.cattle.repositories.FileRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywszjut on 15/7/27.
 */
@Component("fileService")
@Transactional
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public Long save(FileEntity fileEntity){
        return fileRepository.save(fileEntity).getId();
    }

    public FileEntity findOne(Long id){
        return fileRepository.findOne(id);
    }

    public List<FileEntity> listFilesByParent(Long parent){
        if(parent == null){
            return fileRepository.findByParent(null);
        }else{
            return fileRepository.findByParent(new FileEntity(parent));
        }
    }

    public List<FileEntity> listFilesByParent(Long parent,String[]... sortProperties){

        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        for(String[] sp : sortProperties){
            orders.add(new Sort.Order(Sort.Direction.fromString(sp[1]),sp[0]));
        }
        Sort sort = new Sort(orders);

        if(parent == null){
            return fileRepository.findByParent(null,sort);
        }else{
            return fileRepository.findByParent(new FileEntity(parent),sort);
        }
    }

    public void delete(Long id) {
        fileRepository.delete(id);
    }
}
