package com.chabak.services;

import com.chabak.repositories.HashTagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HashTagService {
    @Autowired
    HashTagDao hashTagDao;

    public int insertHashTag(String Hashtag){
        return hashTagDao.insertHashTag(Hashtag);
    }
}
