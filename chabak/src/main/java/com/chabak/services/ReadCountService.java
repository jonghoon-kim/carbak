package com.chabak.services;

import com.chabak.repositories.ReadCountDao;
import com.chabak.vo.ReadCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadCountService {
    @Autowired
    ReadCountDao readCountDao;

    public ReadCount selectReadCount(ReadCount readCount){
        ReadCount selectedReadCount = readCountDao.selectReadCount(readCount);
        return selectedReadCount;
    }

    public int insertReadCount(ReadCount readCount){
        int insertCount = readCountDao.insertReadCount(readCount);
        return insertCount;
    }

    public int updateReadCount(ReadCount readCount){
        int updateCount = readCountDao.updateReadCount(readCount);
        return updateCount;
    }
}
